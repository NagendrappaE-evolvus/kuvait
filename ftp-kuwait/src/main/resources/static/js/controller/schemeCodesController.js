app.controller("SchemeCodesController", [ "$scope", "$http", "$rootScope",
		"$state", "$stateParams",'MasterService','SchemesService','ProductService',

		function($scope, $http, $rootScope, $state, $stateParams,MasterService,SchemesService,ProductService) {
					$scope.products = [];
					MasterService.getProducts().then(function(response) {
						$scope.products = response.data;
					}, function(errorResponse) {
						
					});
					$scope.availableSchemes = [];
					$scope.fetchAllAvailableSchemes = function() {
						SchemesService.getAllSchemes().then(
							function(response) {
								$scope.availableSchemes = response.data.data;
							}, function(errorResponse) {

							});
					};
					$scope.fetchAllAvailableSchemes();
					$scope.schemeMap =  {
						action:"view",
						productCode:"",
						prodSchemes:[]
					};
					
					$scope.scheme = {
							action:"new", 
							schemeId:"",
							schemeCode:"",
							schemeDesc:"",
							ftpCategory:"",
							glSubHeadCode:""
					};
					
					$scope.renderSchemes = function(data) {
						var returndata = true;
						angular.forEach($scope.schemeMap.prodSchemes,function(item) {
							if(data.schmCode == item.schmCode && data.glSubHeadCode == item.glSubHeadCode && data.ftpCategory == item.ftpCategory) {
								returndata = false;
								alertify.alert("Warning","The scheme code is already selected.");
							}
						});
						return (returndata?data:null);
					};
					
					$scope.fetchProduct = function() {
						$scope.message = "";
						if(undefined!==$scope.schemeMap.productCode &&  $scope.schemeMap.productCode!==null && $scope.schemeMap.productCode!=='') {
							SchemesService.getProductSchemeMap($scope.schemeMap.productCode).then(function(response) {
								if(response.status!=='OK') {
									$scope.message = response.description;
									$scope.status = (response.status !=='OK');
									$scope.schemeMap.prodSchemes = null;
								} else {
									$scope.message = "";
									$scope.schemeMap.prodSchemes = response.data;
								}
				            },function(errResponse) {
				            	$scope.fetchedProduct = null;
				            });
						} else {
							$scope.schemeMap.prodSchemes = null;
						}
					}
					
					$scope.saveSchemeMap= function(schemeMapForm) {
						if(!schemeMapForm.$valid || $scope.schemeMap.prodSchemes == null || $scope.schemeMap.prodSchemes.length == 0) {
							alertify.alert("Alert","Please enter the valid details.");
							return false;
						}
						ProductService.saveProductSchemeCodes($scope.schemeMap).then(function(response) {
							$scope.message = response.description;
							$scope.status = (response.status !=='OK');
						}, function(err_response) {
							
						});
					};
					
					$scope.editSchemeMap= function(code) {
						$scope.schemeMap.action ='update';
						$scope.fetchProduct();
					};
					
					$scope.resetSchemeMap = function(schemeMapForm) {
						$scope.schemeMap =  {
								action:"view",
								productCode:"",
								prodSchemes:[]
							};
						$scope.message = "";
						$scope.status = true;
						 
					};
					
					$scope.deleteSchemeChip = function(chip) {
						return $scope.schemeMap.action!=='view';
					};
					
					$scope.saveScheme = function(form) {
						if(!form.$valid) {
							alertify.alert("Alert","Please enter the valid details.");
							return false;
						}
						SchemesService.addScheme($scope.scheme).then(function(response) {
							$scope.schemeFormMessage = response.description;
							if(response.status !== 'OK') {
								$scope.schemeFormStatus = true;
							} else {
								$scope.pageChanged();
								$scope.fetchAllAvailableSchemes();
							}
						},function(errorResponse) {
							$scope.schemeFormMessage = "Error in saving scheme code.";
						});
					};
					
					$scope.deleteScheme = function(scheme) {
						alertify
						.confirm("Are you sure, you want to delete the Scheme Code?")
						.setHeader('Confirm')
						.set(
								'onok',
								function() {
									$scope
											.$apply(function() {
												SchemesService.deleteScheme(scheme).then(function(response) {
													
													alertify.alert("Info",response.description);
													if(response.status == 'OK') {
														$scope.pageChanged();
														$scope.fetchAllAvailableSchemes();
													}
												});
											});
								});
					};
					
					$scope.updateScheme = function(form) {
						if(!form.$valid) {
							alertify.alert("Alert","Please enter the valid details.");
							return false;
						}
						SchemesService.updateScheme($scope.scheme).then(function(response) {
							$scope.schemeFormMessage = response.description;
							if(response.status !== 'OK') {
								$scope.schemeFormStatus = true;
							} else {
								$scope.pageChanged();
								$scope.fetchAllAvailableSchemes();
							}
						},function(errorResponse) {
							$scope.schemeFormMessage = "Error in saving scheme code.";
						});
					};
								
					$scope.resetScheme = function(form) {
						$scope.scheme = {action :"new"};
						$scope.schemeFormMessage="";
						$scope.schemeFormStatus = false;
						form.$setPristine();
					};
					
					$scope.fetch = function(action,scheme) {
						$scope.scheme = {
							action :action,
							schemeId:scheme.schemeId,
							schemeCode:scheme.schmCode,
							schemeDesc:scheme.schmDesc,
							glSubHeadCode:scheme.glSubHeadCode,
							ftpCategory:scheme.ftpCategory
						};
					};
					
					
					$scope.searchSchemes = function() {
						$scope.pageChanged();
					};
					
					$scope.schemes =[];
					
					$scope.paginationTotalCount=0;
					$scope.pagination = {
							currentPage:1,
							pageSize:5,
							searchString:""
					};
					
					$scope.pageChanged = function() {
						SchemesService.pagedSchemes($scope.pagination.currentPage,
								$scope.pagination.pageSize, $scope.pagination.searchString).then(
								function(response) {
									$scope.schemes = response.data.content;
									$scope.paginationTotalCount = response.data.totalElements;
									$scope.pageCount = response.data.totalPages;
								}, function(errorResponse) {

								});
						
					};
		} ]);