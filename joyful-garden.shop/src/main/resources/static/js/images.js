const { default: axios } = require("axios");

function update(activityId){
	var formData = new FormData();
    var activityName = document.getElementById('activityName').value;
    var activityDate = document.getElementById('activityDate').value;
    var activityLocation = document.getElementById('activityLocation').value;
    var activityContent = document.getElementById('activityContent').value;
    var imageElement = document.getElementById('activityImage');
    var image = imageElement.getAttribute('src');
    
    console.log(imageElement);
    console.log(image);
    
    
    formData.append('activityName', activityName);
    formData.append('activityDate', activityDate);
    formData.append('activityLocation', activityLocation);
    formData.append('activityContent', activityContent);
    
    var fileInput = document.getElementById('inputImage');
    var file = fileInput.files[0];
    if(file !== null && file !== undefined){
		formData.append('activityImage',file);
		
		axios.post('/activity/'+activityId,formData)
		.then((response)=>{
			console.log(response);
			window.location.href = '/web/Activity';
		})
		.catch((error)=>{
			console.log('There was a problem with your Axios request:',error);
		});
	}else{
		formData.append('activityImage',image);
		axios.put('/update/'+activityId,formData)
		.then((response)=>{
			console.log(response);
			window.location.href = '/Activity';
		})
		.catch((error) =>{
			console.log('There was a problem with your Axios request:',error);
		});
	}
}