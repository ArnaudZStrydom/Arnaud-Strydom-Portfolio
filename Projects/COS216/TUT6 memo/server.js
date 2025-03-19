//Ronan van de Vyver

//This messaging server is basic and flawed, with 
//virtually no error handling or protection.

//It would be best if it were not used as-is in
//any important project.
//It would be even better if you did not use this code at all :)

var ht = require('http');
var fs = require('fs');


const allowedFiles = ['/index.css', '/index.html', '/']; //A list of allowed files
var messages = {'messages' : [{'user' : 'Chitter Help', 'message' : 'Welcome to Chitter! Start Chitting Today!'}]}; //An object with a message array

console.log('Starting Server');

ht.createServer(function (req, res) { 

	if (req.method== 'GET') //File request
	{
		if (req.url == '/messages')
		{
			var send = JSON.stringify(messages);
			res.writeHead(200, {'Content-Type' : 'application/json', 'Access-Control-Allow-Origin' : '*'});
			res.write(send);
			res.end();
		}
		else if (allowedFiles.includes(req.url)) //File request
		{

				var f = getFile(req.url);
				res.writeHead(200, {'Access-Control-Allow-Origin' : '*'});
				res.write(f);
				res.end();
		}
		else //File not found, return JSON error message
		{
				console.log(req.url + ' not found, returning 404');
				res.writeHead(404, {'Content-Type' : 'application/json', 'Access-Control-Allow-Origin' : '*'});
				res.write('{"status" : "failed", "error" : "Not found"}');
				res.end();
				return;
		}
	}
	else if (req.method == 'POST')
	{
		if (req.url == '/send') //A message was sent
		{

			var body = '';
			req.on('data', function(data)
			 {
				body += data;
			 });
			req.on('end', function() //Message received, parse data
			 {
				var pars = getParameters(body);
				messages.messages.push({'user' : pars.user, 'message' : pars.message});
				res.end(); //Tell message sender request is done
			 });


		}
		else
		{
			res.end();
		}
	}


}).listen(8085);

console.log('Listening for connections');


//Get the parameters by splitting them on & and =, return object
function getParameters(body)
{
	var pars = body.split('&'); //Split parameters
	var res = {};
	for (var i=0;i<pars.length;i++)
	{
		var p = pars[i].split('=');
		res[p[0]] = p[1];
	}

	return res;
}

//Get a filestream by name
function getFile(name)
{
	console.log('Getting ' + name);

	if (name == '/')
	{
		return fs.readFileSync('index.html');
	}

	if (allowedFiles.includes(name))
	{
		return fs.readFileSync('.' + name);	
	}

}

