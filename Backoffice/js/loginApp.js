;(function()
{
    function myCtrl($window,$scope,$http) 
    {
        $scope.login = function()
        {
            $http.post('http://localhost:8072/api/auth/signin',{username:$scope.username,password:$scope.password}).then(function successCallback(response) 
            {
                $window.localStorage['jwtToken'] = response.data.accessToken;
                //console.log("TOKENA: "+$window.localStorage['jwtToken']);
                $window.location.href = "accueil.html" 
            }, 
            function errorCallback(response) 
            {
                $scope.message = response.data.message ;
            });
        }
    }
    
    angular.module('myApp', []).controller('main', myCtrl)

})();