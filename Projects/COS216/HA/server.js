const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const axios = require('axios');
const readline = require('readline');
require('dotenv').config();

const expressApp = express();
const server = http.createServer(expressApp);
const io = socketIo(server);

const username = process.env.USER;
const password = process.env.PASSWORD;

expressApp.use(express.static(__dirname));

expressApp.use(express.json());

expressApp.post('/login', (req, res) => {
    const email = req.body.email;
    const userpass = req.body.password;

    axios.post('https://wheatley.cs.up.ac.za/u23536013/api.php', {
        type: 'Login',
        email: email,
        password: userpass
    }, {
        headers: { 'Content-Type': 'application/json' },
        auth: { username: username, password: password }
    })
    .then(response => {
        if (response.data.status === 'Success') {
            res.status(200).json({ status: 'Success', email: email });
        } else {
            res.status(401).json({ status: 'Failure' });
        }
    })
    .catch(error => {
        console.error('Error during login:', error.message);
        res.status(500).json({ status: 'Error' });
    });
});

io.on('connection', (socket) => {
    socket.on('userConnected', (email) => {
        console.log(`User connected: ${email}`);
        usernameSocketMap[email] = socket.id;

        socket.on('disconnect', () => {
            console.log(`${email} disconnected`);
            delete usernameSocketMap[email];
        });
    });
});




expressApp.post('/create-auction', (req, res) => {

    const auctionId = generateAuctionID();

    const { starting_price, auction_name, start_date, end_date, listing_id } = req.body;

    if (!starting_price || !auction_name || !start_date || !end_date || !listing_id) {
        return res.status(400).json({ error: 'All fields are required.' });
    }

    const auctionData = {
        "type": "CreateAuction",
        "starting_price": starting_price,
        "auction_id": auctionId,
        "auction_name": auction_name,
        "start_date": start_date,
        "end_date": end_date,
        "listing_id": listing_id
    };
    

    axios.post('https://wheatley.cs.up.ac.za/u23536013/api.php', auctionData, {
        headers: {
            'Content-Type': 'application/json'
        },
        auth: {
            username: username,
            password: password
        }
    })
    .then(response => {
        if (response.data.status === 'Success') {
            createdauctions[auctionId] = {
                "auction_id": auctionId,
                "auction_name": auction_name
            }
            res.status(200).json({ status: 'Success', auctionId: auctionId });
        } else {
            res.status(401).json({ status: 'Failure' });
        }
    })
    .catch(error => {
        console.error('Error during auction creation:', error.message);
        res.status(500).json({ error: 'Internal server error.' });
    });
});



expressApp.post('/getAuctionListings', async (req, res) => {
    try {
        const auctions = await getAllActiveAuctionsWithRetry();
        const responsetextsdata = [];
        var responses = {};

        for (const auction of auctions) {
            try {
                var response = await axios.post('https://wheatley.cs.up.ac.za/u23536013/api.php', {
                    "type": "getAuctionListings",
                    "listing_id": auction.listing_id,
                    "auction_id": auction.auction_id
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    auth: {
                        username: username,
                        password: password
                    }
                });

               var responsetext = response.data
               var responsedata = responsetext.data
               responsetextsdata.push(responsedata[0]);
            } catch (error) {
                console.error('Error during auction listings population:', error.message);
                responses.push({
                    status: 'Error',
                    message: error.message
                });
            }
        }
        responses = {
            status: "Success",
            data: responsetextsdata

        };

        res.status(200).json(responses);
    } catch (error) {
        res.status(500).json({ status: 'Error', message: 'Failed to retrieve active auctions.' });
    }
});

expressApp.post('/ViewBid', async (req, res) => {
    var listingid = req.body.listing_id;
    var auctionid = req.body.auction_id;
    try {
        
            try {
                var response = await axios.post('https://wheatley.cs.up.ac.za/u23536013/api.php', {
                    "type": "getAuctionListings",
                    "listing_id": listingid,
                    "auction_id": auctionid
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    auth: {
                        username: username,
                        password: password
                    }
                });

               var responsetext = response.data
              
            } catch (error) {
                console.error('Error during auction listings population:', error.message);
                res.status(500).json({ status: 'Error', message: 'Failed to retrieve active auctions.' });
            }

        res.status(200).json(responsetext);
    } catch (error) {
        res.status(500).json({ status: 'Error', message: 'Failed to retrieve active auctions.' });
    }
});


expressApp.post('/highestbid', async (req, res) => {
    var auctionId = req.body.auction_id;
    try {
        
            try {
                var response = await axios.post('https://wheatley.cs.up.ac.za/u23536013/api.php', {
                    "type": "GetAuction",
                    "auction_id": auctionId
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    auth: {
                        username: username,
                        password: password
                    }
                });

               var responsetext = response.data
              
            } catch (error) {
                console.error('Error during auction listings highest bid:', error.message);
                res.status(500).json({ status: 'Error', message: 'Failed to retrieve highest bid.' });
            }

        res.status(200).json(responsetext);
    } catch (error) {
        res.status(500).json({ status: 'Error', message: 'Failed to retrieve active auctions.' });
    }
});

const userPort = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});



const usernameSocketMap = {};
const usernameSocketMap1 = {};
const createdauctions = {};


function askForPort() {
    userPort.question('Enter a port number (1024-49151): ', (port) => {
        serverusername = username;
        port = parseInt(port);
        if (port >= 1024 && port <= 49151) {
            checkPortAvailability(port,serverusername);
        } else {
            console.log('Please enter a valid port number within the range of 1024-49151.');
            askForPort();
        }
    });
}



function checkPortAvailability(port,serverusername) {
    const net = require('net');
    const tester = net.createServer()
        .once('error', (err) => {
            if (err.code === 'EADDRINUSE') {
                console.log(`Port ${port} is already in use. Please choose a different port ${serverusername} .`);
                askForPort();
            }
        })
        .once('listening', () => {
            tester.once('close', () => {
                startServer(port,serverusername);
            }).close();
        })
        .listen(port);
}

function startServer(port,serverusername) {
    const username1 = serverusername;
    server.listen(port, () => {
        console.log(`Server listening on port ${port}`);
    });
}

function closeAllConnections() {
    Object.keys(usernameSocketMap).forEach((username) => {
        const socketId = usernameSocketMap[username];
        const socket = io.sockets.sockets.get(socketId);
        io.to(socketId).emit('forceDisconnect');
        if (socket) {
            socket.disconnect(true);
        }
    });
    server.close(() => {
        console.log('Server is offline.');
        process.exit(0);
    });
}

userPort.on('line', (input) => {
    const command = input.trim().toUpperCase();

    
    if (command === 'KILL') {
        userPort.question('Enter the username to disconnect: ', (username) => {
            if (usernameSocketMap.hasOwnProperty(username)) {
                const socketId = usernameSocketMap[username];
                io.to(socketId).emit('forceDisconnect');
                console.log(`Connection for user ${username} closed.`);
                delete usernameSocketMap[username];

            } else {
                console.log(`No active connection found for user ${username}.`);

            }
        });
    } else if (command === 'LIST') {
        
        const connections = Object.entries(usernameSocketMap).map(([username, socketId]) => ({ username, socketId }));
        console.log('Current connections:', connections);
    } else if (command === 'QUIT') {
        closeAllConnections();
    } else if (command === 'AUCTIONS') {
        getAllActiveAuctionsWithRetry()
        .then(auctionsInfo => {

            console.log('Active Auction information:', auctionsInfo);
            console.log('Current session created auctions: ',createdauctions);
        })
        .catch(error => {
            console.error('Error fetching active auctions:', error.message);
        });
    } else {
        console.log('Invalid command. Please enter one of the following commands: "KILL", "LIST", "QUIT", "AUCTIONS".');
    }
});


async function checkAuctions() {
    try {
        const allAuctions = await getAllAuctions();
        const currentTime = new Date(); 

        allAuctions.forEach((auction) => {
            const { auction_id, start_date, end_date } = auction;

           
            const startDate = new Date(start_date);
            const endDate = new Date(end_date);

            
            const timeUntilStart = startDate - currentTime;
            const timeUntilEnd = endDate - currentTime;

            if (timeUntilStart <= 0 && timeUntilEnd > 0) {
                startAuction(auction_id);
            }

            if (timeUntilEnd <= 0) {
                endAuction(auction_id);
            }
        });
    } catch (error) {
        console.error('Error checking auctions:', error.message);
    }
}



setInterval(checkAuctions, 60000); 
async function getAllAuctions() {
    try {
        const requestData = {
            "type": 'GetAllAuctions'
        };
        
        const response = await axios.post(
            'https://wheatley.cs.up.ac.za/u23536013/api.php',
            requestData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: username,
                    password: password
                }
            }
        );
        $responsetext = response.data;
        return $responsetext.data;
    } catch (error) {
        console.error('Error fetching all auctions:', error.message);
    }
}

function generateAuctionID() {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let auctionId = '';
    for (let i = 0; i < 10; i++) {
        auctionId += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return auctionId;
}





async function startAuction(auctionId) {
    notifyAuctionStart(auctionId);
    await updateAuction(auctionId, { auction_status: "Ongoing" });
}


async function endAuction(auctionId) {

    await updateAuction(auctionId, { auction_status: "Done" });
}




function handleBid(data) {
    const { auctionId, bidAmount } = data;

    if (typeof bidAmount !== 'number' || bidAmount <= 0) {
        console.log('Invalid bid amount');
        return;
    }



    if (bidAmount <= currentHighestBid) {
        console.log('Bid amount must be higher than the current highest bid');
        return;
    }


    currentHighestBid = bidAmount;
    io.emit('highestBidUpdated', { auctionId, bidAmount }); 
}


function notifyAuctionStart(auctionId) {
    io.emit('auctionStarting', { auctionId }); 
}


async function updateAuction(auctionId, auctionData) {
    try {
        var requestData = {
            "type": 'UpdateAuction',
            "auction_id": auctionId
        };

       var updatedata = {
            ...requestData,
            ...auctionData

       };
        
        const response = await axios.post(
            'https://wheatley.cs.up.ac.za/u23536013/api.php',
            updatedata,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: username,
                    password: password
                }
            }
        );
    } catch (error) {
        console.error('Error updating auctions:', error.message);
    }
}

async function getAuction(auctionId) {
    const auctionData = {
        "type": "GetAuction",
        "auction_id": auctionId
    };

    try {
        const response = await axios.post(
            'https://wheatley.cs.up.ac.za/u23536013/api.php',
            auctionData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: username,
                    password: password
                }
            }
        );

            return response.data.data
    } catch (error) {
        console.error('Error fetching ongoing auction IDs:', error.message);
        throw error; 
    }
}

async function getAllActiveAuctionsWithRetry() {
    const maxRetries = 3; 
    let retryCount = 0; 

    const retryHttpRequest = async () => {
        try {
            return await getAllActiveAuctions();
        } catch (error) {
            console.error('Error fetching active auctions:', error.message);
            if (retryCount < maxRetries) {
                retryCount++;
                const delay = Math.pow(2, retryCount - 1) * 1000; 
                console.log(`Retrying in ${delay} milliseconds...`);
                await new Promise(resolve => setTimeout(resolve, delay));
                return retryHttpRequest();
            } else {
                throw new Error('Max retries reached. Failed to fetch active auctions.');
            }
        }
    };

    return retryHttpRequest();
}

async function getAllActiveAuctions() {
    let activeAuctions = [];
    let auctionInfo = [];

    const auctionData = {
        "type": "GetAllActiveAuctions"
    };

    try {
        const response = await axios.post(
            'https://wheatley.cs.up.ac.za/u23536013/api.php',
            auctionData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: username,
                    password: password
                }
            }
        );

        const responseData = response.data;
        if (responseData && responseData.data && Array.isArray(responseData.data) && responseData.data.length) {
            activeAuctions = responseData.data;
        } else {
            return "No active or created listings found."; 
        }
    } catch (error) {
        console.error('Error fetching ongoing auction IDs:', error.message);
        throw error; 
    }

    

    return activeAuctions;
}
    
    

askForPort()
