'use strict';
// var App = angular.module('uploadApp',[]);
// var urlUpload = "http://localhost:8199/tm/api/v1/file";
// var urlDownload = "http://localhost:8199/tm/api/v1/file/download";

App.constant('urls', {
    FILE_URL: 'http://localhost:8199/tm/api/v1/file'
});

App.controller('uploadContr',
    ['$scope', '$rootScope', '$q', '$window', '$sce', 'uploadService', '$http', 'urls', function ($scope, $rootScope, $q, $window, $sce, uploadService, $http, urls) {
        //['$scope', '$rootScope', '$q', '$window','uploadService' , '$sce', '$http' , function($scope, $rootScope, $q, $window, $sce, uploadService, $http) {
        $scope.file = '';

        $scope.upload = function () {
            var file = $scope.file;
            console.log('upload()!:', $scope.file.name);
            uploadService.saveDoc(file);
            $scope.file = '';
            // $http.get(urls.FILE_URL).then(
            //     // Success
            //     function successCallback(response) {
            //         console.log("uploadGet:", response);
            //         $scope.docList = response.data;
            //         var json = '<pre>' + '<h5>Upload</h5>' + JSON.stringify(response, null, 4) + '<br>' + '</pre>';
            //         $scope.bindGet = $sce.trustAsHtml(json);
            //         console.log("SUCCESS Upload : ", response);
            //         console.log("SUCCESS.json Upload: ", json);
            //         // $("#btn-del").prop("disabled", false);
            //     },
            //     // Error
            //     function errorCallback(response) {
            //         alert("Error: " + response.data);
            //         console.log(response.statusText);
            //     }
            // );
        }

        $scope.downloadFile = function (filename) {
            // var deferred = $q.defer();
            console.log("download:", filename);
            let urlDownload = urls.FILE_URL + '/download' + '?fileName=' + filename;
            console.log("urlDownload:", urlDownload);
            $http.get(urlDownload)
                .then(
                    function (response) {
                        // var disposition = headers('Content-Disposition');
                        // var filename = contentDispositionParser.getFileName(disposition);
                        // var contentType = "application/octet-stream";
                        //                  console.log("url:", response.config.url.restrict);

                        // var contentType = headers['content-type'];
                        // var contentType = 'image/png';
                        var linkElement = document.createElement('a');
                        console.log("linkElement:", linkElement);
                        try {

                            linkElement.setAttribute('href', response.config.url);
                            linkElement.setAttribute("download", filename);

                            var clickEvent = new MouseEvent("click", {
                                "view": window,
                                "bubbles": true,
                                "cancelable": false
                            });

                            linkElement.dispatchEvent(clickEvent);

                        } catch (ex) {
                            console.log("exception", ex);
                        }
                        // return deferred.promise;
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                    }
                );
        };

        $scope.deleteFile = function (id) {
            // var deferred = $q.defer();
            console.log("delete:", id);
            let urlDelete = urls.FILE_URL + '/delete' + '?id=' + id;
            console.log("delete:", urlDelete);
            $http.delete(urlDelete)
                .then(
                    function (response) {
                        uploadService.getAllData();
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                    }
                );
        };
        // //call loadData when controller initialized
        // $scope.loadData();
    }
    ]);

App.
    directive('fileModel', ['$parse', function sheetController ($parse) {
        return {
            restrict: 'A',
            // replace: true,
            link: function (scope, element, attrs) {
                let model = $parse(attrs.fileModel);
                let modelSetter = model.assign;
                console.log("link:", attrs);
                element.bind('change', function (event) {
                    console.log("bind:", scope);
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                        console.log("scope_apply:", scope);
                    });
                });
                element.bind('click', function (event) {
                    console.log("bind:", scope);
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                        console.log("scope_apply:", scope);
                    });
                });
            }
        };
    }]);

App.run(function ($rootScope, $http, $sce, urls) {
    $http.get(urls.FILE_URL).then(
        // Success
        function successCallback(response) {
            $rootScope.docList = response.data;
            var json = '<pre>' + '<h5>Run</h5>' + JSON.stringify(response, null, 4) + '<br>' + '</pre>';
            $rootScope.bindGet = $sce.trustAsHtml(json);
            console.log("SUCCESS RUN: ", response);
            console.log("SUCCESS.json RUN: ", json);
        },
        // Error
        function errorCallback(response) {
            alert("Error: " + response.data);
            console.log(response.statusText);
        }
    );
});

// App.run(function($rootScope, $scope, $http) {
//     $scope.docList = [];
//         // REST URL:
//         var url = "http://localhost:8199/tm/api/v1/file";
//         $http.get(url).then(
//             // Success
//             function successCallback(response) {
//                 alert("OK");
//                 $scope.docList = response.data;
//             },
//             // Error
//             function errorCallback(response) {
//                 alert("Error: " + response.data);
//                 console.log(response.statusText);
//             }
//         );
// });
