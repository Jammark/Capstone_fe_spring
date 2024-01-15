//import * as bootstrap from '/webjars/bootstrap/dist/js/bootstrap.esm.min.js'

window.onload = () => {
	console.log('load');
	let lista = document.getElementsByTagName('input');
	for(let i = 0; i < lista.length; i++){
		let element = lista[i];
		element.addEventListener('input', function (evt)  {
			console.log('change v');
			let count = 0;
			for(let i = 0; i < lista.length; i++){
				if(lista[i].validity.valid){
					count++;
				}
				}
				let btn = document.getElementById('btn');
			
				btn.disabled = count != lista.length;
				console.log(count);
			
		});
	}
}

function showModal(){
	var myModal = new bootstrap.Modal(document.getElementById(`modalSuccess`));
      myModal.show();
}

var app = angular.module('loginApp', []);
app.controller('loginCtrl', function($scope, $http){
	
	$scope.login = function(){
		
		console.log('credentials');
		console.log($scope.un.value);
		console.log($scope.pw.value);
		
		$http.post('http://localhost:3018/auth/login', {'email': $scope.un.value, 'password':$scope.pw.value})
		.success(function(data){
			showModal();
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	};
	
	$scope.initLogin = function(){
		$scope.un = document.getElementById('email');
		$scope.pw = document.getElementById('password');
	};
	
});