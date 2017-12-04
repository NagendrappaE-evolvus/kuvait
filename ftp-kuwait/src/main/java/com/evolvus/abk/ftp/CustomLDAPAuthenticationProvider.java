package com.evolvus.abk.ftp;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.evolvus.abk.ftp.commons.UserDetailsAdapter;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.FtpEntityService;
import com.evolvus.abk.ftp.service.impl.UserService;

@Component
public class CustomLDAPAuthenticationProvider implements AuthenticationManager, UserDetailsService {

	@Value("#{\"${ldap.url}\".trim()}")
	private String ldapUrl;

	@Value("#{\"${ldap.fatory}\".trim()}")
	private String ldapFactory;

	@Value("#{\"${ldap.domain}\".trim()}")
	private String ldapDomain;

	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	UserService userService;

	@Autowired
	FtpEntityService ftpEntityService;

	@Autowired(required = false)
	private HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String bankId = Constants.EMPTY;
		FtpAudit audit = new FtpAudit();
		User user = null;
		UserDetails userDetails = null;
		try {
			if (request.getParameter("region") != null) {
				bankId = request.getParameter("region").toString();
			}
			audit.setPreTxnVal(ftpAuditService.objectToJson(user));
			audit.setTxnAction(Constants.TXN_LOGIN);
			audit.setTxnStartTime(ftpAuditService.getCurrentTime());
			audit.setTxnUser(username);
			audit.setTxnObjectType(User.class.getName());
			FtpEntity entity = ftpEntityService.fetchEntityByCode(bankId, Boolean.TRUE);
			if (entity == null) {
				audit.setTxnStatus(Constants.STATUS_FAIL);
				audit.setTxnEndTime(ftpAuditService.getCurrentTime());
				audit.setMessage("The region selected is either inactive or removed.");
				ftpAuditService.logAudit(audit);
				throw new BadCredentialsException(audit.getMessage());
			}
			user = userService.findByUsername(username,entity);

			if (user == null || user.getIsActive()==null || !user.getIsActive()) {
				audit.setTxnStatus(Constants.STATUS_FAIL);
				audit.setTxnEndTime(ftpAuditService.getCurrentTime());
				audit.setMessage("User is not allowed.");
				ftpAuditService.logAudit(audit);
				throw new BadCredentialsException(audit.getMessage());
			}

			Map<String, Object> verifiedUser = verifyWithLdap(username, password);
			if (verifiedUser == null) {
				audit.setTxnStatus(Constants.STATUS_FAIL);
				audit.setTxnEndTime(ftpAuditService.getCurrentTime());
				audit.setMessage("Invalid username or password.");
				ftpAuditService.logAudit(audit);
				throw new BadCredentialsException(audit.getMessage());
			}
			user.setEntity(entity);

			if (verifiedUser.get(Constants.LDAP_SN_KEY) != null) {
				user.setFullName(verifiedUser.get(Constants.LDAP_SN_KEY).toString());
			} else {
				user.setFullName(Constants.NA_SHORT);
			}
			if (verifiedUser.get(Constants.LDAP_GIVEN_NAME_KEY) != null) {
				user.setName(verifiedUser.get(Constants.LDAP_GIVEN_NAME_KEY).toString());
			} else {
				user.setName(Constants.NA_SHORT);
			}
			if (verifiedUser.get(Constants.LDAP_MAIL_KEY) != null) {
				user.setEmail(verifiedUser.get(Constants.LDAP_MAIL_KEY).toString());
			} else {
				user.setEmail(Constants.NA_SHORT);
			}

			user.setUsername(username);
			user.setLastLoginTime(ftpAuditService.getCurrentTime());
			
			audit.setBankCode(entity);
			audit.setPostTxnVal(ftpAuditService.objectToJson(user));
			audit.setTxnStatus(Constants.STATUS_OK);
			audit.setTxnEndTime(ftpAuditService.getCurrentTime());
			
			userDetails = new UserDetailsAdapter(user, null);
			userService.saveUser(user);
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			audit.setTxnStatus(Constants.STATUS_FAIL);
			audit.setTxnEndTime(ftpAuditService.getCurrentTime());
			audit.setMessage("Exception in login.");
			ftpAuditService.logAudit(audit);
			throw new BadCredentialsException(audit.getMessage());
		}
		ftpAuditService.logAudit(audit);
		return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),
				userDetails.getAuthorities());
	}

	Map<String, Object> verifyWithLdap(String username, String password) {
		HashMap<String, Object> localHashMap = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, ldapFactory);
			env.put(Context.PROVIDER_URL, ldapUrl);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username + "@" + ldapDomain);
			env.put(Context.SECURITY_CREDENTIALS, password);

			String[] arrayOfString = { Constants.LDAP_SN_KEY, Constants.LDAP_GIVEN_NAME_KEY, Constants.LDAP_MAIL_KEY };

			SearchControls localSearchControls = new SearchControls();
			localSearchControls.setReturningAttributes(arrayOfString);
			String[] domainParts = ldapDomain.split("\\.");

			String domainSearchString = Constants.EMPTY;
			if (domainParts.length == 2) {
				domainSearchString = "dc=" + domainParts[0] + ",dc=" + domainParts[1];
			}
			localSearchControls.setSearchScope(2);
			// Create the initial context
			InitialLdapContext localInitialLdapContext = new InitialLdapContext(env, null);
			NamingEnumeration<?> localNamingEnumeration1 = localInitialLdapContext.search(domainSearchString,
					"(&(objectClass=user)(sAMAccountName=" + username + "))", localSearchControls);

			if (localNamingEnumeration1.hasMoreElements()) {

				SearchResult localSearchResult = (SearchResult) localNamingEnumeration1.next();

				javax.naming.directory.Attributes localAttributes = localSearchResult.getAttributes();
				if (localAttributes != null) {
					localHashMap = new HashMap<String, Object>();
					NamingEnumeration<?> localNamingEnumeration2 = localAttributes.getAll();
					while (localNamingEnumeration2.hasMore()) {
						Attribute localAttribute = (Attribute) localNamingEnumeration2.next();
						localHashMap.put(localAttribute.getID(), localAttribute.get());
					}
					localNamingEnumeration2.close();
				}
			}
			if (localInitialLdapContext != null) {
				localInitialLdapContext.close();
			}
			return localHashMap;
		} catch (NamingException e) {
			return localHashMap;
		} catch (Exception e) {
			return localHashMap;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
