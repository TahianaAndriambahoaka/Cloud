var app = angular.module("demoapp", ["openlayers-directive"]);

app.controller("DemoController", ['$scope', function($scope) {
  $scope.showDetails = function(id) { alert('lat: '+ id.lat+', '+'lon: '+id.lon); };
  angular.extend($scope, {
    center: { lat: 42.9515,lon: -8.6619, zoom: 9 },
    finisterre: { lat: 30.907800500000000000,lon: 30.265031499999964000,label: { show: false },onClick: function (event, properties) { alert('lat: '+ properties.lat+', '+'lon: '+properties.lon); } }
  });

}]);