function() {
    var env = karate.env;
    if (env == 'dev') {
        var apiURL = 'http://quick-api-dev.com:8080'
    } else if (env == 'stg') {
        var apiURL = 'http://192.168.33.10:8080'
    }
    var config = {
        apiURL: apiURL
    };

    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    return config;
}