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
				}, 
				getAllMappers : function() {
					return $http.get("/items/findAllMappers")
					.then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
						console.log('Error while fetching mappers:'
								+ JSON.stringify(errResponse));
						return errResponse;
					});
				},
				clearTemp : function(type) {
					return $http.post("/mappers/clearTempMapper?mapperName="+encodeURI(type))
					.then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
						console.log('Error while clearing temp file:'
								+ JSON.stringify(errResponse));
						return errResponse;
					});
				},
				getDifferences : function(fileType) {
					return $http.post("/file/getDifferences?fileType="+fileType,
							{
								headers : {
									'Content-Type' : undefined
								}
							}).then(function(response) {
						result = response.data;
						return result;

					}, function(errResponse) {
								console.log('Error while comparing the files:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				}
			};
		}]);