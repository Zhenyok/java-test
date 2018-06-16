(function(){
    "use strict";
    var app = angular.module('commonFilters',[]);
    app.filter('replacer', applyFilter);
    app.filter('isDeletable', isDeletable);
    app.filter('imageFilter', imageFilter);
    app.filter('filterName', filterName);
    app.filter('thumbFilter',thumbFilter);
    app.filter('filterType', filterType);
    app.filter('replacerQuery', replacerQuery);
    app.filter('cutString', cutString);
    function applyFilter() {
        return function(input) {
            var result;
            result = angular.lowercase(input);
            result = result.replace(/common_/g,"");
            return ucfirst(result);
        };
    }

    function imageFilter(){
        return function(input){
            if(angular.isUndefined(input) || input.length == 0) {
                return;
            }
          return "/uploads/"+input;
        };
    }
    function filterType() {
        return function(input){
            if(angular.isUndefined(input)) {
                return;
            }
            input = input.split(".");
            if (input.length == 0) {
                return;
            }
            return input[input.length - 1].replace(/(\?(.+)?)$/,"");
        };
    }

    function isDeletable() {
        return function(input) {
            return input.substr(0,7).indexOf("common_")!=-1;
        }
    }


    function ucfirst(result) {
        return result[0].toUpperCase()+result.slice(1);
    }

    function filterName(){
        return function(input) {
            if (!angular.isUndefined(input)) {
                var result = input.split("/");
                if (result.length > 0) {
                    return result[result.length-1].replace(/(\?(.+)?)$/,"");
                }
                return input;
            }
            return input;
        }
    }

    function thumbFilter() {
        return function(input){
            if (angular.isUndefined(input)) {
                return input;
            }
            var result = input.split('.');
            return "/uploads/"+result[0]+"_200x200."+result[1];
        };
    }

    function replacerQuery() {
        return function(input) {
          if(angular.isUndefined(input)) {
              return;
          }
            return input.replace(/(\?(.+)?)$/,"");
        };
    }

    function cutString() {
        return function(input){
            if (angular.isUndefined(input)) {
                return;
            }
            if(input.length >= 100) {
                return input.substring(0,97)+"...";
            }
            return input;
        };
    }
})();