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