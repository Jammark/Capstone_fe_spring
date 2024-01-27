var app = angular.module('metaApp');
app.controller('prenotazioneCtrl',["$scope", "$filter", function($scope, $filter){
	
		$scope.partenza = new Date();
		$scope.ritorno = new Date();
		$scope.nomePartenza = undefined;
	
	$scope.initPrenotazione = function(){
		$scope.cityNames = JSON.parse(nc);
		$scope.alloggio = $scope.$parent.alloggio;
		
		$scope.viaggioAndata = undefined;
		$scope.viaggioRitorno = undefined;
	};
	
	$scope.submit = function(){
		let obj = {
			ritorno: $filter('date')($scope.ritorno, "dd/MM/yyyy"),
			partenza: $filter('date')($scope.partenza ,"dd/MM/yyyy"),
			np: $scope.nomePartenza
		};
		console.table(obj);
	};
	}]);