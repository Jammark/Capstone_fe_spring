var app = angular.module('metaApp');
app.controller('alloggiCtrl', function($scope, $http){
	
	 $scope.$emit('childEmit', 'Child calling parent');
      $scope.$broadcast('siblingAndParent');
	
	$scope.initAlloggi = function(){
		console.table(hotels);
		console.table(appartamenti);
		if(!error){
		$scope.hotels = JSON.parse(hotels);
		$scope.appartamenti = JSON.parse(appartamenti);
		setTimeout($scope.load, 1000);
		$scope.token = token;
		}
	};
	
	
	$scope.selezionaHotel = function(index){
		let h = $scope.hotels[index];
		$scope.$parent.alloggio = h;
		$scope.$parent.show = true;
		console.log('selezione hotel');
		console.table(h);
	}
	
	$scope.selezionaAppartamento = function(index){
		let a = $scope.appartamenti[index];
		$scope.alloggio = a;
	}
	
	
	 $scope.load = function(){
		$scope.hotels.forEach(e => $scope.populateContainer(e.id));
		$scope.appartamenti.forEach(e => $scope.populateContainer(e.id));
	};
	
	 $scope.populateContainer = function(id){
    console.log('rate layout for id: '+id);
    let container = document.getElementById('rateContainer'+id);
    console.table(id+' container '+container);
  //  container!.innerHTML = '';
    let alloggio;
    let h = $scope.hotels.find(e => e.id == id);
    alloggio = h? h : $scope.appartamenti.find(e => e.id == id);

    for(let i = 0; i < 5; i++){//vengono aggiunte le 10 stelline di rating
      //  let parent = document.createElement('li');
        let child = container.getElementsByClassName('star')[i] ;
        //child.setAttribute('src','../../../');
        console.table(i+' child '+child);
        child.classList.remove('on');
        child.classList.remove('off');
        child.classList.add(alloggio.rate > i ? 'on':'off');
      //  child.classList.add('mx-2', 'star');
     //   parent.appendChild(child);
       // container?.appendChild(parent);
     //   let stars: HTMLElement[] | undefined =  this.rating.get(id);
     //   stars?.push(child);
     
     let modalContainer = document.getElementById('rateModal'+id);
     let array = Array.prototype.slice.call(modalContainer.getElementsByClassName('star')) ;
     for(let i = 0; i< array.length; i++){
        let element = array[i];
        element.classList.add('off');
     }
     child = modalContainer.getElementsByClassName('star')[i] ;

        child.onclick = () => {//al click sulla stella vengono distinte le classi per quelle accese o spente in 2 cicli for distinti
          let stars = modalContainer.getElementsByTagName('img');
          console.log('click star n: '+i);


            for(let j=0; j <= i; j++){
              stars[j].classList.remove('off');
              stars[j].classList.add( 'on');

          }
          for(let j = i +1; j < 5; j++){
              stars[j].classList.remove('on');
              stars[j].classList.add( 'off');
          }



        };

}
    
    return true;
};

$scope.showModalVoto = function(id){
  var myModal = new bootstrap.Modal(document.getElementById(`exampleModal`+id) );
  myModal.show();
};

$scope.vota = function(id){
  let container = document.getElementById('rateModal'+id);
  let stars = Array.prototype.slice.call(container.getElementsByTagName('img'));
  let i = stars.filter(e => e.classList.contains('on')).length;
  
  let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
  $http.post('http://localhost:3018/ratings', {"rate":i, "alloggioId":id}, options)
  .success(function(data){
	  console.log('rate success');
	  console.table(data);
			$scope.appartamenti.filter(e => e.id == data.alloggioId).forEach(e => e.rate = data.rate);
			$scope.hotels.filter(e => e.id == data.alloggioId).forEach(e => e.rate = data.rate);
			console.table($scope.hotels);
			console.table($scope.appartamenti);
			setTimeout($scope.load, 1000);
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
};

});