app.controller("RuleMappingController", [ "$scope", "$http", "$rootScope",
		"$state", "$stateParams","ProductService","MasterService","RuleMappingService","$q",

		function($scope, $http, $rootScope, $state, $stateParams, ProductService,MasterService,RuleMappingService,$q) {
			if (!$rootScope.authenticated) {
				$state.transitionTo("login");
			}
			
			$scope.ruleMap = {
				action:"new",
				productCode:"",
				targetType:$stateParams.targetType,
				targetTypeDesc:$stateParams.targetTypeDesc,
				product: {
					ruleMappings:null
				}
			};
			$scope.products= [];
			$scope.targets= [];
			MasterService.getProducts().then(
					function(response) {
						$scope.products= response.data;
					}, function(errorResponse) {

					});
			
			$scope.fetchProduct = function() {
				$scope.message = "";
				$scope.ruleMap.product.ruleMappings = [];
				$scope.targets = [];
				if(undefined!==$scope.ruleMap.productCode && $scope.ruleMap.productCode!=null && $scope.ruleMap.productCode!=='') {
					RuleMappingService.getTargets($scope.ruleMap.targetType,$scope.ruleMap.productCode).then(
							function(response) {
								if(response.data.status==='OK') {
									$scope.targets= response.data.data;
									$scope.message = "";
								} else {
									$scope.message = response.data.description;
									$scope.status = true;
									return false;
								}
							}, function(errorResponse) {
								$scope.message = "Error in response";
								return false;
							});
					RuleMappingService.getRuleMappings($scope.ruleMap.targetType,$scope.ruleMap.productCode).then(function(response) {
						if(response.data==null) {
							return false;
						}		
						$scope.ruleMap.product.ruleMappings = response.data;
		            },function(errResponse) {
		            	$scope.ruleMap.productCode = "";
		            });
				}
			};
			$scope.ruleDeleted = false;
			$scope.reloadRules = function() {
				$scope.fetchProduct();
			};
			
			$scope.removeRuleMaps = function() {
				alertify
				.confirm("Do you want to delete the Rule? Click cancel to continue editing.")
				.setHeader('Confirm')
				.set(
						'onok',
						function() {
							$scope
									.$apply(function() {
										RuleMappingService.deleteRuleMap($scope.modalData).then(function(response) {
											$scope.modalMessage = response.description;
											$scope.ruleDeleted = true;
											$scope.reloadRules();
							            },function(errResponse) {
							            	
							            });
									});
						});
			};
			
			$scope.editTarget = function(target, targetType,productCode,action) {
				/*$state.transitionTo('home.manRuleMappings',{
					productCode:$scope.ruleMap.productCode,productName:null,targetType:$scope.ruleMap.targetType,targetTypeDesc:null
				});*/
				$scope.modalMessage = null;
				$scope.ruleMap.action = action;
				RuleMappingService.getAssignRule(target,targetType,productCode).then(function(response) {
					if(response.data==null) {
						return false;
					}		
					$scope.modalData = response.data;
					$scope.reloadRules();
	            },function(errResponse) {
	            	$scope.ruleMap.productCode = "";
	            });
				
			};
			
			$scope.addTargetClicked= function() {
				$scope.modalMessage = null;
				$scope.modalData = angular.copy($scope.modalDataNew);
			};
			
			$scope.addAnExpression = function($index) {
				$scope.modalData.ruleMappings[$index].expressions.push(angular.copy($scope.newExpression));
			};
			
			$scope.deleteExpression = function(expIndex,ruleIndex) {
								alertify
										.confirm("Are you sure, you want to delete the expression?")
										.setHeader('Confirm')
										.set(
												'onok',
												function() {
													$scope
															.$apply(function() {
																/*$scope.modalData.ruleMappings[ruleIndex].expressions
																		.splice(
																				expIndex,
																				1);*/
																$scope.modalData.ruleMappings[ruleIndex].expressions[expIndex].crudOperation="3";
															});
												});
			};
			
			$scope.deleteRuleMap= function(ruleIndex) {
				alertify
				.confirm("Are you sure, you want to delete the Rule?")
				.setHeader('Confirm')
				.set(
						'onok',
						function() {
							$scope
									.$apply(function() {
										/*$scope.modalData.ruleMappings.splice(ruleIndex,1);*/
										$scope.modalData.ruleMappings[ruleIndex].crudOperation="3";
									});
						});
				
			};
			
			$scope.createNewRuleMap=function() {
				$scope.modalData = angular.copy($scope.modalDataNew);
				$scope.modalMessage = null;
			};
			
			$scope.saveRuleMap = function() {
				$scope.modalData.productCode = $scope.ruleMap.productCode;
				alertify
				.confirm("Do you want to save the Rule? Click Cancel to continue editing.")
				.setHeader('Confirm')
				.set(
						'onok',
						function() {
							$scope
									.$apply(function() {
										RuleMappingService.saveRuleMap($scope.modalData).then(function(response) {
											$scope.modalStatus = (response.status !== 'OK');
											$scope.modalMessage = response.description;
											$scope.reloadRules();
							            },function(errResponse) {
							            	
							            });
									});
						});
				
			};
			
			$scope.addARuleMap= function() {
				$scope.modalData.ruleMappings.push(angular.copy($scope.newModalRuleMap));
			};
			
			$scope.newExpression =
				{
					expId:"", 
					expName:"", 
					expOpr:"", 
					expValues:[],
					crudOperation:"1",
					expTargetType:$scope.ruleMap.targetType,
					
				};
			
			$scope.newModalRuleMap = {
				ruleId:null,
				ruleTarget:"",
				ruleName:"",
				crudOperation:"1",
				targetType:$scope.ruleMap.targetType,
				expressions :[
					{
						expId:"", 
						expName:"", 
						expOpr:"", 
						expValues:[],
						crudOperation:"1",
						expTargetType:$scope.ruleMap.targetType,
						
					}
				]
			};
			
			$scope.renderValue = function(data/*,expIndex,ruleIndex*/) {
				var exist = false;
				return (exist?null:data);
			};
			
			$scope.deleteChip = function(data) {
				return true;
			};
			
			$scope.modalDataNew = {
				modalTarget:"",
				productCode:$scope.ruleMap.productCode,
				ruleMappings : [{
					ruleId:null,
					ruleTarget:"",
					ruleName:"",
					targetType:$scope.ruleMap.targetType,
					crudOperation:"1",
					expressions :[
						{
							expId:"", 
							expName:"", 
							expOpr:"", 
							expValue:"",
							crudOperation:"1",
							targetType:$scope.ruleMap.targetType,
							
						}
					]
				}]
			};
		
		}]);