app.factory('AuthService', [
		'$http',
		'$rootScope',
		'$q',

		function($http, $rootScope, $q) {

			var result = {};

			return {
				authenticate : function(credentials) {
					var deferred = $q.defer();
					  $http.post('/login',"username=" + encodeURIComponent(credentials.username) +
			                     "&password=" + encodeURIComponent(credentials.password) +
			                     "&region="+credentials.region,{
						  headers : {
							  'Content-Type': 'application/x-www-form-urlencoded'
						  }
					  })
					  .then(function(response) { 
						 deferred.resolve(response);
					  },function(errorResponse) {
						  deferred.reject(errorResponse)
					  });
					  return deferred.promise;
				},
				isAuthenticated : function() {
					var authenticated = false
					var defered = $q.defer();
					 $http.get('user').then(function(response) {
						 if (response.data.authenticated) {
							 	authenticated = $rootScope.authenticated = true;
						        $rootScope.errorInLogin = false;
						        $rootScope.user= response.data.principal.user;
						        defered.resolve(authenticated);
						      } else {
						    	$rootScope.errorInLogin = true;
						        $rootScope.authenticated = false;
						        defered.resolve(authenticated);
						      }					     
					    },function(errorResponse) {
					      $rootScope.authenticated = false;
					      defered.resolve(authenticated);
					    });
					return defered.promise;;
				}
				,
				getPrincipal : function(callback) {
					  $http.get('user').then(function(response) {
					      if (response.data.authenticated) {
					        $rootScope.authenticated = true;
					        $rootScope.errorInLogin = false;
					        $rootScope.user= response.data.principal.user;
					      } else {
					    	$rootScope.errorInLogin = true;
					        $rootScope.authenticated = false;
					      }
					      callback && callback();
					    },function() {
					      $rootScope.authenticated = false;
					      callback && callback();
					    });
				},
				logout : function(credentials) {
					var deferred = $q.defer();
					  $http.post('logout')
					  .then(function(response) { 
						 deferred.resolve(response);
					  },function(errorResponse) {
						  deferred.reject(errorResponse)
					  });
					  return deferred.promise;
				}
			};

		} ]);