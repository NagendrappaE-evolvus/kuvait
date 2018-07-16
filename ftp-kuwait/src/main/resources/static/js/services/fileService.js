app.factory('UploadService', [
		'$http',
		'$rootScope','$q',		
		function($http, $rootScope,$q) {

			var result = {};

			return {
				uploadRateFile : function(fileType,selectedFile,rateDate,overwrite) {
					var file = new FormData();
					file.append('file', selectedFile);
					file.append('fileType',fileType);
					file.append('date',(rateDate.getDate()+"-"+(rateDate.getMonth()+1)+"-"+rateDate.getFullYear()));
					file.append('overwrite',overwrite);
					return $http.post("/file/uploadRates",
							file, {
								headers : {
									'Content-Type' : undefined
								}
							}).then(function(response) {
						result = response.data;
						return result;

					}, function(errResponse) {
								console.log('Error while uploading the file:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				uploadMapperFile : function(fileType,selectedFile) {
					var file = new FormData();
					file.append('file', selectedFile);
					file.append('fileType',fileType);				
					return $http.post("/file/uploadMapper",
							file, {
								headers : {
									'Content-Type' : undefined
								}
							}).then(function(response) {
						result = response.data;
						return result;

					}, function(errResponse) {
								console.log('Error while uploading the file:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				checkDataAvailability : function(fileType, date) {
					var deferred = $q.defer();
					  
					$http
							.post("/file/checkDataAvailable?fileType="
											+ fileType
											+ "&date="
											+ (date.getDate()+"-"+(date.getMonth()+1)+"-"+date.getFullYear()))
							.then(function(response) {
								deferred.resolve(response.data);
							},
									function(errResponse) {
										console
												.log('Error calling services:'
														+ JSON
																.stringify(errResponse));
										deferred.reject(errResponse);
							});
					return deferred.promise;
				}
			};

		}]);