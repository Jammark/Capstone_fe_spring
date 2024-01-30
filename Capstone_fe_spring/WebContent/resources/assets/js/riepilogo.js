 app = angular.module('riepilogoApp', []);
app.controller('riepilogoCtrl', function($scope, $http){
	
	
	
	$scope.initRiepilogo = function(){
		$scope.count = count;
		if(error){
		$scope.errorMsg = msg;
			showWarning();
		}
		
	};
	
	});