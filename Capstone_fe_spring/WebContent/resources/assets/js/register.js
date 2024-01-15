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



var app = angular.module('registerApp', []);
app.controller('registerCtrl', function($scope, $http){
	
	$scope.register = function(){
		
		console.log('credentials');
		console.log($scope.un.value);
		console.log($scope.pw.value);
		
		$http.post('http://localhost:3018/auth/register', {'email': $scope.un.value, 'password':$scope.pw.value, 'name': $scope.nm.value, 'surname': $scope.cg.value})
		.success(function(data){
			showModal();
			
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	};
	
	$scope.initRegister = function(){
		$scope.un = document.getElementById('email');
		$scope.pw = document.getElementById('password');
		$scope.nm = document.getElementById('name');
		$scope.cg = document.getElementById('surname');
	};
	
	$scope.finish = function(){
		window.location.href='login';
	};
	
});