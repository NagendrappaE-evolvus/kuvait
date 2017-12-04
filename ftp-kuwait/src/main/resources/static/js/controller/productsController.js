app.controller("ProductsConfigController", [ "$scope", "$http", "$rootScope",
		"$state", "$stateParams","ProductService","RuleService",

		function($scope, $http, $rootScope, $state, $stateParams, ProductService,RuleService) {
			if (!$rootScope.authenticated) {
				$state.transitionTo("login");
			}
			$scope.message = "";
			$scope.product = {
				view :"new",
				code : "",
				name : "",
				rule : {	
					ruleId:"",
					transferMethod : "",
					ruleName:"",
					isYieldCurveApplicable : false,
					yieldTenor : "",
					isKeyCurveApplicable : false,
					keyRateCode : "",
					isMrgnAdjstTempApplicable : false,
					mrgnAdjstTempCode : "",
					isMrgnAdjstExtApplicable : false,
					mrgnAdjstExtCode : ""
				},
				rateRuleCalcMethd:"",
				adjCalcMethod:"",
				variableFields:[]
			};
			
			$scope.validateProduct = function(ruleForm) {
				if(!ruleForm.$valid) {
	        		alertify.alert("Error","Please enter valid details.");
	        		return false;
	        	}
				var valid = true;
				$scope.empty_fields = [];
				angular.forEach($scope.product.variableFields,function(field,key) {
					if((field.fieldType == 'PRECHIPS' || field.fieldType == 'MULTISELECT' || field.fieldType == 'CHIPS') && field.listValue != null && field.listValue.length == 0) {
						valid = false;
						$scope.empty_fields.push(field.keyDesc);
					}
					if(field.fieldType == 'DATE_C' && field.listValue.length == 0) {
						var date = new Date(field.textValue);
						field.dateValue = (date.getMonth()+1) + "-" + date.getDate() +"-"+ date.getFullYear();
					}
				});
				if(!valid) {
					alertify.alert("Error","Please select "+$scope.empty_fields+".");
					return false;
				}
				return true;
			};
			
			$scope.operatorChanged= function(index) {
				$scope.product.variableFields[index].textValue = '';
			};
			
			$scope.transferMethodChanged = function() {
				if($scope.product.rule.transferMethod==='Matched Term') {
					$scope.product.rateRuleCalcMethd="Yield Curve";
				} else {
					$scope.product.rateRuleCalcMethd="";
				}
				$scope.rateRuleMethodChanged();
			}
			
			$scope.product.code = $stateParams.code;
			$scope.product.name = $stateParams.name;
			
			$scope.keyRateItems = [];
			$scope.yieldTenorItems = [];
			$scope.marginAdjItems = [];
			$scope.marginExtItems = [];

			$scope.rateRuleMethodChanged = function() {
				$scope.product.rule.isKeyCurveApplicable = ($scope.product.rateRuleCalcMethd!=="" && $scope.product.rateRuleCalcMethd=="Key Rate");
				$scope.product.rule.isYieldCurveApplicable = ($scope.product.rateRuleCalcMethd!=="" && $scope.product.rateRuleCalcMethd=="Yield Curve");
			};
			
			$scope.adjCalcMethodChanged = function() {
				$scope.product.rule.isMrgnAdjstTempApplicable = ($scope.product.adjCalcMethod!=="" && $scope.product.adjCalcMethod=="Transfer Rate Margin Adjustments");
				$scope.product.rule.isMrgnAdjstExtApplicable = ($scope.product.adjCalcMethod!=="" && $scope.product.adjCalcMethod=="Transfer Rate Margin Curve Extended");
			};

			if(undefined!==$scope.product.code) {
				ProductService.getProduct($scope.product.code).then(function(response) {
					if(response.data==null) {
						return false;
					}
					$scope.product.variableFields = response.data.variableFields;
					$scope.product.code = response.data.productCode;
					$scope.product.name = response.data.productName;
	            },function(errResponse) {
	            	$scope.fetchedProduct = null;
	            });
			}
			
			$scope.saveProduct = function(ruleForm) {
				if (!$scope.validateProduct(ruleForm)) {
					return false;
				}
				RuleService.saveRule($scope.product).then(function(response) {
					$scope.pageChanged();
					alertify.alert("Status",response.description);
					
				}, function(errorResponse) {
					alertify.alert("Error","Error in saving rule.");
				});
				
			};
			
			$scope.renderChip = function(val) {				
				if(val.length===0) {
					alertify.alert("Error","Invalid value.");
					return null;
				}
	            return {itemCode:val,itemValue:val};
	        };
	        
	        $scope.renderPreChip = function(item) {	
	            return item;
	        };
	        
	        
	        $scope.deletePreChip = function(item) {
	            return ($scope.product.view !='view');
	        };
	        
	        $scope.deleteChip = function(val) {
	            return ($scope.product.view !='view');
	        };
	        
	        
			$scope.fetchRule = function(currentView,rule) {
				$scope.resetProduct();
				RuleService.getRule(rule.ruleId).then(
						function(response) {
							$scope.product = response.data; 
							angular.forEach($scope.product.variableFields,function(field,key) {
								if(field.fieldType == 'DATE' || field.fieldType == 'DATE_C' ) {
									var textDate = field.textValue;
									field.textValue = new Date(textDate);
								}
							});
							if ($scope.product.rule.isKeyCurveApplicable) {
								$scope.product.rateRuleCalcMethd = "Key Rate";
							}
							if ($scope.product.rule.isYieldCurveApplicable) {
								$scope.product.rateRuleCalcMethd = "Yield Curve";
							}
							if ($scope.product.rule.isMrgnAdjstTempApplicable) {
								$scope.product.adjCalcMethod = "Transfer Rate Margin Adjustments";
							}
							if ($scope.product.rule.isMrgnAdjstExtApplicable) {
								$scope.product.adjCalcMethod = "Transfer Rate Margin Curve Extended"
							}
							$scope.product.view=currentView;
						}, function(errorResponse) {
							
						});
			};
			
			$scope.deleteRule = function(rule) {
				alertify
				.confirm("Are you sure, you want to delete the Rule Configuration?")
				.setHeader('Confirm')
				.set(
						'onok',
						function() {
							$scope
									.$apply(function() {
										RuleService.deleteRule(rule.ruleId).then(function(response) {
											$scope.pageChanged();
											alertify.alert("Status",response.description);
										}, function(errorResponse) {
											alertify.alert("Error","Error in saving rule.");
										});
									});
						});
			};
			
			$scope.paginationTotalCount=0;
			$scope.pagination = {
					currentPage:1,
					pageSize:7
			};
			
			$scope.searchRules = function() {
				$scope.pageChanged();
			};
			
			$scope.pageChanged = function() {
				if($scope.product.code!==null && $scope.product.code !=='') {
					RuleService.getRules($scope.pagination.currentPage,$scope.pagination.pageSize,$scope.searchString,$scope.product.code).then(
						function(response) {
							$scope.fetchedRules = response.data.content;
							$scope.paginationTotalCount = response.data.totalElements;
							$scope.pageCount = response.data.totalPages;
						}, function(errorResponse) {
							
						});
				}
			};
			
			$scope.resetProduct = function(resetForm) {
				$scope.ruleForm.$setPristine();
				$scope.product.view="new";
				/*$scope.product.code = "";
				$scope.product.name = "";*/
				$scope.product.rule.ruleId="";
				$scope.product.rule.transferMethod ="";
				$scope.product.rule.ruleName="";
				$scope.product.rule.isYieldCurveApplicable= false;
				$scope.product.rule.yieldTenor = "";
				$scope.product.rule.isKeyCurveApplicable = false;
				$scope.product.rule.keyRateCode = "";
				$scope.product.rule.isMrgnAdjstTempApplicable = false;
				$scope.product.rule.mrgnAdjstTempCode = "";
				$scope.product.rule.isMrgnAdjstExtApplicable = false;
				$scope.product.rule.mrgnAdjstExtCode = "";
				$scope.product.rateRuleCalcMethd="";
				$scope.product.adjCalcMethod="";
				angular.forEach($scope.product.variableFields,function(field,key) {
					field.listValue = [];
					field.textValue = "";
				});
			}
						
			
			$scope.fetchKeyItems = function() {
				ProductService.getItemsByFieldId("prd_keyRateCode").then(function(response) {
							$scope.keyRateItems = response.data;
						}, function(errorResponse) {
							if (errorResponse.data) {
								$scope.message = errorResponse.data.data;
							} else {
								$scope.message = "Error occurred.";
							}
						});
			};

			$scope.fetchYieldTenorItems = function() {
				ProductService.getItemsByFieldId("yield_tenor").then(function(response) {
					$scope.yieldTenorItems = response.data;
				}, function(errorResponse) {
					if (errorResponse.data) {
						$scope.message = errorResponse.data.data;
					} else {
						$scope.message = "Error occurred.";
					}
				});
			};

			$scope.fetchMrarginAdjItems = function(element) {
				ProductService.getItemsByFieldId("prd_mrgnAdjstTempCode").then(function(response) {
					$scope.marginAdjItems = response.data;
				}, function(errorResponse) {
					if (errorResponse.data) {
						$scope.message = errorResponse.data.data;
					} else {
						$scope.message = "Error occurred.";
					}
				});
			};

			$scope.fetchMrarginExtItems = function(element) {
				ProductService.getItemsByFieldId("prd_mrgnAdjstExtCode").then(function(response) {
					$scope.marginExtItems = response.data;
				}, function(errorResponse) {
					if (errorResponse.data) {
						$scope.message = errorResponse.data.data;
					} else {
						$scope.message = "Error occurred.";
					}
				});
			};
		}]);