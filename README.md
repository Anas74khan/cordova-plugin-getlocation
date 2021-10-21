# cordova-plugin-getlocation

Cordova plugin to get current location of user in android.

### Supported Platform:

- Android

### Installations:

> cordova plugin add https://github.com/Anas74khan/cordova-plugin-getlocation

After installation, the Location plugin would be avilable as "Location".

### Methods:

- Get current location.

```js
let successCallback = function (location) {
  console.log(JSON.stringify(location)); //{"latitude" : "value" , "longitude" : "value"}
};
let errorCallback = function (err) {
  console.error(err);
};

Location.getLocation(successCallback, errorCallback);
```

### Release Notes:

# 0.0.1:

Initial Release
This version contains only one function getLocation.
