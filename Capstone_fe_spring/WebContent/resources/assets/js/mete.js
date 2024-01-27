var app = angular.module('meteApp', []);
app.controller('meteCtrl', function($scope, $http){
	
	
	
	$scope.initMete = function(data){
		$scope.count = 3;
		
		if(error){
			$scope.errorMsg = msg;
			showWarning();
		}
	};
	
	});
	
	
	
	$('#città-tab-ex').on('click', function (e) {
e.preventDefault()
$(this).tab('show')
});

$('#dest-tab-ex').on('click', function (e) {
e.preventDefault()
$(this).tab('show')
});