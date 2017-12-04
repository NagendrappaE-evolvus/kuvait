app.factory('RuleMappingService', [
		'$http',
		'$rootScope',
		'$q',

		function($http, $rootScope, $q) {

			var result = {};

			return {
				getTargets : function(targetType,productCode) {
					if(targetType==null || targetType=='' || productCode==null || productCode=='') {
						return null;
					}
					return $http.post(
							"/rule-map/getTargets",{
								targetType:targetType,
								productCode:productCode
							},
							{
								header : {
									contentType : "application/json"
								}
							}).then(
							function(response) {
								result = response;
								return result;
							},
							function(errResponse) {
								console.log('Error while Fetching the Rules:'
										+ JSON.stringify(errResponse));
								return errResponse;
							});
				},
				getRuleMappings: function(targetType,productCode) {
					if(targetType==null || targetType=='' || productCode==null || productCode=='') {
						return null;
					}
					return $http.post(
							"/rule-map/getRuleMappings",{
								targetType:targetType,
								productCode:productCode
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
				saveRuleMap: function(ruleMapBean) {
					return $http.post(
							"/rule-map/save-assign-rules",ruleMapBean).then(
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
				getAssignRule: function(target, targetType,productCode) {
					return $http.post(
							"/rule-map/get-assign-rule",{
								target:target,
								targetType:targetType,
								productCode:productCode
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
				deleteRuleMap: function(ruleMapBean) {
					return $http.post(
							"/rule-map/remove-assign-rules",ruleMapBean).then(
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