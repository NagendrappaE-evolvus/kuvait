app.controller("LoginController", [
		"$scope",
		"$http",
		"$rootScope","$state","$stateParams","$q","$location","AuthService","$transitions","MasterService","$window",
		function($scope, $http, $rootScope,$state,$stateParams,$q,$location,AuthService,$transitions,MasterService,$window) {
			if($rootScope.loggedOut) {
				$window.location.reload();
			}
			$rootScope.user = {
								email:"",
								fullName:"",
								name:"",
								username:"",
								entity:{
									bankCode:"",
									regionName:"",
									isActive:false
								}
							  };
	
			$transitions.onBefore({}, function(transition) {
				if (transition.to().name !== 'login'
						&& !$rootScope.authenticated) {
					return transition.router.stateService.target("login",{
						expired : true
					});
				}

				if (transition.to().name !== 'login'
						&& $rootScope.authenticated) {
					var promise = AuthService.isAuthenticated();
					  promise.then(function(response) {
						  if(!$rootScope.authenticated) {
							return transition.router.stateService.target("login", {
								expired : true
							});
						  } 
					  }, function(errorResponse) {
						  return transition.router.stateService.target("login", {
								expired : true
							});
					  });
				}
			});
			
			$rootScope.errorInLogin = $stateParams.error;
			$rootScope.sessionExpired = $stateParams.expired;
			
			$rootScope.entities =[];
			
			MasterService.getEntities().then(function(response){
				$rootScope.entities = response.data;
			}, function(errorResponse) {
				console.log("Error in fetching entities.");
			});
			
			$rootScope.currencies = 
				[ 
					"AED", 
					"KWD", 
					"AUD",
					"USD",
					"JPY",
					"GBP",
					"EUR",
					"CHF",
					"CAD"
				];
			$rootScope.transferMethods = [
				{name:"Matched Term"},
				{name:"Assign Rate"},
				{name:"Margin Assign Rate"},
				{name:"Margin Assign Rate with Lock"},
				{name:"ABK's proprietary Method"}
			];
			$rootScope.rateRuleCalcMethods = [
				{name:"Key Rate"},
				{name:"Yield Curve"}
				];
			$rootScope.comparators = [
				{code:"eq",symbol:"=",text:"Equal"},
				{code:"ne",symbol:"<>",text:"Not Equal"},
				{code:"lt",symbol:"<",text:"Less Than"},
				{code:"gt",symbol:">",text:"Greater Than"},
				{code:"le",symbol:"<=",text:"Less Than Or Equal"},
				{code:"ge",symbol:">=",text:"Greater Than Or Equal"}
			];
			
			$rootScope.adjCalcMethods = [{name:"Transfer Rate Margin Adjustments"},
				{name:"Transfer Rate Margin Curve Extended"}]
			/*$rootScope.fileTypes = ["Yield Curve","Margin Adjustment","Margin Curve Extended","All Key Rates"];*/
			$rootScope.rateFileTypes = ["Currency Rates","Static Rates"];
			$rootScope.mapperFileTypes = ["Grand Mapper","Product Mapper","Division Mapper","Policy Mapper"];
			
			$rootScope.dateOptions = {
					    formatYear: 'yyyy',
					    startingDay: 1,
					    showWeeks:false
					  };
			
			$rootScope.patterns = {
					decimalNumeric:"^\\d+((\\.?)(\\d+))*$",
					numeric:"^\\d+$",
					alphaNumeric:"^[a-zA-Z0-9]+$",
					alphaWithSpace:"^[a-zA-Z\\s?]+$",
					alphaWithSpaceAndPeriod:"^[a-zA-Z]+((\\s?)|(\\.?\\s?)([a-zA-Z]+)(\\.?))+$",
					alphaOnly:"^[a-zA-Z]+$",
	        		alphaNumWithUnderScoreAndAmp:"^[a-zA-Z0-9_&\\-]+$",
	        		alphaNumWithUnderScore:"^[a-zA-Z0-9_]+$",
	        		alphaNumericWithSpace:"^[a-zA-Z0-9\\s?]+$",
	        		alphaNumWithSpaceAndUnderScore:"^[a-zA-Z0-9\\s?_]+$",
	        		dateFormat:'\\d{2}\\-\\d{2}\\-\\d{4}',
	        		dateFormatKw:'\\d{4}\\-\\d{2}\\-\\d{2}',
	        		emailListPattern:"\\w+\\.*\\w+@(\\w+\\.\\w+)+(,\\w+\\.*\\w+@(\\w+\\.\\w+)+)*",
	        		email:"\\w+\\.*\\w+@(\\w+\\.\\w+)+"
	        };
			
		  $scope.credentials = {username:"",password:"",region:""};
		  
		  $scope.login = function(loginForm) {
			  if(!loginForm.$valid) {
	        		alertify.alert("Invalid Details","Please enter valid credentials.");
	        		return false;
	        	}
			  var promiseData = AuthService.authenticate($scope.credentials);
			  promiseData.then(function(response) {
				  AuthService.getPrincipal(function() {
				        if ($rootScope.authenticated) {
				        	$rootScope.errorInLogin = false;
				        	$rootScope.loggedOut = false;
				        	$state.transitionTo("home.welcome");
				        } else {
				        	$rootScope.errorInLogin = true;
				        }
				      });
			  }, function(errorResponse) {
				  $rootScope.authenticated = false;
			  });
			  
		  };
}]);