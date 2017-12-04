app.controller("UserController", [
		"$scope",
		"$http",
		"$rootScope","$state","$stateParams","MasterService","UserService",
		function($scope, $http, $rootScope,$state,$stateParams,MasterService,UserService) {
			
			$scope.mgmUser = {
					userId:"",
					email:"",
					fullName:"",
					name:"",
					username:"",
					isActive:false,
					isAdmin:false,
					entity:{
						
					}
				};
			
			$scope.resetUser = function(userForm) {
				$scope.action = "new";
				$scope.message = "";
				$scope.status = false;
				$scope.mgmUser = {		
						email:"",
						fullName:"",
						name:"",
						username:"",
						isAdmin:false,
						isActive:null
					};
				$scope.initEntity();
				$scope.userForm.$setPristine();	
			};
			
			$scope.action = "new";
			
			$scope.fetch = function(action,user) {
				$scope.resetUser($scope.userForm);
				$scope.action = action;
				UserService.getUser(user.username,user.entity.bankCode).then(function(response) {
					if(response.status=='OK') {
						$scope.mgmUser = response.data;
						$scope.setEntity($scope.mgmUser.entity);
					} else {
						alertify.alert("Error",response.description);
		        		return false;
					}
				}, function (errorResponse) {
					alertify.alert("Error","Error in fetching user.");
				});			
			}

			$scope.saveUser = function(userForm) {
				if(!userForm.$valid) {
	        		alertify.alert("Invalid User Details","Please enter valid Details.");
	        		return false;
				}
				UserService.saveUser($scope.mgmUser).then(function(response) {
					if(response.status==='OK') {
						$scope.status = false;
						$scope.message = response.description;
						$scope.pageChanged();
					} else {
						$scope.message = response.description;
						$scope.status = true;
					}
				}, function(errorResponse) {
					$scope.message = "Error in saving user";
				});
			};
			
			$scope.updateUser = function(userForm) {
				if(!userForm.$valid) {
	        		alertify.alert("Invalid User Details","Please enter valid Details.");
	        		return false;
				}
				UserService.updateUser($scope.mgmUser).then(function(response) {
					if(response.status==='OK') {
						$scope.status = false;
						$scope.message = response.description;
						$scope.pageChanged();
					} else {
						$scope.message = response.description;
						$scope.status = true;
					}
				}, function(errorResponse) {
					$scope.message = "Error in saving user";
				});
			};
			
			$scope.initEntity = function() {
				$scope.setEntity($rootScope.user.entity);
			};
			
			$scope.initSearchEntity = function() {
				angular.forEach($rootScope.entities,function(entity,key) {
					if(entity.bankCode == $rootScope.user.entity.bankCode) {
						$scope.searchEntity = entity;
					}
				});
			};
			
			$scope.setEntity = function(setterEntity) {
				angular.forEach($rootScope.entities,function(entity,key) {
					if(entity.bankCode == setterEntity.bankCode) {
						$scope.mgmUser.entity = entity;
					}
				});
			};
			
			$scope.paginationTotalCount=0;
			$scope.pagination = {
					currentPage:1,
					pageSize:7
			};
			
			$scope.searchUsers = function() {
				console.log($scope.mgmUser);
				$scope.pageChanged();
			};
			
			$scope.pageChanged = function() {
				UserService.pagedUsers($scope.pagination.currentPage,$scope.pagination.pageSize,$scope.searchString,$scope.searchEntity.bankCode).then(
					function(response) {
						$scope.fetchedUsers = response.data.content;
						$scope.paginationTotalCount = response.data.totalElements;
						$scope.pageCount = response.data.totalPages;
					}, function(errorResponse) {
						
				});
			};
}]);