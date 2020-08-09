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
        .when('/dictionaries', {
            templateUrl: 'dictionaries.html'
        })

        .when('/advcategory', {
            templateUrl: 'advcategory.html',
            controller: 'advCategoryController'
        })

        .when('/advert', {
            templateUrl: 'advert.html',
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
    const advPath="http://localhost:8189/tm/api/v1/adverts";

    fillTable = function () {
        $http.get(advPath)
            .then(function (response) {
                $scope.AdvertsList = response.data;
            });
    };

    fillTable();



    $scope.submitNew = function() {
        $http.post(advPath, $scope.advNew)
            .then(function(response) {
                $scope.AdvertsList.push(response.data);
            });

        window.location.href = 'http://localhost:8189/tm/index.html#!/adverts';
        window.location.reload(true);
    };

    $scope.delete = function(index) {
        var del = $scope.AdvertsList[index];

        $http.delete(advPath+"/"+del.id)
            .then(function(success) {
                $scope.AdvertsList.splice(index, 1);
            });
    };

    $scope.edit = function(index) {
        var ed = $scope.AdvertsList[index];

        console.log(advPath+"/"+ed.id);
        $http.get(advPath+"/"+ed.id)
            .then(function (response) {
                $scope.Advert  = response.data;
                console.log($scope.Advert);
            });

    };
});



app.controller('advCategoryController', function($scope, $http) {
    const advCategoryPath="http://localhost:8189/tm/api/v1/advcategory";

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

        $http.delete(advCategoryPath+"/"+del.id)
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