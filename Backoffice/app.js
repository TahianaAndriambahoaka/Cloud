var app = angular.module('myApp', []);
  app.controller('myCtrl', function($window,$scope,$http) {
     console.log("a");

function userService($http, API) 
{
  var self = this;

  self.register = function(username,password,nom,prenom) 
  {
    return $http.post(API + '/auth/signup',{username: username,password: password,nom: nom,prenom: prenom})
  }

  self.login = function(username, password) 
  {
    return $http.post(API + '/auth/signin', {username: username,password: password})
  };
  

  self.getNbSignalementParType = function() 
  {
    return $http.get(API + '/rechercherSignalement/getNbSignalementParType')
  };


  //self.getAllEmployees = function()
  //{
     //return $http.get("http://localhost:8072/api/v1/employees")
  //}
}

function MainCtrl(user, auth,$window) 
{
    var self = this;
    self.valiny = "koko";
    function handleRequest(res) 
    {
       self.message = res.data; 
    $scope.versAffectation = function(idSignalement){
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
      var conf = confirm("Voulez vous vraiment supprimer?");
      if(conf){
           $http.delete("http://localhost:8072/supprimerSignalement/"+id).success(function(response){
            $http.get("http://localhost:8072/listAffectedSignalement").then(function(response) {
            $scope.listAffectedSignalement = response.data;
          });
        }); 
      }
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
    });

    $http.get("http://localhost:8072/regions").then(function(response) {
        $scope.listeRegion = response.data;
    });
  });