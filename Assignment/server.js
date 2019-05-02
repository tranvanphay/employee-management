require ( './models/db');
const express = require('express');
const path=require('path');
const exphbs=require('express-handlebars');
const bodyparser=require('body-parser');
const employeeController = require('./controllers/employeeController');
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
app.use(express.static('public'));
app.use(bodyparser.urlencoded({
    extended:true
}));
app.use(bodyparser.json());
app.set('views',path.join(__dirname,'/views/'));
app.engine('hbs',exphbs({extname:'hbs',defaultLayout:'mainLayout',layoutsDir: __dirname+'/views/layouts/'}));
app.set('view engine','hbs');
/*app.listen(8080,() => {
    console.log('Express server started at port : 8080');
    
});*/
server.listen(process.env.PORT || 8080);
app.use('/', employeeController);
console.log("Server Running");
io.sockets.on('connection', function(socket){
    console.log("device connected");
    socket.on('client-send-data',function(data){
        console.log("Server received:" + data);
        io.sockets.emit('server-send-data',{noidung : data});
    });
});