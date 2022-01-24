var app = angular.module('myApp', []);
  app.config(['$httpProvider', function($httpProvider) 
  {
    $httpProvider.defaults.withCredentials = true;
  }])

  app.controller('myCtrl', function($window,$scope,$http) 
  {
     /*console.log("a");

    $scope.versAffectation = function(idSignalement)
    {
      sessionStorage.setItem("usera",idSignalement);
      console.log("usera: "+ sessionStorage.getItem("usera"));
       $window.location.href = './fiche.html';
    }
    
    $scope.affecterSignalement = function(idSignalement,idRegion){
      console.log("terminer");
      console.log("idSignalement: "+id);
      console.log("idRegion: "+$scope.reg);
      $http.put("http://localhost:8072/affecterSignalement/"+idSignalement+"/"+idRegion);
      //REFRESH
      $http.get("http://localhost:8072/listNewSignalement").then(function(response) {
        $scope.listNewSignalement = response.data;
      });
    }

    $scope.terminerSignalement = function(id){
      console.log("terminer");
      console.log("idSignalement: "+id);
      $http.put("http://localhost:8072/terminerSignalement/"+id).success(function(){
           $http.get("http://localhost:8072/listAffectedSignalement").then(function(response) {
              $scope.listAffectedSignalement = response.data;
            }); 
      })
     
    }

    $scope.supprimerSignalement = function(id){
      console.log("supprimer");
      console.log("idSignalement: "+id);
      
      $http.delete("http://localhost:8072/supprimerSignalement/"+id).success(function(response){
          $http.get("http://localhost:8072/listAffectedSignalement").then(function(response) {
          $scope.listAffectedSignalement = response.data;
        });
      }); 
  
      
    }

    //Liste signalement non affecté
    $http.get("http://localhost:8072/listNewSignalement").then(function(response) {
        console.log(response.data);
        $scope.listNewSignalement = response.data;
        //console.log(response.data[0]);
    });

    //Liste signalement affecté
    $http.get("http://localhost:8072/listAffectedSignalement").then(function(response) {      
        $scope.listAffectedSignalement = response.data;
    });*/

    $http({ method: 'GET',url: 'http://localhost:8072/listAffectedSignalement',headers: { 'Authorization': 'Bearer ' + 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZHZ3ZGZkZGJAZG1pa2puY2RrbsOpY2QiLCJpYXQiOjE2NDMwNTk4MjQsImV4cCI6MTY0MzE0NjIyNH0.mit-MuuFrQo1mh3CSbpro8KyR6zWyG8LeD9ldADsD6RRbc9JHl2MlGtW10vSMdeoFVxw9wdKnVGVPn42NKym7g' ,'Content-Type': 'application/json','Accept':'application/json'}}).then(function successCallback(response) 
    {
          console.log(response.data)
    }, function errorCallback(response) {
      
          console.log(response.status);
    });

    $http({ method: 'GET',url: 'http://localhost:8072/regions',headers: { 'Authorization': 'Bearer ' + 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZHZ3ZGZkZGJAZG1pa2puY2RrbsOpY2QiLCJpYXQiOjE2NDMwNTk4MjQsImV4cCI6MTY0MzE0NjIyNH0.mit-MuuFrQo1mh3CSbpro8KyR6zWyG8LeD9ldADsD6RRbc9JHl2MlGtW10vSMdeoFVxw9wdKnVGVPn42NKym7g' ,'Content-Type': 'application/json','Accept':'application/json'}}).then(function successCallback(response) 
    {
          console.log(response.data)
    }, function errorCallback(response) {
      
          console.log(response.status);
    });


  });