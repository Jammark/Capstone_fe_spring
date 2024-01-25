var app = angular.module('saldoApp', []);
app.controller('saldoCtrl', function($scope, $http){
	
	
	
	$scope.initSaldo = function(){
		$scope.count = 3;
		$scope.prenotazioni = JSON.parse(json);
		$scope.token = token;
		$scope.redirection = redirection;
		$scope.baseUrl = baseUrl;
		
	};
	
	$scope.selezionaPrenotazione = function(index){
		let p = $scope.prenotazioni[index];
		console.log('prenotazione selezionata');
		console.table(p);
		
		let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
		
		$http.post($scope.baseUrl+`prenotazioni/acquisti/${p.id}`,p, options)
		 .success(function(data){
	  console.log('rate success');
	  console.table(data);
			console.log('acquisto success.');
			window.location.href = $scope.redirection;
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	};
	
	});