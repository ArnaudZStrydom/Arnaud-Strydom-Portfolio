//Ronan van de Vyver

//This socket server is for use only with the tutorial on Sockets.


//It accepts a connection on port 8081, and if sent a number
//it will broadcast that number to all connected clients.
//The number is an angle in degrees, from 0 to 360.

var wS = require('ws');

var angle = 0;
const server = new wS.Server({port: 8081});
var clients = [];

server.on('connection', (ws) => 
{
	console.log('New connection');
	ws.send(angle);
	clients.push(ws);

	//Add connection to array for future use
	ws.on('message', (message) => 
	{
		if (!isNaN(message)) //If it is a number, update angle and send to clients
			setAngle(message);
	});

	//Remove the  client from the array
	ws.on('close', (ws) =>
	{
		console.log('Disconnect');
		var i = clients.indexOf(ws);
		if (i>-1)
		{
			clients.splice(i, 1);
		}
	});

});

//For all connected clients, send the angle after updating it
function setAngle(nAngle)
{
	angle = nAngle%360;
	for (var i =0;i<clients.length;i++)
	{
		clients[i].send(angle);
	}
}


