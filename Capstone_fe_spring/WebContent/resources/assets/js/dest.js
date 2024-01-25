var app = angular.module('metaApp', []);
app.controller('metaCtrl', function($scope, $http){
	
	
	
	$scope.initMeta = function(data){
		$scope.count = 3;
		
		
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