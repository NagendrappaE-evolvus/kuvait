var app = angular.module("FTPUiApp", [ 'ui.router', 'ngRoute','angular.chips',
		'ui.bootstrap','angularUtils.directives.dirPagination','ui.multiselect']);
app
		.constant(
				"Constants",
				{
					"SERVER_ERROR" : "500",
					"NOT_FOUND" : "404",
					"COPYRIGHT_YEAR":"2017",
					"alphaWithSpaceRegex" : /^[a-zA-Z\s]*$/,
					"smallAlphaNoSpaceRegex" : /^[a-z]*$/,
					"capAlphaWithSpaceRegex" : /^[A-Z\s]*$/,
					"capAlphaNoSpaceRegex" : /^[A-Z]*$/,
					"alphaNumericWithSpaceRegex" : /^([a-zA-Z0-9 ]+)$/,
					"alphaNumericNoSpaceRegex" : /^([a-zA-Z0-9]+)$/,
					"numericNoSpaceRegex" : /^([0-9]+)$/,
					"emailRegex" : /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
				});

app.config(["$stateProvider", "$urlRouterProvider", "$routeProvider", "$locationProvider","$httpProvider",
		function($stateProvider, $urlRouterProvider, $routeProvider, $locationProvider, $httpProvider) {
	 		$httpProvider.interceptors.push('callChecker');
			$urlRouterProvider.otherwise("/login");
			$stateProvider.state('login', {
				url : "/login",
				params : {
					error:false,
					loggedOut:false,
					expired:false
				},
				views : {
					"home" : {
						templateUrl : 'login.html',
						controller : 'LoginController'
					}
				}
			}).state('home', {
				url : "/home",
				views : {
					"home" : {
						templateUrl : 'master-page.html',
						controller : 'MasterController'
					}
				}
			}).state('home.rate-upload', {
				url : "/rate-upload",
				views : {
					"child" : {
						templateUrl : 'pages/rate-upload.html',
						controller : 'FileUploadController'
					}
				}
			}).state('home.products', {
				url : "/products",
				params : {code:null,name:null},
				views : {
					"child" : {
						templateUrl : 'pages/products.html',
						controller : 'ProductsConfigController'
					}
				}
			}).state('home.welcome', {
				url : "/welcome",
				views : {
					"child" : {
						templateUrl : 'pages/welcome.html',
						controller : 'DashboardController'
					}
				}
			}).state('home.schemecodes', {
				url : "/scheme-codes",
				views : {
					"child" : {
						templateUrl : 'pages/scheme-codes.html',
						controller : 'SchemeCodesController'
					}
				}
			}).state('home.ruleMappings', {
				url : "/configuration/:targetType",
				params : {
					productCode:null,
					targetType:null,
					targetTypeDesc:null
				},
				views : {
					"child" : {
						templateUrl : 'pages/rule-mappings.html',
						controller : 'RuleMappingController'
					}
				}
			}).state('home.manRuleMappings', {
				url : "/configuration/:targetType",
				params : {productCode:null,productName:null,targetType:null,targetTypeDesc:null},
				views : {
					"child" : {
						templateUrl : 'pages/manRuleMappings.html',
						controller : 'ManageRuleMappingController'
					}
				}
			}).state('home.user-mgmt', {
				url : "/users",
				views : {
					"child" : {
						templateUrl : 'pages/user-management.html',
						controller : 'UserController'
					}
				}
			});
		}]);
		
app.factory('callChecker', ['$q', '$rootScope',"$state", function($q, $rootScope,$state) {  
    var requestInterceptor = {
       'responseError': function(rejection) {
        	if(rejection.status >=400 && rejection.status < 500) {
        		$rootScope.authenticated = false;
        		$state.transitionTo("login",{
					expired : true
				});
        	}
            return $q.reject(rejection);
          }
    };
    return requestInterceptor;
}]);

app.filter('groupBy', function() {
	return function(list, group_by) {

		var filtered = [];
		var prev_item = null;
		var group_changed = false;
		// this is a new field which is added to each item where we append
		// "_CHANGED"
		// to indicate a field change in the list
		var new_field = group_by + '_CHANGED';

		// loop through each item in the list
		angular.forEach(list, function(item) {

			group_changed = false;

			// if not the first item
			if (prev_item !== null) {

				// check if the group by field changed
				if (prev_item[group_by] !== item[group_by]) {
					group_changed = true;
				}

				// otherwise we have the first item in the list which is new
			} else {
				group_changed = true;
			}

			// if the group changed, then add a new field to the item
			// to indicate this
			if (group_changed) {
				item[new_field] = true;
			} else {
				item[new_field] = false;
			}

			filtered.push(item);
			prev_item = item;

		});

		return filtered;
	};
});