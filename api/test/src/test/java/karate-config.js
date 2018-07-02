function () {
    var config = {
        apiURL: 'http://quick-api-dev.com:8080'
    };

    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    return config;
}