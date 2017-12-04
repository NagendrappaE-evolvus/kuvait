app.controller("ManageRuleMappingController", [ "$scope", "$http", "$rootScope",
		"$state", "$stateParams","ProductService","MasterService","RuleMappingService","$q",

		function($scope, $http, $rootScope, $state, $stateParams, ProductService,MasterService,RuleMappingService,$q) {
			if (!$rootScope.authenticated) {
				$state.transitionTo("login");
			}
			
			$scope.ruleMap = {
				productCode:"",
				targetType:$stateParams.targetType,
				targetTypeDesc:$stateParams.targetTypeDesc,
				product: {
					ruleMappings:null
				}
			};
			$scope.products= [];
			MasterService.getProducts().then(
					function(response) {
						$scope.products= response.data;
					}, function(errorResponse) {

					});
			
			$scope.addTargetClicked= function() {
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
																$scope.modalData.ruleMappings[ruleIndex].expressions
																		.splice(
																				expIndex,
																				1);
															});
												}
											);
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
										$scope.modalData.ruleMappings.splice(ruleIndex,1);
									});
						}
					);
			};
			
			$scope.createNewRuleMap=function() {
				$scope.modalData = angular.copy($scope.modalDataNew);
				$scope.modalMessage = null;
			};
			
			$scope.saveRuleMap = function() {
				$scope.modalData.productCode = $scope.ruleMap.productCode;
				alertify
				.confirm("Do you want to save the Rule? Click cancel to continue editing.")
				.setHeader('Confirm')
				.set(
						'onok',
						function() {
							$scope
									.$apply(function() {
										RuleMappingService.saveRuleMap($scope.modalData).then(function(response) {
											$scope.modalStatus = (response.status !== 'OK');
											$scope.modalMessage = response.description;
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
					expTargetType:$scope.ruleMap.targetType,
					
				};
			
			$scope.newModalRuleMap = {
				ruleId:null,
				ruleTarget:"",
				ruleName:"",
				targetType:$scope.ruleMap.targetType,
				expressions :[
					{
						expId:"", 
						expName:"", 
						expOpr:"", 
						expValues:[],
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
					expressions :[
						{
							expId:"", 
							expName:"", 
							expOpr:"", 
							expValue:"",
							targetType:$scope.ruleMap.targetType,
							
						}
					]
				}]
			};
		
		}]);