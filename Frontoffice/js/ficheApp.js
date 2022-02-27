;(function() 
{
    function authInterceptor($window) 
    {
        return {
            request: function(config) 
            {
                if ($window.localStorage['jwtToken']) 
                {
                    config.headers.Authorization = 'Bearer ' + $window.localStorage['jwtToken'];
                }
                return config;
            }
        }
    }

    function myCtrl($window,$scope,$http,$filter) 
    {
        var str = window.location.href;
        var url = new URL(str);
        var id = url.searchParams.get("id");
        console.log(id);
        $http.get('http://localhost:8072/v1/signalement/'+id).then(function successCallback(res) 
        { 
             console.log(res.data); 
             $scope.val = res.data;          
        },function errorCallback(response) { if (response.status == 401) { $window.location.href = 'login.html' } });
    }
    angular.module('myApp',[]).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor'); }).controller('main', myCtrl)
})();