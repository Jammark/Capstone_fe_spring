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

function post(path, params, method='post') {

  // The rest of this code assumes you are not using a library.
  // It can be made less verbose if you use one.
  const form = document.createElement('form');
  form.method = method;
  form.action = path;
 

  for (const key in params) {
    if (params.hasOwnProperty(key)) {
      const hiddenField = document.createElement('input');
      hiddenField.type = 'hidden';
      hiddenField.name = key;
      hiddenField.value = params[key];

      form.appendChild(hiddenField);
    }
  }

  document.body.appendChild(form);
  form.submit();
}

var app = angular.module('loginApp', []);
app.controller('loginCtrl', function($scope, $http){
	
	$scope.login = function(){
		
		console.log('credentials');
		console.log($scope.un.value);
		console.log($scope.pw.value);
		
		$http.post($scope.baseUrl +'auth/login', {'email': $scope.un.value, 'password':$scope.pw.value})
		.success(function(data){
			$scope.data = data;
			$scope.data.user['token'] = data.accessToken;
			showModal();
		}).error(function(error){
			console.log(error);
			$scope.errorMsg = error.message;
			showWarning();
		});
	};
	
	$scope.initLogin = function(name){
		$scope.un = document.getElementById('email');
		$scope.pw = document.getElementById('password');
		$scope.name = name;
		$scope.baseUrl = baseUrl;
		console.log('app name:'+name);
		if(loggedMsg){
			$scope.errorMsg = loggedMsg;
			showWarning();
		}
	};
	
	$scope.navHome = function(){
		console.log('nav home');
		console.table($scope.data['user']);
		post($scope.name+"/home",$scope.data['user'] );
	
	};
	
});