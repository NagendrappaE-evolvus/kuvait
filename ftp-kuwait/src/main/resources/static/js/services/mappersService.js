/**
 * Service for mapper communication
 */
app.factory('MapperService', [
		'$http',
		'$rootScope',
		function($http, $rootScope) {
			var result = {};
			return {
				processMappers : function(type) {
					return $http.post("/mappers/process-mappers?mapperName="+encodeURI(type))
					.then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
						console.log('Error while fetching menu items:'
								+ JSON.stringify(errResponse));
						return errResponse;
					});
				}
			};
		}]);