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

        .when('/adverts_edit_form', {
            templateUrl: 'adverts_edit_form.html',
            controller: 'advertsEditController'
        })
        .when('/filters', {
            templateUrl: 'filters.html',
            controller: 'filterController'
        })
        .when('/article_filter', {
            templateUrl: 'article_filter.html',
            controller: 'articleFilterController'
        })
        .when('/impressions', {
            templateUrl: 'impressions.html',
            controller: 'articleController'
        })
        .when('/article_like_ratings', {
            templateUrl: 'article_like_ratings.html',
            controller: 'articleLikeController'
        })
        .when('/article_like_ratings_new', {
            templateUrl: 'article_like_ratings_new.html',
            controller: 'articleLikeController'
        })
        .when('/article_comments', {
            templateUrl: 'article_comments.html',
            controller: 'articleCommentController'
        })
        .when('/article_comment_new', {
            templateUrl: 'article_comment_new.html',
            controller: 'articleCommentController'
        })
        .when('/article_comment_edit', {
            templateUrl: 'article_comment_edit.html',
            controller: 'articleCommentEditController'
        })
    ;

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
                console.log(response);
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
    const advertsPath = contextPath + '/api/v1/simple_adverts';

    fillTable = function () {
        $http.get(advertsPath)
            .then(function (response) {
                $scope.AdvertsList = response.data;
            });
    };

    fillTable();

    $scope.submitNew = function() {
        $http.post(advertsPath, $scope.advNew).then(function(response) {
            console.log(response);
        });
        window.location.href = contextPath + '/index.html#!/adverts';
        window.location.reload(true);
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
        $http.get(contextPath + "/index.html#!/adverts/" + ed.id, {params: {id: ed.id}});
    };

    $http.get(advertsPath + "/types")
        .then(function (response) {
            $scope.AdvertTypes  = response.data;
            console.log(response.data);
        });
});

app.controller('advertsEditController', function ($scope, $http, $routeParams) {
    const advertsPath = contextPath + '/api/v1/simple_adverts';
    $http.get(advertsPath + '/' + $routeParams.id).then(function(response) {
        $scope.advEdit = response.data;
    });

    $http.get(advertsPath + "/types")
        .then(function (response) {
            $scope.AdvertTypes  = response.data;
        });

    $scope.submitNew = function() {
        $http.post(advertsPath, $scope.advEdit).then(function(response) {
            console.log(response);
        });
        window.location.href = contextPath + '/index.html#!/adverts';
        window.location.reload(true);
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

app.controller('articleLikeController', function ($scope, $http) {
    const likePath = contextPath + '/api/v1/articles/likes';

    fillTable = function () {
        $http.get(likePath)
            .then(function (response) {
                $scope.likeList = response.data;
            });
    };

    fillTable();

    $scope.submitNew = function() {
        $http.post(likePath, $scope.likeNew).then(function (response) {
            console.log(response);
        })
            .catch(function (response) {
                console.log(response);
                alert(response.data.message);
            })
        ;
        window.location.href = contextPath + '/index.html#!/article_like_ratings';
        window.location.reload(true);
    };

    $scope.delete = function(index) {
        var del = $scope.likeList[index];

        $http.delete(likePath + "/article_id=" + del.article + "/user_id=" + del.user)
            .then(function (success) {
                $scope.likeList.splice(index, 1);
            })
            .catch(function (response) {
                console.log(response);
                alert(response.data.message);
            });
        window.location.reload(true);
    };

});

app.controller('articleController', function () {

});

app.controller('articleCommentController', function ($scope, $http) {
    const commPath = contextPath + '/api/v1/articles/comments';

    fillTable = function () {
        $http.get(commPath)
            .then(function (response) {
                $scope.commList = response.data;
            });
    };

    fillTable();

    $scope.submitNew = function () {
        $http.post(commPath, $scope.commNew)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (response) {
                console.log(response);
                alert(response.data.message);
            });
        window.location.href = contextPath + '/index.html#!/article_comments';
        window.location.reload(true);
    };

    $scope.delete = function (index) {
        var del = $scope.commList[index];

        $http.delete(commPath + "/id=" + del.id)
            .then(function (success) {
                $scope.commList.splice(index, 1);
            })
            .catch(function (response) {
                console.log(response);
                alert(response.data.message);
            });
        //window.location.href = contextPath + '/index.html#!/article_comments';
        window.location.reload(true);
    };

});

app.controller('articleCommentEditController', function ($scope, $http, $routeParams) {
    const commPath = contextPath + '/api/v1/articles/comments';
    $http.get(commPath + '/id=' + $routeParams.id).then(function(response) {
        $scope.comm = response.data;
    });

    $scope.submitUpdate = function() {
        $http.put(commPath, $scope.comm).then(function(response) {
            console.log(response);
        })
            .catch(function (response) {
                console.log(response);
                alert(response.data.message);
            });
        window.location.href = contextPath + '/index.html#!/article_comments';
        window.location.reload(true);
    };
});

app.factory('globalFactory', function () {
    return {
        varA: 'hello'
    }
});