;
(function() {

    function authInterceptor($window) {
        return {
            request: function(config) {
                if ($window.localStorage['jwtToken']) {

                    config.headers.Authorization = 'Bearer ' + $window.localStorage['jwtToken'];
                }
                return config;
            }
        }
    }

    function myCtrl($window, $scope, $http) {

        $scope.getOneSignalement = function(idSignalement) {
            // $http.get('http://localhost:8072/signalement/photo/' + idSignalement).then(function successCallback(response) {
            //     console.log("$scope.photo");
            //     // $scope.photo = "http://localhost:1234/a.jpg";
            //     // console.log($scope.photo);
            //     // $scope.photo = response.data;
            //     // document.getElementById('photo').innerHTML = "< img src = 'http: //localhost:1234/" + $scope.photo + "' alt = 'Une erreur s est produite lors du chargement de l image'> ";
            //     // document.getElementById("pic-container").innerHTML = "bbbbbbbbb";
            // });
            $http.get('http://localhost:8072/signalement/photo/' + idSignalement).then(function successCallback(response) {
                console.log("$scope.photo");
            });
            $http.get('http://localhost:8072/signalement/' + idSignalement).then(function successCallback(response) {
                $scope.oneSignalement = response.data
                console.log($scope.oneSignalement);
            });
        }

        // $scope.getPhotoSignalement = function(idSignalement) {
        //     $http.get('http://localhost:8072/signalement/photo/' + idSignalement).then(function successCallback(response) {
        //         // $scope.photo = "http://localhost:1234/a.jpg";
        //         console.log("$scope.photo");
        //         console.log($scope.photo);
        //         $scope.photo = response.data;
        //         // document.getElementById('photo').innerHTML = "< img src = 'http: //localhost:1234/" + $scope.photo + "' alt = 'Une erreur s est produite lors du chargement de l image'> ";
        //         // document.getElementById("pic-container").innerHTML = "bbbbbbbbb";
        //     });
        // }

        $scope.affecterSignalement = function(idSignalement) {
            // console.log("idS: "+idSignalement);
            // console.log("idReg: "+$scope.idRegion);
            $http.put('http://localhost:8072/affecterSignalement/' + idSignalement + '/' + $scope.idRegion).then(function successCallback(response) {
                    $window.location.reload()
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });
        }

        $scope.getListRegion = function(id) {
            $http.get('http://localhost:8072/regions').then(function successCallback(response) {
                    $scope.listeRegion = response.data
                },
                function errorCallback(response) {
                    console.log(response.status);
                });
        }

        //ON LOAD  
        $scope.getOneSignalement(sessionStorage.getItem("idSignalement"));
        // $scope.getPhotoSignalement(sessionStorage.getItem("idSignalement"));
        $scope.getListRegion();

    }

    angular.module('myApp', []).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor'); }).controller('main', myCtrl)
})();