app.factory('MasterService', [
		'$http',
		'$rootScope','$q',
		
		function($http, $rootScope,$q) {
			var result = {};
			return {
				getProducts : function() {
					return $http.post("/products/get-menu-items"				
							).then(function(response) {
						result = response.data;
						return result;

					}, function(errResponse) {
								console.log('Error while fetching menu items:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				}, 
				getEntities : function() {
					return $http.post("/entities").then(function(response) {
							result = response.data;
							return result;
					}, function(errResponse) {
						console.log('Error while fetching entities:'
								+ JSON.stringify(errResponse));
						return errResponse;
					});
				}
			};
		}]);