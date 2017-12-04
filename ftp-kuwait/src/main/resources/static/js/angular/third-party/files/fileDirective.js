app.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					if(element[0].files[0].size<=fileSize.sizeInBytes){
					modelSetter(scope, element[0].files[0]);
					}else{
						document.getElementById("selectedFile").value = "";
						alertify.alert("Alert",'Selected file size should not be greater than '+fileSize.sizeInMB+'MB');
					}
				});
			});
		}
	};
} ]);

