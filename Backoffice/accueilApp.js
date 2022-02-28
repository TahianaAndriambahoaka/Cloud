;(function() 
{
    function authInterceptor($window) 
    {
        return {
            request: function(config) {
                if ($window.localStorage['jwtToken']) {
                    config.headers.Authorization = 'Bearer ' + $window.localStorage['jwtToken'];
                }
                return config;
            }
        }
    }

    function myCtrl($window, $scope, $http) 
    {
        $scope.versAffectation = function(idSignalement) 
        {
            sessionStorage.setItem("usera", idSignalement);
            $window.location.href = './fiche.html';
        }

        $scope.affecterSignalement = function(idSignalement, idRegion) {
            $http.put('https://heroku-ws-cloud.herokuapp.com/v1/affecterSignalement/' + idSignalement + '/' + idRegion).then(function successCallback(response) {
                console.log(response.data);
            });
        }

        $scope.versAffectation = function() {
            $window.location.href = "./affectation.html"
        }

        $scope.getListAffectedSignalement = function() {
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/listAffectedSignalement').then(function successCallback(response) {
                    console.log(response.data)
                    $scope.listAffectedSignalement = response.data;
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });
        }

        $scope.terminerSignalement = function(id) {
            $http.put('https://heroku-ws-cloud.herokuapp.com/v1/terminerSignalement/' + id).then(function successCallback(response) {
                    console.log(response.data);
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });
        }

        $scope.terminerSignalement = function(id) 
        {
            var conf = confirm("Voulez-vous terminer ce signalement?")
            if (conf) {
                $http.put('https://heroku-ws-cloud.herokuapp.com/v1/terminerSignalement/' + id).then(function successCallback(response) {
                        $scope.getListAffectedSignalement()
                    },
                    function errorCallback(response) {
                        if (response.status == 401) { $window.location.href = 'login.html' }
                    });
            }
        }

        $scope.supprimerSignalement = function(id) 
        {
            $http.delete('https://heroku-ws-cloud.herokuapp.com/v1/supprimerSignalement/' + id).then(function successCallback(response) {
                    console.log(response.data);
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });

            var conf = confirm("Voulez-vous vraiment supprimer ce signalement?")
            if (conf) {
                $http.delete('https://heroku-ws-cloud.herokuapp.com/v1/supprimerSignalement/' + id).then(function successCallback(response) {
                        $scope.getListAffectedSignalement()
                    },
                    function errorCallback(response) {
                        if (response.status == 401) { $window.location.href = 'login.html' }
                    });
            }
        }

        $scope.listNewSignalement = function(id) {
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/listNewSignalement').then(function successCallback(response) {
                    console.log(response.data);
                    $scope.listNewSignalement = response.data;
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });
        }

        $scope.listAffectedSignalement = function(id) {
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/listAffectedSignalement').then(function successCallback(response) {
                    console.log(response.data)
                    $scope.listAffectedSignalement = response.data;
                },
                function errorCallback(response) {
                    if (response.status == 401) { $window.location.href = 'login.html' }
                });
        }

        $scope.listRegions = function(id) {
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/regions').then(function successCallback(response) {
                    console.log(response.data)
                },
                function errorCallback(response) {
                    console.log(response.status);
                });
        }

        $scope.insertRegion = function(nom) {
            $http.post('https://heroku-ws-cloud.herokuapp.com/v1/region', { nom: nom }).then(function successCallback(response) {
                    console.log(response.data)
                },
                function errorCallback(response) {
                    console.log(response.status);
                });
        }

        $scope.logout = function() {
            $window.localStorage.removeItem('jwtToken');
            $window.location.href = "login.html";
        }

        //$scope.region='Diana';
        //$scope.insertRegion($scope.region);
        $scope.listAffectedSignalement();
        $scope.getListAffectedSignalement();

        //$scope.listRegions();
        //$scope.listNewSignalement();
        //$scope.supprimerSignalement(5);
        //$scope.terminerSignalement(9);
        //$scope.affecterSignalement(6,1);

    }

    angular.module('myApp', []).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor'); }).controller('main', myCtrl);
})();