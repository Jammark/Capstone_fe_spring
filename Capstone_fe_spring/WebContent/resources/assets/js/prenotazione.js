var app = angular.module('metaApp');
app.controller('prenotazioneCtrl',["$scope", "$filter","$http", function($scope, $filter, $http){
	
		$scope.partenza = new Date();
		$scope.ritorno = new Date();
		$scope.nomePartenza = undefined;
		$scope.nomeArrivo = undefined;
		$scope.viaggioAndata;
		$scope.viaggioRitorno;
		$scope.numeroPosti;
		
		$scope.voliA;
		$scope.voliR;
		
		$scope.$on('onChildAChange', function (event, result) {
			console.log('child change.');
    		console.log(result.data);
    		$scope.viaggioAndata = result.data;
		});
		
		$scope.$on('onChildRChange', function (event, result) {
			console.log('child change.');
    		console.log(result.data);
    		$scope.viaggioRitorno = result.data;
		});
	
	$scope.initPrenotazione = function(){
		$scope.cityNames = JSON.parse(nc);
		$scope.alloggio = $scope.$parent.alloggio;
		$scope.nomeArrivo = metaMap.get($scope.alloggio.metaId);
		$scope.viaggioAndata = undefined;
		$scope.viaggioRitorno = undefined;
		$scope.baseUrl = baseUrl;
		$scope.token = token;
	};
	
	$scope.checked = function(){
		return $scope.voliA && $scope.voliA.length > 0 && $scope.voliR && $scope.voliR.length > 0;
	}
	
	$scope.showA = function(){
		console.log('show andata:'+ $scope.viaggioAndata);
		return $scope.viaggioAndata != undefined;
	}
	
	$scope.submit = function(){
		let obj = {
			ritorno: $filter('date')($scope.ritorno, "dd/MM/yyyy"),
			partenza: $filter('date')($scope.partenza ,"dd/MM/yyyy"),
			np: $scope.nomePartenza,
			na: $scope.nomeArrivo
		};
		console.table(obj);
		//voli andata ricerca
		let url = new URL(`${$scope.baseUrl}trasporti/voli/cerca`);

      url.searchParams.append('partenza', `${$scope.nomePartenza}`);


      url.searchParams.append('arrivo', `${$scope.nomeArrivo}`);


      url.searchParams.append('data', $filter('date')($scope.partenza ,"yyyy-MM-dd"));
      let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
		$http.get(url.toString(), options)
		.success(function(data){
	
	  console.table(data);
			console.log('ricerca voli andata success.');
			$scope.voliA = data;
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
		//voli ritorno ricerca
		 url = new URL(`${$scope.baseUrl}trasporti/voli/cerca`);

      url.searchParams.append('partenza', `${$scope.nomeArrivo}`);


      url.searchParams.append('arrivo', `${$scope.nomePartenza}`);


      url.searchParams.append('data', $filter('date')($scope.ritorno ,"yyyy-MM-dd"));
     
		$http.get(url.toString(), options)
		.success(function(data){
	
	  console.table(data);
			console.log('ricerca voli ritorno success.');
			$scope.voliR = data;
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	};
	
	$scope.rimuoviA = function(){
		$scope.viaggioAndata = undefined;
	}
	
	$scope.rimuoviR = function(){
		$scope.viaggioRitorno = undefined;
	}
	
	$scope.getMaxPosti = function(){
		return $scope.alloggio.capienza ? $scope.alloggio.capienza : 50;
	}
	
	$scope.loadCount = function(){
		 let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
		$http.get($scope.baseUrl+'prenotazioni/saldo', options)
		.success(function(data){
	  console.log('saldo success:'+data.lenth);
	 $scope.$emit('onChildChange', {data: data.length});
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	}
	
	$scope.prenota = function(){
		let prenotazione = {
        data: $filter('date')($scope.partenza ,"yyyy-MM-dd"),
        dataFine: $filter('date')($scope.ritorno ,"yyyy-MM-dd"),
        metaId: $scope.alloggio.metaId,
        alloggioId: $scope.alloggio.id,
        trasportoId: $scope.viaggioAndata.id,
        ritornoId: $scope.viaggioRitorno.id,
        id: undefined,
        numeroGiorni: undefined,
        userId: undefined,
        prezzo: undefined,
        numeroPosti: $scope.numeroPosti
      };
      console.log('invio prenotazione.');
      console.table(prenotazione);
      let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
      $http.post($scope.baseUrl+'prenotazioni', prenotazione, options)
      .success(function(data){
	
	  console.table(data);
			console.log('prenotazione success.');
			 var myModal = new bootstrap.Modal(document.getElementById(`modalSuccess`));
    		myModal.show();
    		$scope.loadCount();
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	}
	
	}]);
	
	app.controller('voliACtrl', function($scope){
		
		
		$scope.initVoliA = function(){
			$scope.voli = $scope.$parent.voliA;
		}
		
		$scope.select = function(data){
			console.log('selezionato volo andata:');
			console.table(data);
			
			 $scope.$emit('onChildAChange', {data: data});
		}
		
		$scope.rimuovi = function(){
			conole.log('rimuovi andata');
			 $scope.$emit('onChildAChange', {data: undefined});
		}
	});
	
	app.controller('voliRCtrl', function($scope){
		
		
		$scope.initVoliR = function(){
			$scope.voli = $scope.$parent.voliR;
		}
		
		$scope.select = function(data){
			$scope.$emit('onChildRChange', {data: data});
		}
		
		$scope.rimuovi = function(){
			 $scope.$emit('onChildRChange', {data: undefined});
		}
	});