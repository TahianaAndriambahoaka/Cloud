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
   
    $scope.versAffectation= function(){
      $window.location.href = "./affectation.html"
    }
    
    //LISTE
     $scope.getListAffectedSignalement = function()
    {
      $http.get('http://localhost:8072/listAffectedSignalement').then(function successCallback(response) 
      {
        console.log(response.data)
        $scope.listAffectedSignalement = response.data;

      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
    }
    /***************************/


    $scope.terminerSignalement = function(id)
    {
      var conf = confirm("Voulez-vous terminer ce signalement?")
      if(conf){
        $http.put('http://localhost:8072/terminerSignalement/'+id).then(function successCallback(response) 
        {
           $scope.getListAffectedSignalement()
        }, 
        function errorCallback(response) 
        {
          if(response.status == 401){ $window.location.href = 'login.html'}
        });
      }
    }

    $scope.supprimerSignalement = function(id)
    {
       var conf = confirm("Voulez-vous vraiment supprimer ce signalement?")
       if(conf){
          $http.delete('http://localhost:8072/supprimerSignalement/'+id).then(function successCallback(response) 
          {
            $scope.getListAffectedSignalement()
          }, 
          function errorCallback(response) 
          {
            if(response.status == 401){ $window.location.href = 'login.html'}
          });
       }
     
    }

    $scope.listNewSignalement = function(id)
    {
      $http.get('http://localhost:8072/listNewSignalement').then(function successCallback(response) 
      {
        console.log(response.data);
        $scope.listNewSignalement = response.data;
      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
    }

   


    $scope.insertRegion = function(nom)
    {
      $http.post('http://localhost:8072/region',{nom:nom}).then(function successCallback(response) 
      {
        console.log(response.data)
      }, 
      function errorCallback(response) 
      {
        console.log(response.status);
      });
    }

    $scope.logout = function()
    {
      $window.localStorage.removeItem('jwtToken');
      $window.location.href = "login.html";
    }

    //$scope.region='Diana';
    //$scope.insertRegion($scope.region);
    $scope.getListAffectedSignalement();
    //$scope.listRegions();
    //$scope.listNewSignalement();
    //$scope.supprimerSignalement(5);
    //$scope.terminerSignalement(9);
    //$scope.affecterSignalement(6,1);

  }

  angular.module('myApp', []).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor');}).controller('main', myCtrl)
})();