;(function()
{

  function authInterceptor($window) 
  {
    return {
      request: function(config) 
      {
        if($window.localStorage['jwtToken'])
        {

          config.headers.Authorization = 'Bearer ' + $window.localStorage['jwtToken'];
        }
        return config;
      }
    }
  }

  function myCtrl($window,$scope,$http) 
  {
    
    $scope.versFiche = function(idSignalement)
    {
      sessionStorage.setItem("idSignalement",idSignalement);
      //GET ONE SIGNALEMENT
      $window.location.href = './fiche.html';
    }
    
   
    $scope.getListNewSignalement = function(id)
    {
      $http.get('https://heroku-ws-cloud.herokuapp.com/v1/listNewSignalement').then(function successCallback(response) 
      {
        console.log(response.data);
        $scope.listNewSignalement = response.data;
      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
    }
    $scope.getListNewSignalement();

  }

  angular.module('myApp', []).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor');}).controller('main', myCtrl)
})();