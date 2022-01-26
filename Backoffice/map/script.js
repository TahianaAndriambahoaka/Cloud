var app = angular.module("demoapp", ["openlayers-directive"]);

app.controller("DemoController", ['$scope', function($scope) {
    $scope.showDetails = function(id) { alert('lat: ' + id.lat + ', ' + 'lon: ' + id.lon); };
    angular.extend($scope, {
        center: { lat: -18.933333, lon: 47.516667, zoom: 9 },
        finisterre: { lat: -18.933333, lon: 47.516667, label: { show: false }, onClick: function(event, properties) { alert('lat: ' + properties.lat + ', ' + 'lon: ' + properties.lon); } }
    });

}]);