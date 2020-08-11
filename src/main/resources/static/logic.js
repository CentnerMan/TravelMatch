var app = angular.module('app', ['ngRoute']);
var contextPath = 'http://localhost:8189/travel'

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
        .when('/dictionaries', {
            templateUrl: 'dictionaries.html'
        })

        .when('/advcategory', {
            templateUrl: 'advcategory.html',
            controller: 'advCategoryController'
        })

        .when('/adverts_create_new_form', {
            templateUrl: 'adverts_create_new_form.html',
            controller: 'advertsController'
        })

});

app.controller('usersController', function ($scope, $http, globalFactory) {
    $scope.globalFactory = globalFactory;

    fillTable = function () {
        $http.get(contextPath + '/api/v1/users')
            .then(function (response) {
                $scope.UsersList = response.data;
            });
    };

    fillTable();
});

app.controller('profileController', function ($scope, $http) {

});

app.controller('advertsController', function ($scope, $http) {
    const advertsPath = contextPath + '/api/v1/simple_adverts';

    fillTable = function () {
        $http.get(advertsPath)
            .then(function (response) {
                $scope.AdvertsList = response.data;
            });
    };

    fillTable();

    $scope.submitNew = function() {
        $http.post(contextPath + '/api/v1/adverts', $scope.advNew).then(function(response) {
            console.log(response);
        });
        // window.location.href = contextPath + '/index.html#!/adverts';
        // window.location.reload(true);
    };

    $scope.delete = function(index) {
        var del = $scope.AdvertsList[index];

        $http.delete(advertsPath + "/" + del.id)
            .then(function(success) {
                $scope.AdvertsList.splice(index, 1);
            });
    };

    $scope.edit = function(index) {
        var ed = $scope.AdvertsList[index];

        console.log(advertsPath+"/"+ed.id);
        $http.get(advertsPath+"/"+ed.id)
            .then(function (response) {
                $scope.Advert  = response.data;
                console.log($scope.Advert);
            });
    };

    $http.get(advertsPath + "/types")
        .then(function (response) {
            $scope.AdvertTypes  = response.data;
            console.log(response.data);
        });
});

app.controller('advCategoryController', function($scope, $http) {
    const advCategoryPath = contextPath + '/api/v1/advcategory';

    fillTable = function() {
        $http.get(advCategoryPath)
            .then(function(response) {
                $scope.advCategoryArray = response.data;
            });
    };

    fillTable();

    $scope.submitNew = function() {
        $http.post(advCategoryPath, $scope.advCategoryNew)
            .then(function(response) {
                $scope.advCategoryArray.push(response.data);
            });
    };

    $scope.delete = function(index) {
        var del = $scope.advCategoryArray[index];

        $http.delete(advCategoryPath + "/" + del.id)
            .then(function(success) {
                $scope.advCategoryArray.splice(index, 1);
            });
    };

    $scope.save = function(index) {
        var sav = $scope.advCategoryArray[index];

        $http.put(advCategoryPath,sav)
            .then(function(success) {
            });
    };

});

app.factory('globalFactory', function() {
    return {
        varA: 'hello'
    }
});