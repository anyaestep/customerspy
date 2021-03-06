/**
 * Connect to the service and process messages
 * @agrebneva
 */
runLiveFeed = function() {
  url = 'ws://0.0.0.0:61614/stomp';
  topic = '/websockets/yay';
  username = 'me';
  password = 'pass';
  if (!("WebSocket" in window)) {
    $("#lfeed").html("Your browser is too old - live feed won't work. Download chrome");
  } else {
    client = Stomp.client(url);
    var onconnect = function(frame) {
      client.subscribe(topic, function(message) {
        obj = JSON.parse(message.body);
        $("#lfeed").append(
            '<li class="span4"><a href="#" class="thumbnail"> <img src="../img/' + obj.imgsrc
                + '" height="200" width="200" alt=""> </a></li>');
      });
    };
    client.connect(username, password, onconnect);
  }
};