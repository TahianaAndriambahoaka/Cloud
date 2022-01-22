// Code goes here

(function(){
angular.module("app", ["chart.js"]);
angular.module("app").controller("DemoController", DemoController);

function DemoController ($scope) {
  $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
  $scope.data = [300, 500, 100];
}  
})();
