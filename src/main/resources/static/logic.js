var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'about.html'
        })
        .when('/users', {
            templateUrl: 'users.html',
            controller: 'usersController'
        })
        .when('/profile', {
            templateUrl: 'profile.html',
            controller: 'profileController'
        })
        .when('/adverts', {
            templateUrl: 'adverts.html',
            controller: 'advertsController'
        })
});

app.controller('usersController', function ($scope, $http, globalFactory) {
    $scope.globalFactory = globalFactory;

    fillTable = function () {
        $http.get("http://localhost:8189/travel/api/v1/users")
            .then(function (response) {
                $scope.UsersList = response.data;
            });
    };

    fillTable();
});

app.controller('profileController', function ($scope, $http) {

});

app.controller('advertsController', function ($scope, $http) {
    fillTable = function () {
        $http.get("http://localhost:8189/travel/api/v1/adverts")
            .then(function (response) {
                $scope.AdvertsList = response.data;
            });
    };

    fillTable();
});

app.factory('globalFactory', function() {
   return {
       varA: 'hello'
   }
});