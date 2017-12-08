'use strict'

const functions =  require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/Notifications/{user_id}/{notification_id}').onWrite(event => {
	
	const user_id = event.params.user_id;
	const notification_id  = event.params.notification_id;
	
	console.log('We have a notification to send to : ',user_id);
	
	if(!event.data.val()){

		return console.log('A notification has been delete from database :', notification_id)
	}

	const from_user = admin.database().ref('/Notifications/'+user_id+'/'+notification_id).once('value');

	return from_user.then(from_user_Result => {
		const from_user_id = from_user_Result.val().from;

		console.log('you have new notification from :',from_user_id);

		const userQuery = admin.database().ref('/Users/'+user_id+'/username').once('value');

		return userQuery.then(userResult => {

			const username = userResult.val();


			const deviceToken = admin.database().ref('/Users/'+user_id+'/device_token').once('value');

		return deviceToken.then(result => {

		const token_id = result.val();

		const payload = {
			notification: {
				title: "Meeting invitation",
				body: username + ' have sent new meeting invitation',
				icon: "default"
			}
		};

		return admin.messaging().sendToDevice(token_id,payload).then(response => {

			console.log('This was notification feature:');
		});
	

	});



		})


		


	})

	

});

