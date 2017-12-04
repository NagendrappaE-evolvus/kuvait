app.factory('UserService', [
		'$http',
		'$rootScope',
		function($http, $rootScope) {
			var result = {};

			return {
				saveUser : function(user) {
					return $http.post("/user/save",user
							).then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
								console.log('Error while saving the USER:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				updateUser : function(user) {
					return $http.post("/user/update",user
					).then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
								console.log('Error while saving the USER:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				},
				pagedUsers : function(currentPage,pageSize,searchString,bankCode) {
					return $http
					.post("/user/users-paged",
							{
								"currentPage" : currentPage - 1,
								"pageSize" : pageSize,
								"searchString" : searchString,
								"bankCode":bankCode								
							})
					.then(
							function(response) {
								result = response.data;
								return result;
							},
							function(reason) {
								console.log('Error while fetching USERs:'
										+ JSON.stringify(errResponse));
								return errResponse;
							}
						);
				},
				getUser : function(username,bankcode) {
					return $http.post("/user/getbyname/"+username+"/"+bankcode).then(function(response) {
						result = response.data;
						return result;
					}, function(errResponse) {
								console.log('Error while saving the USER:'
										+ JSON.stringify(errResponse));
								return errResponse;
					});
				}
			};
		}]);