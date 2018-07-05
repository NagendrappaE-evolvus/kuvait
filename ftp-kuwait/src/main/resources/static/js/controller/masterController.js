app.controller("MasterController", [
	"$scope",
	"$http",
	"$rootScope","$state","MasterService","AuthService","$transitions","$location",
	function($scope, $http, $rootScope,$state,MasterService,AuthService,$transitions,$location) {
		
		if(!$rootScope.authenticated) {
			$state.transitionTo("login");
		}
		
		/*$scope.products= [];
		MasterService.getProducts().then(
				function(response) {
					$scope.products= response.data;
				}, function(errorResponse) {

				});*/
		
		$scope.logout = function() {
			 var promiseData = AuthService.logout();
			  promiseData.then(function(response) {
				  $rootScope.authenticated = false;
				  $rootScope.user = {};
				  $rootScope.loggedOut = true;
				  $location.path("/");
			  }, function(errorResponse) {
				  $rootScope.authenticated = false;
				  $rootScope.user = {};
				  $rootScope.loggedOut = true;
				  $location.path("/");
			  });
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
						$rootScope.loggedOut = true;
						return transition.router.stateService.target("login", {
							expired : true
						});
					} 
				  }, function(errorResponse) {
					  $rootScope.loggedOut = true;
					  return transition.router.stateService.target("login", {
							expired : true
						});
				  });
			}
		});
	}
]);