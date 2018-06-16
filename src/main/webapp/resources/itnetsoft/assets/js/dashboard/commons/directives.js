(function(){
    "use strict";
    var itApp =  angular.module('commonDirectives',['provideConfig', 'configVars', 'commonFactory']);
    itApp.directive('modalClick', ['$rootScope','$modal', modalClick]);
    itApp.directive('loader', ['$rootScope',loaderOnStartDirective]);
    itApp.directive('fieldValidate',[fieldValidate]);
    itApp.directive('manager', [mediaManagerModal]);
    itApp.directive('fallback',[fallbackUrl]);
    function modalClick(r, $modal) {
        return{
            link:function(scope, element, attr){
                scope.$on('triggerModal', function(event, data){
                    var modalInstance = $modal.open({
                        templateUrl: 'myModalContent.html',
                        controller:'deleteFieldCtrl',
                        windowClass:'modal-center modal fade in',
                        resolve:{
                            item:function(){
                                return data;
                            }
                        }
                    });
                });

                scope.$on('triggerModalTmnl', function(event, data){
                    var modalInstance = $modal.open({
                        templateUrl: 'myModalContent.html',
                        controller:'tmnlDeleteCtrl',
                        windowClass:'modal-center modal fade in',
                        resolve:{
                            item:function(){
                                return data;
                            }
                        }
                    });
                });

                scope.$on('showLoader', function(event, data){
                    if (!angular.isUndefined(data.item)) {
                        var item = parseInt(data.item);
                        angular.element('.preloader-'+item).show();
                    }
                });

                scope.$on('hideLoader', function(event, data){
                    if (!angular.isUndefined(data.item)) {
                        var item = parseInt(data.item);
                        angular.element('.preloader-'+item).hide();
                    }
                });
            }
        };
    }

    function loaderOnStartDirective ($rootScope){
        return {
            link: function(scope, el) {

                el.addClass('ng-hide');

                var unregister = $rootScope.$on('$routeChangeStart', function() {
                    el.removeClass('ng-hide');
                });
                $rootScope.$on('$routeChangeSuccess', function() {
                    el.addClass('ng-hide');
                });
                scope.$on('$destroy', unregister);
            }
        };
    }

    function fieldValidate() {
        var link = function linkFunc(scope, el, attr){

            scope.$on('untouchFields', function(){
                scope.fieldAdding.newField.$setPristine();
                scope.fieldAdding.newField.$setUntouched();
            });
        };
        return {
            link:link
        };
    }

    function mediaManagerModal() {
        return {
            link: function(scope, el) {
                scope.$on('mediaManagerTrigger', function(event, data){
                    el.modal('show');
                });
                scope.$on('mediaManagerHide', function(event, data){
                    el.modal('hide');
                });
            }
        };
    }

    function fallbackUrl() {
        return {
            link: function(scope, el, attr) {
                el.bind('error', function() {
                    angular.element(this).attr("src", attr.fallback);
                });
            }
        };
    }

})();