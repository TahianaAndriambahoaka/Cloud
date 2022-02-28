;(function()
{
    function myCtrl($window,$scope,$http) 
    {
        $scope.login = function()
        {
            $http.post('https://heroku-ws-cloud.herokuapp.com/v1/auth/signin',{email:$scope.email,password:$scope.password}).then(function successCallback(response) 
            {
                $window.localStorage['jwtToken'] = response.data.accessToken;

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