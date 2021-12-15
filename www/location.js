var exec = require('cordova/exec');

exports.getLocation = function (success, error) {
  exec(success, error, "Location", "getLocation", []);
};
exports.watchLocation = function () {
  let watching = false;
  let lat = -111111111;
  let lon = -111111111;
  setInterval( () => {
    if(watching) return;
    watching = true;
    exec((location) => {
      if(lat != location.latitude || lon != location.longitude){
        let event = new customElements('locationUpdate',location);
        document.dispatchEvent(event);
        lat = location.latitude;
        lon = location.longitude;
      }
      watching = false;
    }, (error) => {
      console.error(err);
      watching = false;
    }, "Location", "getLocation", []);
  } ,1000);
};
