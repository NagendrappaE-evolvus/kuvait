app.factory('SchemesService', [
		'$http',
		'$rootScope',
		'$q',

		function($http, $rootScope) {

			var result = {};

			return {
				addScheme : function(scheme) {
					return $http.post("/schemes/save",scheme
							).then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
								console.log('Error while saving the Scheme Code:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				updateScheme : function(scheme) {
					return $http.post("/schemes/update",scheme
							).then(function(response) {
						result = response.data;
						return result;
					},
							function(errResponse) {
								console.log('Error while updating Scheme code:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				deleteScheme : function(scheme) {
					return $http.post("/schemes/delete",scheme
							).then(function(response) {
						result = response.data;
						return result;
					},
							function(errResponse) {
								console.log('Error while updating Scheme code:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				pagedSchemes : function(currentPage,pageSize,searchString) {
					return $http
					.post("/schemes/schemes-paged",
							{
								"currentPage" : currentPage - 1,
								"pageSize" : pageSize,
								"searchString" : searchString
							})
					.then(
							function(response) {
								result = response.data;
								return result;
							},
							function(reason) {
								console.log('Error while fetching Scheme codes:'
										+ JSON.stringify(errResponse));
								return errResponse;
							}
						);
				},
				getAllSchemes:function() {
					return $http.post("/schemes/getAll")
					.then(function(response) {
								result = response;
								return result;
						},
						function(errResponse) {
								console.log('Error while saving the Scheme Code:'+ JSON.stringify(errResponse));
								return errResponse;
						});
				},
				getProductSchemeMap : function(code) {
					
					return $http.post(
							"/schemes/getSchmMap/"+code,{header:{
								contentType:"application/json"
							}}).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the product:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				}
			};

		} ]);