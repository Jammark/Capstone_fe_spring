var app = angular.module('metaApp', []);
app.controller('metaCtrl', ['$scope', '$controller', function($scope, $controller){
	
//	$controller('alloggiCtrl', { $scope: $scope });
	
	$scope.show = false;
	$scope.alloggio = undefined;
	
	$scope.$on('onChildChange', function (event, result) {
			console.log('child change.');
    		console.log(result.data);
    		$scope.count = result.data;
		});
	
	$scope.initMeta = function(){
		$scope.count = count;
		if(error){
			$scope.errorMsg = msg;
			showWarning();
		}
		
	};
	
	$scope.check = function(){
		console.log('show: '+ $scope.show);
		return $scope.show;
	}
	
	
	
	}]);
	

	
	$('#città-tab-ex').on('click', function (e) {
e.preventDefault()
$(this).tab('show')
});

$('#dest-tab-ex').on('click', function (e) {
e.preventDefault()
$(this).tab('show')
});