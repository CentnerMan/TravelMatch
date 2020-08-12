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

        .when('/advert', {
            templateUrl: 'advert.html',
            controller: 'advertsController'
        })

        .when('/filters', {
            templateUrl: 'filters.html',
            controller: 'filterController'
        })
        .when('/article_filter', {
            templateUrl: 'article_filter.html',
            controller: 'articleFilterController'
        });

});

app.controller('filterController', function () {

});

app.controller('articleFilterController', function ($scope, $http, globalFactory) {
    $scope.globalFactory = globalFactory;
    var stringParams = '';
    var params = [
        'page_number',
        'page_size',
        'order_direction',
        'order_by_properties',
        'id',
        'author_id',
        'category_id',
        'city_id',
        'language_id',
        'text',
        'title_equal',
        'title_contains',
        'created_equal',
        'created_before',
        'created_after',
        'updated_equal',
        'updated_before',
        'updated_after',
        'likes_equal',
        'likes_greaterOrEqual',
        'likes_lessOrEqual',
        'dislikes_equal',
        'dislikes_greaterOrEqual',
        'dislikes_lessOrEqual',
        'rating_value_count_equal',
        'rating_value_count_greaterOrEqual',
        'rating_value_count_lessOrEqual',
        'rating_equal',
        'rating_greaterOrEqual',
        'rating_lessOrEqual',
        'tags_id'
    ];

    fillTable = function () {
        var path = contextPath + '/api/v1/filter/articles'
            + (stringParams == '' ? '' : '?' + stringParams);
        console.log(path);

        $http.get(path)
            .then(function (response) {
                $scope.articleList = response.data;
            })
            .catch(function (response) {
                // console.log(e);
                alert(response.data.message);
            })
        }
        ;

    $scope.filterArticles = function () {
        stringParams = '';

        params.forEach(function (par) {
            if ($scope[par] != undefined) {
                stringParams = stringParams + (stringParams != '' ? '&' : '') + par + '=' + $scope[par];
            }
        });

        fillTable();
    };

    fillTable();
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
    const advPath = contextPath + '/api/v1/adverts';

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

        window.location.href = contextPath + '/index.html#!/adverts';
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