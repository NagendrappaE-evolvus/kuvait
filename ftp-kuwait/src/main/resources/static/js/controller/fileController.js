app.controller("FileUploadController", [
		"$scope",
		'UploadService',
		"$http",
		"$rootScope",
		"$state",
		function($scope, UploadService, $http, $rootScope, $state) {
			if (!$rootScope.authenticated) {
				$state.transitionTo("login");
			}
			$scope.uploadFile = function() {
				if ($scope.applicableDate == undefined || $scope.applicableDate == "") {
					alertify.alert("Error","Please select a valid date from calendar.");
					return false;
				}
				if ($scope.fileType == undefined || $scope.fileType == "") {
					alertify.alert("Error","Please select the file type.");
					return false;
				}
				if ($scope.selectedFile == undefined || $scope.selectedFile == null) {
					alertify.alert("Error","Please select the file.");
					return false;
				}
				
				var promiseData = UploadService.checkDataAvailability($scope.fileType,$scope.applicableDate);
				promiseData.then(function(response) {
					if(response.data!=null && response.status=='OK' && Number(response.data)>0) {
						alertify
								.confirm('Confirm',
										'Data already exist, click OK to overwrite.',
										function() {
											$scope.serveUploadFile($scope.fileType,$scope.selectedFile,$scope.applicableDate,true);
										}, function() {
											$scope.message = "File not uploaded.";
										});
					} else {
						$scope.serveUploadFile($scope.fileType,$scope.selectedFile,$scope.applicableDate,false);
					}
				}, function(errorResponse) {
					return false;
				});
				
/*				$scope.serveUploadFile($scope.fileType,$scope.selectedFile,$scope.applicableDate,false);*/
			};
			
			$scope.serveUploadFile = function(fileType,file,date,overwrite) {
						$scope.message = "uploading....";
						UploadService.uploadRateFile(fileType,file,date,overwrite)
						.then(function(response) {
								$scope.message = response.description;
						}, function(errorResponse) {
								$scope.message = "Error occurred.";
						});
			};

			$scope.resetFileForm = function() {
				$scope.selectedFile = null; 
				$scope.fileType = "";
			};
			
	
}]);