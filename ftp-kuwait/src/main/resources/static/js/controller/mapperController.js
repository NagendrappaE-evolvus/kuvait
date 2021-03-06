app.controller("MapperController", [
		"$scope",
		'UploadService',
		"$http",
		"$rootScope",
		"$state","MapperService",
		function($scope, UploadService, $http, $rootScope, $state, MapperService) {
			if (!$rootScope.authenticated) {
				$state.transitionTo("login");
			}
			$scope.uploadFile = function() {
			/*
			 * if ($scope.applicableDate == undefined || $scope.applicableDate ==
			 * "") { alertify.alert("Error", "Please select a valid date from
			 * calendar."); return false; }
			 */
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error", "Please select the file type.");
					return false;
				}
				if ($scope.selectedFile == undefined
						|| $scope.selectedFile == null) {
					alertify.alert("Error", "Please select the file.");
					return false;
				}
				
				$scope.serveUploadFile($scope.fileType, $scope.selectedFile);
			};
			$scope.showTable=false;
			$scope.tempList = [];
			$scope.mainList = [];
			$scope.dupList = [];
			$scope.serveUploadFile = function(fileType, file) {
				$scope.message = "UPLOADINGâ€¦ Please Wait";
				$scope.showLoading=true;
				UploadService.uploadMapperFile(fileType, file)
						.then(function(response) {
							$scope.message = response.description;
							if (response.status === "OK") {
								$scope.message = "Comparison is in progress... Please Wait";	
								$scope.startComparision();
							}
							else{
								$scope.showLoading=false;
							}
						}, function(errorResponse) {
							$scope.message = "Error occurred in file upload";
						});
			};
			
			$scope.startComparision = function(){
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error", "Please select the file type.");
					return false;
				}
				MapperService.getDifferences($scope.fileType)
				.then(function(response){
					$scope.showLoading=false;
					$scope.message = response.description;
					if (response.status === "OK") {
						$scope.showTable=true;
						$scope.tempList = response.data.TEMP;
						$scope.mainList = response.data.MAIN;
						$scope.dupList = response.data.DUP;
						if($scope.mainList.length==0 && $scope.tempList.length==0 ){
							$scope.message = "Comparision done...No differences found";
						} else{
							setTimeout(function(){
								//$scope.showHighlightInTemp();
								$scope.showHighlight();
							},1000);
//							if($scope.fileType==='CT') {
//								$scope.findInGrandMapperMain();
//								$scope.findInGrandMapperTemp();
//							} else if ($scope.fileType==='PD') {
//								$scope.findInProductMapperMain();
//								$scope.findInProductMapperTemp();
//							} else if ($scope.fileType==='PC') {
//								$scope.findInPolicyMapperMain();
//								$scope.findInPolicyMapperTemp();
//							} else if ($scope.fileType==='DC') {
//								$scope.findInDivisionMapperMain();
//								$scope.findInDivisionMapperTemp();
//							}
						}
					
					}
				}, 
				function(errorResponse) {
					$scope.message = "Error occurred while comparing";
				});
			};
			
			$scope.fetchFileTypes= function() {
				MapperService.getAllMappers()
				.then(function(response) {
					$scope.mapperFiles = response.data;
				}, function(errorResponse) {
					$scope.message = "Error occurred.";
				});
			};

			$scope.processMappers = function() {
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error", "Please select the file type.");
					return false;
				}
				$scope.message = "Saving the changes..."
				MapperService.processMappers($scope.fileType)
				.then(function(response) {
					if (response.status === "OK") {
						$scope.resetFileForm();
					}
					$scope.message = response.description;
				}, function(errorResponse) {
					$scope.message = "Error occurred while processing.";
				});
				
			};

			$scope.clearTemp = function() {
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error", "Please select the file type.");
					return false;
				}
				$scope.message = "Cancellation is in progress";
				MapperService.clearTemp($scope.fileType)
				.then(function(response) {
					if (response.status === "OK") {
						$scope.resetFileForm();
					}
					$scope.message = response.description;
				}, function(errorResponse) {
					$scope.message = "Error occurred.";
				});
				
			};
			$scope.resetFileForm = function() {
				$scope.selectedFile = null;
				// angular.element("input[type='file']").val(null);
				$scope.fileType = "";
				$scope.mainList = [];
				$scope.tempList = [];
				$scope.message = "";
				/*
				 * $scope.applicableDate = "";
				 * $scope.uploadForm.applicableDate.$pristine = true;
				 */
				$scope.showTable=false;
			};

			$scope.categoryMapper = ["VERSION", "GL SUBHEAD CODE", "GL SUBHEAD DESC",
					"DR FTP CATEGORY", "CR FTP CATEGORY", "ENTITY_NO_IN", "ENTITY_NO_NOT_IN",
					"USER SUBCLASS CODE IN", "USER SUBCLASS CODE NOT IN",
					"BACID IN", "BACID NOT IN", "DIVISION CODE IN",
					"DIVISION CODE NOT IN", "CUST IN LENGTH", "CUST TYPE IN",
					"CUST NOTIN LENGTH", "CUST TYPE NOT IN",
					"SUBDIVISION CODE IN", "SUBDIVISION CODE NOT IN",
					"TRADING BOOK NAME IN", "TRADING BOOK NAME NOT IN",
					"INSTRUMENT CLASS IN", "INSTRUMENT CLASS NOT IN",
					"GROUP BY LOGIC", "COUNT" ];
			
			$scope.productMapper = ["VERSION","FTP CATEGORY","PROD CODE","PROD DESC",
				"AST LIAB CLAS","Core Non Core","CORE PRNT" ];
			
			$scope.divisionMapper = [
				"VERSION",
				"GL SUB HEAD CODE",
				"GLSH CHAR",
				"ENTITY CODE",
				"CATEGORY",
				"DIVISION DESC",
				"OFFICER",
				"SUBDIVISION In",
				"SUBDIVISION Not In",
				"DIVISION",
				"FINAL DIVISION DESC",
				"FTP_DIVISION_CODE",
				"UPLOADED DATE"
			];
			
			$scope.policyMapper = ["VERSION",
 					"FTP CATEGORY", "CCY CODE IN",
					"CCY CODE NOT IN", "DIVISION CODE IN",
					"DIVISION CODE NOT IN", "ORIG DIVISION CODE IN",
					"ORIG DIVISION CODE NOT IN", "CUST TYPE IN",
					"CUST TYPE NOT IN", "SUBDIVISION CODE IN",
					"SUBDIVISION CODE NOT IN", "FIXED LENGTH", "MATURITY DATE",
					"BASE TENOR", "MARGIN TENOR", "APPLICABLE CURVE",
					"PRE POST", "FINAL FTP CATEGORY", "UPLOADED DATE"
			];
			$scope.getSplitWord = function(item){
	     		$scope.item1 = [];
	            $scope.item2 = [];
	            
	          var res = item.split(" ");
	          for(var i = 0; i <= 1; i++){
	              $scope.item1.push(res[i]);
	          }

	          for(var i = 2; i <= res.length; i++){
	              $scope.item2.push(res[i]);
	          }	          
	      };
	      
	      $scope.showHighlight = function() {
	    	  var mainRows = document.getElementById("mainTable").getElementsByTagName("tr");
	    	  var tempRows = document.getElementById("tempTable").getElementsByTagName("tr");
	    	  var continu =false;
	    	  var p=0;
	    	  var i=0;
	    	  var dup = false;
//				for(m=1;m<=$scope.mainList.length;m++){
//					change[m]= new Array(10);
//				}			
		    for(n=0;n<$scope.mainList.length;n++)
		    {    		 		     
		      var j=0;
		      p=0;
		      i=0;
		      while(p<$scope.tempList.length){
		    	  i=0;
		      if($scope.fileType=='PD' || $scope.fileType=='PC'){
		    		 while(!continu && p<$scope.tempList.length){
		    			 if($scope.mainList[n].ftpCategory==$scope.tempList[p].ftpCategory){
		    			 continu = true;
		    			 }
		    	  		 p++;
		    		 }
		      }else{
		    		 while(!continu && p<$scope.tempList.length){
		    			 if($scope.mainList[n].glSubHeadCode==$scope.tempList[p].glSubHeadCode){
		    			 continu = true;
		    			 }
		    	  		 p++;
		    		 }
		      }	
	    		 p=p-1;
	    		 if(continu){
		    	 angular.forEach($scope.mainList[n],function(val,key)
		    	 { 
		    		 j=0;val=val+"";
		    		 if(key!=="id" && key!=="glSubHeadCode" && key!=="ftpCategory" && key!=="uploadedDate"  && key!=="version" && key!="$$hashKey"){
		    		angular.forEach($scope.tempList[p],function(value,key2)
		    		{		    		
		    			console.log("keyyyyyyyyyy= "+key);console.log("222keyyyyyyyyyy= "+key2);
		    			value=value+"";
		    			if(i==j && key2!=="id" && key2!=="glSubheadCode" && key2!=="ftpCategory" && key2!=="uploadedDate"  && key2!=="version" && key2!="$$hashKey"){
		    			    if(val!==value) {	 			 
		    			    	mainRows[n+2].cells[j].style.backgroundColor = "#f0b27a";
		    			    	tempRows[p+2].cells[j].style.backgroundColor = "#f0b27a";
		    			    	console.log("diff found: temp= "+value +" main : "+val+"n= "+n+"j= "+j);
			    		     }
		    			 } j++;		    			
		    		 });
		    		 } i++;
		    	 });    				    	
	    		 }
	    		 continu = false;
	    		 p=p+1;
		       }
		     }
		   }
	      
	      $scope.findInGrandMapperMain = function() {
	    	 var changed = true;
	    	 var index = 0;
	    	 angular.forEach($scope.tempList,function(obj) {
	    		 changed = true;
	    	  angular.forEach($scope.mainList,function(val) {
	    		  if(obj.glSubheadCode == val.glSubheadCode &&  
	    				  	obj.glSubheadDesc == val.glSubheadDesc &&
							obj.drFtpCat == val.drFtpCat &&
							
							obj.entityNoIn == val.entityNoIn &&
							obj.entityNoNotIn == val.entityNoNotIn &&
							obj.crFtpCat == val.crFtpCat &&
							obj.userSubclassCodeIn == val.userSubclassCodeIn &&

							obj.userSubclassCodeNotIn == val.userSubclassCodeNotIn &&
							obj.bacidIn == val.bacidIn &&
							obj.bacidNotIn == val.bacidNotIn &&

							obj.divisionCodeIn == val.divisionCodeIn &&
							obj.divisionCodeNotIn == val.divisionCodeNotIn &&

							obj.custInLength == val.custInLength && 
							obj.custTypeIn == val.custTypeIn &&
							obj.custNotInLength == val.custNotInLength &&

							obj.custTypeNotIn == val.custTypeNotIn &&
							obj.subDivisionCodeIn == val.subDivisionCodeIn &&
 							obj.subDivisionCodeNotIn == val.subDivisionCodeNotIn &&

							obj.tradingBookNameIn == val.tradingBookNameIn &&
							obj.tradingBookNameNotIn == val.tradingBookNameNotIn &&
							obj.instrumentClassIn == val.instrumentClassIn &&

							obj.instrumentClassNotIn == val.instrumentClassNotIn &&
							obj.groupByLogic == val.groupByLogic &&
							obj.count == val.count) {
	    			 changed=false;
	    		  }
	    	  });
	    	  $scope.tempList[index].changed=changed;
	    	  index = index + 1;
	    	 });
	      };
	      
	      $scope.findInGrandMapperTemp = function() {
	    	  var changed = true;
	    	  var index = 0;
	    	  angular.forEach($scope.mainList,function(val) {
	    		  changed = true;
	    	  angular.forEach($scope.tempList,function(obj) {
	    		  if(obj.glSubheadCode == val.glSubheadCode &&  
	    				  obj.glSubheadDesc == val.glSubheadDesc &&
							obj.drFtpCat == val.drFtpCat &&
							
							obj.entityNoIn == val.entityNoIn &&
							obj.entityNoNotIn == val.entityNoNotIn &&
							obj.crFtpCat == val.crFtpCat &&
							obj.userSubclassCodeIn == val.userSubclassCodeIn &&

							obj.userSubclassCodeNotIn == val.userSubclassCodeNotIn &&
							obj.bacidIn == val.bacidIn &&
							obj.bacidNotIn == val.bacidNotIn &&

							obj.divisionCodeIn == val.divisionCodeIn &&
							obj.divisionCodeNotIn == val.divisionCodeNotIn &&

							obj.custInLength == val.custInLength && 
							obj.custTypeIn == val.custTypeIn &&
							obj.custNotInLength == val.custNotInLength &&

							obj.custTypeNotIn == val.custTypeNotIn &&
							obj.subDivisionCodeIn == val.subDivisionCodeIn &&
 							obj.subDivisionCodeNotIn == val.subDivisionCodeNotIn &&

							obj.tradingBookNameIn == val.tradingBookNameIn &&
							obj.tradingBookNameNotIn == val.tradingBookNameNotIn &&
							obj.instrumentClassIn == val.instrumentClassIn &&

							obj.instrumentClassNotIn == val.instrumentClassNotIn &&
							obj.groupByLogic == val.groupByLogic &&
							obj.count == val.count) {
	    			  changed=false;
	    			
	    		  }
	    	  });
	    	  $scope.mainList[index].changed=changed;
	    	  index = index + 1;
	    	  });
	      };
	      
	      $scope.findInPolicyMapperMain = function() {
	    	  var changed = true;
		    	 var index = 0;
		    	 angular.forEach($scope.tempList,function(obj) {
		    		 changed = true;
		    	  angular.forEach($scope.mainList,function(val) {
		    		  if(obj.ftpCategory == val.ftpCategory && 
								obj.ccyCodeIn == val.ccyCodeIn &&
								obj.ccyCodeNotIn == val.ccyCodeNotIn &&
								obj.divisionCodeIn == val.divisionCodeIn &&
								obj.divisionCodeNotIn == val.divisionCodeNotIn &&
								obj.origDivisionCodeIn == val.origDivisionCodeIn &&
								obj.origDivisionCodeNotIn == val.origDivisionCodeNotIn &&
								obj.custTypeIn == val.custTypeIn &&
								obj.custTypeNotIn == val.custTypeNotIn &&
								obj.subdivisionCodeIn == val.subdivisionCodeIn &&
								obj.subdivisionCodeNotIn == val.subdivisionCodeNotIn &&
								obj.fixedLength == val.fixedLength &&
								obj.maturityDate == val.maturityDate &&
								obj.baseTenor == val.baseTenor &&
								obj.marginTenor == val.marginTenor &&
								obj.applicableCurve == val.applicableCurve &&
								obj.prePost == val.prePost &&
								obj.finalFtpCategory == val.finalFtpCategory) {
		    			 changed=false;
		    		  }
		    	  });
		    	  $scope.tempList[index].changed=changed;
		    	  index = index + 1;
		    	 });
	      };
	      
	      $scope.findInPolicyMapperTemp = function() {
	    	  var changed = true;
	    	  var index = 0;
	    	  angular.forEach($scope.mainList,function(val) {
	    		  changed = true;
	    	  angular.forEach($scope.tempList,function(obj) {
	    		  if(obj.ftpCategory == val.ftpCategory && 
							obj.ccyCodeIn == val.ccyCodeIn &&
							obj.ccyCodeNotIn == val.ccyCodeNotIn &&
							obj.divisionCodeIn == val.divisionCodeIn &&
							obj.divisionCodeNotIn == val.divisionCodeNotIn &&
							obj.origDivisionCodeIn == val.origDivisionCodeIn &&
							obj.origDivisionCodeNotIn == val.origDivisionCodeNotIn &&
							obj.custTypeIn == val.custTypeIn &&
							obj.custTypeNotIn == val.custTypeNotIn &&
							obj.subdivisionCodeIn == val.subdivisionCodeIn &&
							obj.subdivisionCodeNotIn == val.subdivisionCodeNotIn &&
							obj.fixedLength == val.fixedLength &&
							obj.maturityDate == val.maturityDate &&
							obj.baseTenor == val.baseTenor &&
							obj.marginTenor == val.marginTenor &&
							obj.applicableCurve == val.applicableCurve &&
							obj.prePost == val.prePost &&
							obj.finalFtpCategory == val.finalFtpCategory) {
	    			  changed=false;
	    		  }
	    	  });
	    	  $scope.mainList[index].changed=changed;
	    	  index = index + 1;
	    	  });
	      };
	      
	      $scope.findInDivisionMapperMain = function() {
	    	  var changed = true;
		    	 var index = 0;
		    	 angular.forEach($scope.tempList,function(obj) {
		    		 changed = true;
		    	  angular.forEach($scope.mainList,function(val) {
		    		  if(obj.glSubHeadCode == val.glSubHeadCode && 
								obj.glshChar == val.glshChar && 
								obj.entityCode == val.entityCode &&
								obj.category == val.category &&
								obj.divisionDesc == val.divisionDesc &&
								obj.officer == val.officer &&
								obj.subDivision == val.subDivision &&
								obj.division == val.division &&
								obj.finalDivisionDesc == val.finalDivisionDesc
		    				  ) {
		    			 changed=false;
		    		  }
		    	  });
		    	  $scope.tempList[index].changed=changed;
		    	  index = index + 1;
		    	 });
	      };
	      
	      $scope.findInDivisionMapperTemp = function() {
	    	  var changed = true;
	    	  var index = 0;
	    	  angular.forEach($scope.mainList,function(val) {
	    		  changed = true;
	    	  angular.forEach($scope.tempList,function(obj) {
	    		  if(obj.glSubHeadCode == val.glSubHeadCode && 
							obj.glshChar == val.glshChar && 
							obj.entityCode == val.entityCode &&
							obj.category == val.category &&
							obj.divisionDesc == val.divisionDesc &&
							obj.officer == val.officer &&
							obj.subDivision == val.subDivision &&
							obj.division == val.division &&
							obj.finalDivisionDesc == val.finalDivisionDesc) {
	    			  changed=false;
	    			
	    		  }
	    	  });
	    	  $scope.mainList[index].changed=changed;
	    	  index = index + 1;
	    	  });
	      };
	      
	      $scope.findInProductMapperMain = function() {
	    	  var changed = true;
		    	 var index = 0;
		    	 angular.forEach($scope.tempList,function(obj) {
		    		 changed = true;
		    	  angular.forEach($scope.mainList,function(val) {
		    		  if(obj.ftpCategory == val.ftpCategory && 
								obj.prodCode == val.prodCode && 
								obj.prodDesc == val.prodDesc &&
								obj.astLiabClas == val.astLiabClas && 
								obj.coreNonCore == val.coreNonCore &&
								obj.corePrnt == val.corePrnt) {
		    			 changed=false;
		    		  }
		    	  });
		    	  $scope.tempList[index].changed=changed;
		    	  index = index + 1;
		    	 });
	      };
	      
	      $scope.findInProductMapperTemp = function() {
	    	  var changed = true;
	    	  var index = 0;
	    	  angular.forEach($scope.mainList,function(val) {
	    		  changed = true;
	    	  angular.forEach($scope.tempList,function(obj) {
	    		  if(obj.ftpCategory == val.ftpCategory && 
							obj.prodCode == val.prodCode && 
							obj.prodDesc == val.prodDesc &&
							obj.astLiabClas == val.astLiabClas && 
							obj.coreNonCore == val.coreNonCore &&
							obj.corePrnt == val.corePrnt) {
	    			  changed=false;
	    		  }
	    	  });
	    	  $scope.mainList[index].changed=changed;
	    	  index = index + 1;
	    	  });
	      };
			

		} ]);