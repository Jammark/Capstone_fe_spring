var app = angular.module('homeApp', []);
app.controller('homeCtrl', function($scope, $http){
	
	
	
	$scope.initHome = function(data){
		$scope.count = 5;
		$scope.scroll = new Map();
		$scope.scroll2 = new Map();
		setTimeout($scope.load, 1000);
		$scope.imgs = JSON.parse(array);
		
	};
	
	
	$scope.getMetaImgUrl = function(img){
		return 'http://localhost:3018/mete/image/'+img.img;
	}



 $scope.load = function(){
	console.log('load');
	 let h = window.innerHeight;
    let hc = document.getElementById('c').offsetTop +document.getElementsByTagName('nav')[0].offsetHeight;//document.getElementById('c')!.offsetHeight;
//    window.onload = () => {
    document.addEventListener("scroll", (event) => {
      let lastKnownScrollPosition = window.scrollY;
      Array.from<Element>(document.getElementsByClassName('card')).forEach((target, index, array) => {


      let current = $scope.scroll.get(index) ? $scope.scroll.get(index): false;
      let val = lastKnownScrollPosition + h > target.offsetTop +hc;
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
        let msr = (lastKnownScrollPosition - target.offsetTop) / target.offsetHeight - 0.5;
        header.style.opacity = `${1- 2*Math.abs(msr)}`;
      }
    });
  });

  document.addEventListener("scroll", (event) => {
    let lastKnownScrollPosition = window.scrollY;
    Array.from<Element>(document.getElementsByClassName('card')).forEach((target, index, array) => {


    let current = $scope.scroll2.get(index) ? $scope.scroll2.get(index): false;
    let val = lastKnownScrollPosition + h > target.offsetTop + target.offsetHeight +hc;
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