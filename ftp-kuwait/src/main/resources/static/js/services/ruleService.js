app.factory('RuleService', [
		'$http',
		'$rootScope',
		'$q',

		function($http, $rootScope, $q) {

			var result = {};

			return {
				getRules : function(currentPage,pageSize,searchString,productCode) {
					if(productCode==null || productCode=='') {
						return null;
					}
					
					return $http.post(
							"/rules/paged-rules",{
								"currentPage" : currentPage - 1,
								"pageSize" : pageSize,
								"searchString" : searchString,
								"productCode" : productCode
							},
							{
								header : {
									contentType : "application/json"
								}
							}).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the Rules:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				},
				saveRule : function(product) {
					return $http.post(
							"/rules/save-rule",product).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the Rules:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				},
				getRule : function(ruleId) {
					return $http.post(
							"/rules/get-rule",{"ruleId":ruleId}).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the Rules:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				},
				deleteRule : function(ruleId) {
					return $http.post(
							"/rules/delete-rule",{"ruleId":ruleId}).then(
							function(response) {
								result = response.data;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the Rules:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				}
				
			};

		} ]);