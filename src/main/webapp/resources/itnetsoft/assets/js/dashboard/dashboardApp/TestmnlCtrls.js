(function(){
    "use strict";
    var app = angular.module('dashboardApp');
    app.controller('addTestimnlController',['$scope','saveTmnl', addTestimnlController]);
    app.controller('viewTestimnlController',['$scope','$rootScope', 'getTmnls','delTmnls', 'saveTmnl',
        'DTOptionsBuilder', 'DTColumnDefBuilder', viewTestimnlController]);
    app.controller('tmnlDeleteCtrl',['$scope','$rootScope', '$modalInstance', 'item','delTmnls',  tmnlDeleteCtrl]);
    function addTestimnlController(s, saveTmnl){
        s.tmnl = {};
        s.submitted = false;
        s.preloader = false;
        s.saveTmnl = function(){
            if (s.submitted == false) {
                s.submitted = true;
                s.preloader = true;
                if (s.userReview.$valid) {
                    saveTmnl.list(s.tmnl).then(function(result){
                        var status = result.status;
                        if (status == 200) {
                            if (!angular.isUndefined(result.data.data) && result.data.data != null
                                    && parseInt(result.data.data.id, 10) > 0) {
                                angular.copy({},s.tmnl);
                                s.userReview.$setPristine();
                                s.userReview.$setUntouched();
                            }
                        }

                    })
                    .finally(function(){
                        s.submitted = false;
                        s.preloader = false;
                        s.userReview.submitted = false;
                        });

                } else {
                    s.userReview.submitted = true;
                }
            }
        };

    }
    function viewTestimnlController(s, r, getTmnls,delTmnls, saveTmnl, DTOptionsBuilder, DTColumnDefBuilder){
        r.tmnlArrayList = [];
        r.checkedArray = [];
        r.itemsCheckIndex = [];
        s.deleteClicked = false;
        s.options = [];
        s.columns=[];
        s.rowUpdated = -1;
        s.updateItem={};
        s.onUpdateClicked = false;
        s.options = DTOptionsBuilder.newOptions()
            .withPaginationType('full_numbers')
            .withBootstrap();

       getTmnls.list().then(function(result){
            if (!angular.isUndefined(result) && result != null && result.length > 0) {
                angular.copy(result, r.tmnlArrayList);
            }
        });

        s.checkItem = function(index) {
            var keyIndex = -1,itemSearch = r.tmnlArrayList[index];
            if (r.checkedArray.length > 0 && !angular.isUndefined(itemSearch) &&  r.itemsCheckIndex[index] == false) {
                keyIndex =jQuery.inArray(itemSearch, r.checkedArray);
                if (keyIndex != -1 && angular.equals(itemSearch, r.checkedArray[keyIndex])) {
                    r.checkedArray.splice(keyIndex,1);
                }
            } else if(!angular.isUndefined(itemSearch) &&  r.itemsCheckIndex[index] == true){
                if (!angular.isUndefined(itemSearch)) {
                    r.checkedArray.push(itemSearch);
                }
            }
        };

        s.open = function (index) {
            index = parseInt(index, 10);
            r.$broadcast('triggerModalTmnl', {item: parseInt(r.tmnlArrayList[index].id, 10), index: index});
            s.cancelUpdate();
        };


        s.$watch('uncheckAll', function(){
            s.uncheckAll();
        });
        s.uncheckAll = function(){
            r.itemsCheckIndex.length=0;
            r.checkedArray.length= 0;
        };

        s.cancelUpdate = function(){
            s.updateItem.length=0;
            s.rowUpdated = -1;
            s.onUpdateClicked = false;
        };



        s.deleteAll = function(){
            if (r.checkedArray.length > 0 && s.deleteClicked == false) {
                s.deleteClicked = true;
                s.cancelUpdate();
                delTmnls.list(r.checkedArray)
                    .then(function(result){
                        if (angular.isDefined(result) && angular.isDefined(result.error) && parseInt(result.error, 10) == 0) {
                            var k = 0, indexKey = -1;
                            for(k;k< r.checkedArray.length;k++) {
                                indexKey = jQuery.inArray(r.checkedArray[k], r.tmnlArrayList);
                                if (indexKey <= -1 || indexKey > (r.tmnlArrayList.length-1)){
                                    continue;
                                } else {
                                    if (angular.equals(r.tmnlArrayList[indexKey], r.checkedArray[k])) {
                                        r.tmnlArrayList.splice(indexKey,1);
                                    }
                                }
                            }
                            s.uncheckAll();
                        }
                    }).finally(function(){
                        s.deleteClicked = false;
                    });
            }
        };

        s.editTmnl = function(index){
            index = parseInt(index, 10);
            if (angular.isDefined(r.tmnlArrayList[index])) {
                angular.copy(r.tmnlArrayList[index], s.updateItem);
                s.rowUpdated = index;
            }
        };
        s.updateSave = function(index){
            index = parseInt(index, 10);
            if (s.rowUpdated == index && s.onUpdateClicked == false && !s.test.$invalid) {
                s.onUpdateClicked = true;
                saveTmnl.list(s.updateItem).then(function(result){
                    var status = result.status;
                    if (status == 200) {
                        if (!angular.isUndefined(result.data.data) && result.data.data != null
                            && parseInt(result.data.error, 10) == 0) {
                            angular.copy(s.updateItem, r.tmnlArrayList[index]);
                            s.test.$setPristine();
                            s.test.$setUntouched();
                        }
                    }

                })
                .finally(function(){
                    s.onUpdateClicked = false;
                    s.cancelUpdate();
                });
            }
        };
    }

    function tmnlDeleteCtrl(s, r, m, items, delTmnls) {
        s.preloader=false;
        s.delete = function () {
                s.preloader = true;
                m.close();
                delTmnls.list(items.item)
                    .then(function(result){
                        if (angular.isDefined(result) && angular.isDefined(result.error) && parseInt(result.error, 10) == 0) {
                            if (angular.isDefined(items.index) && parseInt(items.index, 10) >= 0) {
                                r.tmnlArrayList.splice(parseInt(items.index,10), 1);
                                s.$emit("uncheckAll");
                            }
                        }
                    }).finally(function(){
                        s.preloader = false;
                    });
        };

        s.cancel = function () {
            m.dismiss('cancel');
        };
    }

})();