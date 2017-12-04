app.factory('ProductService', [
		'$http',
		'$rootScope','$q',
		
		function($http, $rootScope,$q) {

			var result = {};

			return {
				getProduct : function(code) {
					
					return $http.post(
							"/products/getByCode/"+code,{header:{
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
				},
				getItemsByFieldId:function(fieldId) {
					return $http.post(
							"/items/getByFieldId?fieldId="+fieldId).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Connecting to the Server : '
										+ JSON.stringify(errResponse));
								return errResponse;

							});
				},
				saveProductSchemeCodes:function(schemsMap) {
					return $http.post(
							"/products/save-code-map",schemsMap).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Connecting to the Server : '
										+ JSON.stringify(errResponse));
								return errResponse;

							});
				}
			};

		}]);