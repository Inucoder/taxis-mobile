var underscore = angular.module('underscore', []);
underscore.factory('_', function() {
  return window._; //Underscore must already be loaded on the page
});

var app = angular.module('taxitaxi',[]);