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
				if ($scope.applicableDate == undefined
						|| $scope.applicableDate == "") {
					alertify.alert("Error",
							"Please select a valid date from calendar.");
					return false;
				}
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error", "Please select the file type.");
					return false;
				}
				if ($scope.selectedFile == undefined
						|| $scope.selectedFile == null) {
					alertify.alert("Error", "Please select the file.");
					return false;
				}
				
				$scope.serveUploadFile($scope.fileType, $scope.selectedFile,
						$scope.applicableDate, false);
			};

			$scope.tempList = [];
			$scope.mainList = [];
			$scope.serveUploadFile = function(fileType, file, date, overwrite) {
				$scope.message = "uploading....";
				UploadService.uploadRateFile(fileType, file, date, overwrite)
						.then(function(response) {
							$scope.message = response.description;
							if (response.status === "OK") {
								$scope.tempList = response.data.TEMP;
								$scope.mainList = response.data.MAIN;
							}

						}, function(errorResponse) {
							$scope.message = "Error occurred.";
						});
			};
			
			$scope.fetchFileTypes= function() {
				MapperService.getAllMappers()
				.then(function(response) {
					console.log(response);
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
				MapperService.processMappers($scope.fileType)
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
				$scope.fileType = "";
				$scope.mainList = [];
				$scope.tempList = [];
				$scope.message = "";
				$scope.applicableDate = "";
			};

			$scope.categoryMapper = [ "GL SUBHEAD CODE", "GL SUBHEAD DESC",
					"DR FTP CATEGORY", "CR FTP CATEGORY",
					"USER SUBCLASS CODE IN", "USER SUBCLASS CODE NOT IN",
					"BACID IN", "BACID NOT IN", "DIVISION CODE IN",
					"DIVISION CODE NOT IN", "CUST IN LENGTH", "CUST TYPE IN",
					"CUST NOTIN LENGTH", "CUST TYPE NOT IN",
					"SUBDIVISION CODE IN", "SUBDIVISION CODE NOT IN",
					"TRADING BOOK NAME IN", "TRADING BOOK NAME NOT IN",
					"INSTRUMENT CLASS IN", "INSTRUMENT CLASS NOT IN",
					"GROUP BY LOGIC", "COUNT",
					"VERSION" ];
			
			$scope.productMapper = [ "FTP CATEGORY","PROD CODE","PROD DESC",
				"AST LIAB CLAS","Core Non Core","CORE PRNT","VERSION" ];
			
			$scope.divisionMapper = [
				"GL SUB HEAD CODE",
				"GLSH CHAR",
				"ENTITY CODE",
				"CATEGORY",
				"DIVISION DESC",
				"OFFICER",
				"SUBDIVISION",
				"DIVISION",
				"FINAL DIVISION DESC",
				"UPLOADED DATE",
				"BANK ID",
				"VERSION"
			];
			
			$scope.policyMapper = [
 					"FTP CATEGORY", "CCY CODE IN",
					"CCY CODE NOT IN", "DIVISION CODE IN",
					"DIVISION CODE NOT IN", "ORIG DIVISION CODE IN",
					"ORIG DIVISION CODE NOT IN", "CUST TYPE IN",
					"CUST TYPE NOT IN", "SUBDIVISION CODE IN",
					"SUBDIVISION CODE NOT IN", "FIXED LENGTH", "MATURITY DATE",
					"BASE TENOR", "MARGIN TENOR", "APPLICABLE CURVE",
					"PRE POST", "FINAL FTP CATEGORY", "UPLOADED DATE",
					"BANK ID", "VERSION"
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
			

		} ]);