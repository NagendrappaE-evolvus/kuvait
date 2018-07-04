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
				/*
				 * var promiseData =
				 * UploadService.checkDataAvailability($scope.fileType,$scope.applicableDate);
				 * promiseData.then(function(response) { if(response.data!=null &&
				 * response.status=='OK' && Number(response.data)>0) { alertify
				 * .confirm('Confirm', 'Data already exist, click OK to
				 * overwrite.', function() {
				 * $scope.serveUploadFile($scope.fileType,$scope.selectedFile,$scope.applicableDate,true);
				 * },function() { $scope.message = "File not uploaded."; }); }
				 * else {
				 * $scope.serveUploadFile($scope.fileType,$scope.selectedFile,$scope.applicableDate,false); }
				 * },function(errorResponse) { return false; });
				 */
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
					$scope.message = response.description;
					if (response.status === "OK") {
						
					}
				}, function(errorResponse) {
					$scope.message = "Error occurred.";
				});
				
			};
			
			$scope.resetFileForm = function() {
				$scope.selectedFile = null;
				$scope.fileType = "";
				$scope.mainList = [];
				$scope.tempList = [];
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
					"Group By logic", "Count",
					"VERSION" ];
			
			$scope.productMapper = [ "FTP CATEGORY","PROD CODE","PROD DESC",
				"AST LIAB CLAS","Core Non Core","CORE PRNT","VERSION" ];
			
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