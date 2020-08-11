'use strict';
var App = angular.module('uploadApp',[]);

App.factory('uploadService', ['$rootScope' ,'$http', '$q', '$sce', 'urls', function ($rootScope, $http, $q, $sce, urls) {

    var factory = {
        saveDoc: saveDoc,
        findDoc: findDoc,
        getAllData: getAllData
    };

    return factory;

    function saveDoc(file) {
        var deferred = $q.defer();
        var formData = new FormData();
        formData.append('file', file);
        console.log('saveDoc:', file);
        $http.post(urls.FILE_URL+'/upload', formData,{
            transformRequest : angular.identity,
            headers : {
                'Content-Type' : undefined
            }})
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    console.log("uploadPost:", response);
                    var json = '<pre>' + '<h5>Post</h5>' + JSON.stringify(response, null, 5) + '<br>' + '</pre>';
                    $rootScope.bindPost = $sce.trustAsHtml(json);
                    getAllData();
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    };

    function findDoc(docId) {
        var deferred = $q.defer();
        $http.get(urls.FILE_URL + '/' + docId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    };

    function getAllData() {
        var deferred = $q.defer();
        $http.get(urls.FILE_URL)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    $rootScope.docList = response.data;
                    console.log("getData:", response);
                    var json = '<pre>' + '<h5>GetAll</h5>' + JSON.stringify(response, null, 5) + '<br>' + '</pre>';
                    $rootScope.bindGet = $sce.trustAsHtml(json);
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    };
}
]);