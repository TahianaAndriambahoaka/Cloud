;(function()
{

  function authInterceptor($window) 
  {
    return {
      request: function(config) 
      {
        if($window.localStorage['jwtToken'])
        {
          config.headers.Authorization = 'Bearer ' + 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZHZ3ZGZkZGJAZG1pa2puY2RrbsOpY2QiLCJpYXQiOjE2NDMwNTk4MjQsImV4cCI6MTY0MzE0NjIyNH0.mit-MuuFrQo1mh3CSbpro8KyR6zWyG8LeD9ldADsD6RRbc9JHl2MlGtW10vSMdeoFVxw9wdKnVGVPn42NKym7g';
        }
        return config;
      }
    }
  }

  function myCtrl($window,$scope,$http) 
  {
    $scope.versAffectation = function(idSignalement)
    {
      sessionStorage.setItem("usera",idSignalement);
      $window.location.href = './fiche.html';
    }
    
    $scope.affecterSignalement = function(idSignalement,idRegion)
    {
      $http.put('http://localhost:8072/affecterSignalement/'+idSignalement+'/'+idRegion).then(function successCallback(response) 
      {
        console.log(response.data);
      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
    }

    $scope.terminerSignalement = function(id)
    {
      $http.put('http://localhost:8072/terminerSignalement/'+id).then(function successCallback(response) 
      {
        console.log(response.data);
      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
    }

    $scope.supprimerSignalement = function(id)
    {
      $http.delete('http://localhost:8072/supprimerSignalement/'+id).then(function successCallback(response) 
      {
        console.log(response.data);
      }, 
      function errorCallback(response) 
      {
        if(response.status == 401){ $window.location.href = 'login.html'}
      });
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

    $scope.listAffectedSignalement = function(id)
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

    $scope.listRegions = function(id)
    {
      $http.get('http://localhost:8072/regions').then(function successCallback(response) 
      {
        console.log(response.data)
      }, 
      function errorCallback(response) 
      {
        console.log(response.status);
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
    $scope.listAffectedSignalement();
    //$scope.listRegions();
    //$scope.listNewSignalement();
    //$scope.supprimerSignalement(5);
    //$scope.terminerSignalement(9);
    //$scope.affecterSignalement(6,1);

  }

  angular.module('myApp', []).factory('authInterceptor', authInterceptor).config(function($httpProvider) { $httpProvider.interceptors.push('authInterceptor');}).controller('main', myCtrl)
})();