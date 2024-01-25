var app = angular.module('homeApp', []);
app.controller('homeCtrl', function($scope, $http){
	
	
	
	$scope.initHome = function(){
		$scope.count = count;
		$scope.scroll = new Map();
		$scope.scroll2 = new Map();
		setTimeout($scope.load, 1000);
		$scope.imgs = JSON.parse(array);
		$scope.mete = JSON.parse(mete);
		$scope.check = new Map();
		$scope.mete.forEach(m => $scope.check.set(m, false));
		$scope.pacchetti = JSON.parse(map);
		$scope.baseUrl = baseUrl;
		$scope.token = token;
	};
	
	
	$scope.getMetaImgUrl = function(img){
		return 'http://localhost:3018/mete/image/'+img.img;
	}
	
	$scope.close = function(id){
		$scope.check.set(id, false);
	}


	$scope.mostraPacchetti = function(id){
		$scope.check.set(id, true);
	}
	
	$scope.selezionaPacchetto = function(id, index){
		let pacchetto = $scope.pacchetti[id][index];
		console.log('selezionato pacchetto.');
		console.table(pacchetto);
		
		 let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
		$http.post($scope.baseUrl+'prenotazioni', pacchetto, options)
		.success(function(data){
			 $('#modalSuccess'+id).appendTo("body").modal('show');
	  console.log('prenotazione success');
	 $scope.loadCount();
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	}
	
	$scope.loadCount = function(){
		 let headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer '+ $scope.token };
let options = { headers: headers };
		$http.get($scope.baseUrl+'prenotazioni/saldo', options)
		.success(function(data){
	  console.log('saldo success:'+data.lenth);
	 $scope.count = data.length;
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	}
	
	$scope.showModalP = function(id, index){
  $('#'+id+'modalPush'+index).appendTo("body").modal('show');
};

 $scope.load = function(){
	console.log('load');
	 let h = window.innerHeight;
    let hc = document.getElementById('c').offsetTop +document.getElementsByTagName('nav')[0].offsetHeight+ document.getElementById('hero').offsetHeight;//document.getElementById('c')!.offsetHeight;
//    window.onload = () => {
    document.addEventListener("scroll", (event) => {
      let lastKnownScrollPosition = window.scrollY;
      Array.from<Element>(document.getElementsByClassName('card')).forEach((target, index, array) => {


      let current = $scope.scroll.get(index) ? $scope.scroll.get(index): false;
      let val = lastKnownScrollPosition + h > target.offsetTop +hc - target.offsetHeight;
      //lastKnownScrollPosition + h > target!.offsetTop + target!.offsetHeight && lastKnownScrollPosition > target!.offsetTop;
      $scope.scroll.set(index, val);


      if($scope.scroll.get(index) != current && !current){
        document.getElementById('r').style.backgroundImage=`url(${$scope.getMetaImgUrl($scope.imgs[index])}`;
         // target.getElementsByClassName('card-img-top')[0].classList.toggle('sticky-top');
        //  if(index < array.length - 1)
        //  array[index+1].getElementsByClassName('card-img-top')[0].classList.toggle('sticky-top');
        //  document.getElementById('topBar')!.classList.toggle('white');
      }

      if(val && lastKnownScrollPosition + h < target.offsetTop + target.offsetHeight){
        let header = target.getElementsByClassName('card-header')[0] ;
        let msr = (lastKnownScrollPosition - target.offsetTop -hc) / (target.offsetHeight * 2);
        header.style.opacity = `${ 2*Math.abs(msr)}`;
      }
    });
  });

  document.addEventListener("scroll", (event) => {
    let lastKnownScrollPosition = window.scrollY;
    Array.from<Element>(document.getElementsByClassName('card')).forEach((target, index, array) => {


    let current = $scope.scroll2.get(index) ? $scope.scroll2.get(index): false;
    let val = lastKnownScrollPosition + h > target.offsetTop  +hc;
    //lastKnownScrollPosition + h > target!.offsetTop + target!.offsetHeight && lastKnownScrollPosition > target!.offsetTop;
    $scope.scroll2.set(index, val);


    if($scope.scroll2.get(index) != current && current){
      document.getElementById('r').style.backgroundImage=`url(${$scope.getMetaImgUrl($scope.imgs[index])}`;
       // target.getElementsByClassName('card-img-top')[0].classList.toggle('sticky-top');
      //  if(index < array.length - 1)
      //  array[index+1].getElementsByClassName('card-img-top')[0].classList.toggle('sticky-top');
      //  document.getElementById('topBar')!.classList.toggle('white');
    }
  });
});
}



});