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

        $scope.getOneSignalement = function(idSignalement) 
        {
           
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/signalement/' + idSignalement).then(function successCallback(response) 
            {
                $scope.oneSignalement = response.data
                console.log($scope.oneSignalement);
                $scope.position.lat = $scope.oneSignalement[0][6];
                $scope.position.lon = $scope.oneSignalement[0][7];
                document.getElementById("div2").innerHTML = "<img src = 'data:image/jpg;base64," + $scope.oneSignalement[0][8] + "' alt = 'Une erreur s est produite lors du chargement de l image' style='width: 100%;height: 100%;' >";
                                                            
            },function errorCallback(response) { if (response.status == 401) { $window.location.href = 'login.html' } });
        }


        $scope.affecterSignalement = function(idSignalement) 
        {
            $http.put('https://heroku-ws-cloud.herokuapp.com/v1/affecterSignalement/' + idSignalement + '/' + $scope.idRegion).then(function successCallback(response) 
            {
                    $window.location.reload()
            },function errorCallback(response) { if (response.status == 401) { $window.location.href = 'login.html' } });
        }

        $scope.getListRegion = function(id) 
        {
            $http.get('https://heroku-ws-cloud.herokuapp.com/v1/regions').then(function successCallback(response) 
            {
                $scope.listeRegion = response.data
            },function errorCallback(response) { if (response.status == 401) { $window.location.href = 'login.html' } });
        }

        //ON LOAD  
        $scope.getOneSignalement(sessionStorage.getItem("idSignalement"));
        $scope.getListRegion();


         angular.extend($scope, {
             center: {
                 lat: -18.933333,
                 lon: 47.516667,
                 zoom: 5
             },
             position: {
                 lat:  -18.933333,
                 lon:  47.516667,
                 label: { show: false},
                 onClick: function(event, properties) {
                     alert('lat: ' + properties.lat + ', ' + 'lon: ' + properties.lon);
                 }
             }
         });

         
    }

    angular.module('myApp', ["openlayers-directive"]).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor'); }).controller('main', myCtrl)
})();