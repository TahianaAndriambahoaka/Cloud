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
            console.log("jfsdjkfkasjdfka");
            $http.get('http://localhost:8072/signalement/' + idSignalement).then(function successCallback(response) {
                var photo = response.data[0][9];
                // $scope.photo = "storage/" + response.data;
                document.getElementById("pic-container").innerHTML = "<img src = 'storage/" + photo + "' alt = 'Une erreur s est produite lors du chargement de l image'>";
            });
            $http.get('http://localhost:8072/signalement/' + idSignalement).then(function successCallback(response) {
                $scope.oneSignalement = response.data
                console.log($scope.oneSignalement);
            });
        }
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
        $scope.getListRegion();



        // $scope.showDetails = function(id) {
        //     alert('lat: ' + id.lat + ', ' + 'lon: ' + id.lon);
        // };
        // angular.extend($scope, {
        //     center: {
        //         lat: -18.933333,
        //         lon: 47.516667,
        //         zoom: 9
        //     },
        //     finisterre: {
        //         lat: -18.933333,
        //         lon: 47.516667,
        //         label: {
        //             show: false
        //         },
        //         onClick: function(event, properties) {
        //             alert('lat: ' + properties.lat + ', ' + 'lon: ' + properties.lon);
        //         }
        //     }
        // });


    }

    angular.module('myApp', ["openlayers-directive"]).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor'); }).controller('main', myCtrl)
})();