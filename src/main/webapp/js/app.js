var socket = new WebSocket('ws://' + window.location.host + room);//

socket.onmessage = message => {
    var msg = JSON.parse(message.data);

    switch (msg.type) {
        case 'assigned_id':
            socket.id = msg.id;
            break;
        case 'received_offer':
            console.log('received offer', msg.data);
            pc.setRemoteDescription(new RTCSessionDescription(msg.data));
            pc.createAnswer().then(function(description) {
                console.log('sending answer');
                pc.setLocalDescription(description);
                socket.send(JSON.stringify({
                    type : 'received_answer',
                    data : description
                }));
            }, null, mediaConstraints);
            break;
        case 'received_answer':
            console.log('received answer');
            if (!connected) {
                pc.setRemoteDescription(new RTCSessionDescription(msg.data));
                connected = true;
            }
            break;
        case 'received_candidate':
            console.log('received candidate');
            var candidate = new RTCIceCandidate({
                sdpMLineIndex : msg.data.label,
                candidate : msg.data.candidate
            });
            pc.addIceCandidate(candidate);
            break;
        case 'received_message':
            document.getElementById("message-list-label").append(msg.data);
            break;
        case 'connection_closed':
            console.log('peer ' + msg.peer + ' closed connection');
            break;
    }
};

var pc;
var configuration = {
    "iceServers" : [ {
        "url" : "stun:stun.l.google.com:19302"
    } ]
};

var stream;
var pc = new RTCPeerConnection(configuration);
var connected = false;
var mediaConstraints = {
    'mandatory' : {
        'OfferToReceiveAudio' : true,
        'OfferToReceiveVideo' : true
    }
};

pc.onicecandidate = e => {
    if (e.candidate) {
        if(!isOpen(socket)){return}
        socket.send(JSON.stringify({
            type : 'received_candidate',
            data : {
                label : e.candidate.sdpMLineIndex,
                id : e.candidate.sdpMid,
                candidate : e.candidate.candidate
            }
        }));
    };
};

pc.onaddstream = e => {
    console.log('start remote video stream');
    vid2.srcObject = e.stream;
    vid2.play();
};

pc.sendmessage = e => {
    e.preventDefault();
    let text =  userName + ": " + document.getElementById("message-form-input").value + '\r\n';
    document.getElementById("message-form-input").value = "";
    console.log('message');
    socket.send(JSON.stringify({
        type : 'received_message',
        data: text
    }));
    document.getElementById("message-list-label").append(text);
    return false;
};

broadcast = () => {
    navigator.getUserMedia({
        audio : true,
        video : true
    }, s => {
        stream = s;
        pc.addStream(s);

        var binaryData = [];
        binaryData.push(stream);
        vid1.srcObject = s;

        vid1.play();

        if (initCall){
            start();
            pc.onaddstream(s);
        }
    }, error => {
        try {
        } catch (e) {
        }
    });
};

isOpen = ws => { 
    return ws.readyState === ws.OPEN 
};

start = () => {

    pc.createOffer().then((description) => {
        pc.setLocalDescription(description);
        if(!isOpen(socket)) {return}
        socket.send(JSON.stringify({
            type : 'received_offer',
            data : description
        }));

    }, null, mediaConstraints);

};

window.onload = () => {
    broadcast();
};

window.onbeforeunload = () => {
    socket.send(JSON.stringify({
        type: 'close'
    }));
    pc.close();
};