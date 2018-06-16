(function(){
    "use strict";
    var itApp = angular.module("commonFactory",['angularFileUpload']);
    itApp.factory('deleteItem', ['$http','$q','ajaxUrlGeneration', function($http,$q, config){
        return {
            list:function(item){
                item = parseInt(item, 10);
                if (isNaN(item) || item <= 0 ) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.get(config.getDeleteItemUrl+'/'+item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('getSettings', ['$http','$q','ajaxUrlGeneration', function($http,$q, config){
        return {
            list:function(){

                var deffered = $q.defer(),
                    httpPromise = $http.get(config.getSettingsUrl);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('addSetting', ['$http','$q','ajaxUrlGeneration', function($http,$q, config){
        return {
            list:function(item){
                if (angular.isUndefined(item)) {
                    return
                }
                var deffered = $q.defer(),
                    httpPromise = $http.post(config.getAddSettingUrl,item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('applySettings', ['$http','$q','ajaxUrlGeneration','helperFunctions', function($http,$q, config,
                                                                                                 helperFunctions){
        return {
            list:function(item){
                var itemList;
                if (angular.isUndefined(item) || item.length ==0) {
                    return
                }
                itemList = helperFunctions.imgToStr(item);
                var deffered = $q.defer(),
                    httpPromise = $http.post(config.getSaveSettings,itemList);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('imageList', ['$http','$q','ajaxUrlGeneration', function($http,$q, config){
        return {
            list:function(){
                var deffered = $q.defer(),
                    httpPromise = $http.get(config.getImageManager);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('autoUpdate',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(item){
                if (angular.isUndefined(item)) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.post(config.getImageItem,item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('helperFunctions',[function(){
        return {
            imgToJson:function(items) {
                var i = 0;
                if (angular.isUndefined(items) || items.length == 0) {
                    return;
                }
                for(i; i<items.length;i++) {
                    if(items[i].fieldType == "IMAGE") {
                        if (angular.isUndefined(items[i].value) || items[i].value.length == 0) {
                            continue;
                        }
                        items[i].value = JSON.parse(items[i].value);
                    }
                }
                return items;
            },
            imgToStr:function(items) {
                var i = 0;
                if (angular.isUndefined(items) || items.length == 0) {
                    return;
                }

                for(i; i<items.length;i++) {
                    if(items[i].fieldType == "IMAGE") {
                        if (angular.isUndefined(items[i].value) || items[i].value.length == 0) {
                            continue;
                        }
                        items[i].value = JSON.stringify(items[i].value);
                    }
                }
                return items;
            }
        };
    }]);

    itApp.factory('deleteImageManagerItem',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(item){
                if (parseInt(item, 10) <= 0) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.get(config.getImageDelete+'/'+item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('imgUploaderAdm',['$q', '$http', 'ajaxUrlGeneration', '$upload', function($q, $http, config, u){
        return {
            list:function(items, progressCl, scope){
                if (items == null || angular.isUndefined(items) || items.length == 0) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = u.upload({
                        url:config.getImageUploadItemUrl,
                        method:"POST",
                        headers: {'Content-Type': 'multipart/form-data'},
                        file:items
                    }).progress(function(evt){
                        if (angular.isFunction(progressCl)) {
                            progressCl(evt, scope);
                        }
                    });
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('getImageItemInfo', ['$http','$q','ajaxUrlGeneration', function($http,$q, config){
        return {
            list:function(item){
                item = parseInt(item, 10);
                if (isNaN(item) || item <= 0 ) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.get(config.getImageInfoUrl+'/'+item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('imageCrop',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(item){
                if (angular.isUndefined(item) && parseInt(item.id, 10) <= 0) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.post(config.getImageCropUrl,item);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    console.log(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('saveTmnl',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(tmnl){
                if (angular.isUndefined(tmnl)) {
                    return;
                }
                var deffered = $q.defer(),
                    httpPromise = $http.post(config.getTestimonialUrl,tmnl);
                httpPromise.then(function(result){
                    deffered.resolve(result);
                }, function(error){
                    deffered.reject(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('getTmnls',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(){
                var deffered = $q.defer();
                    $http.get(config.getTestimonialListUrl)
                    .then(function(result){
                        deffered.resolve(result.data.data);
                }, function(error){
                    deffered.reject(error);
                });
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('delTmnls',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(item){
                var deffered = $q.defer();
                if (angular.isNumber(item) && parseInt(item, 10) > 0) {
                    $http.get(config.getTestimonialDelUrl+'/'+item)
                        .then(function(result){
                            deffered.resolve(result.data);
                        }, function(error){
                            deffered.reject(error);
                        });
                } else if(item != null && angular.isArray(item) && item.length > 0) {
                    $http.post(config.getTestimonialDelUrl,item)
                        .then(function(result){
                            deffered.resolve(result.data);
                        }, function(error){
                            deffered.reject(error);
                        });
                } else {
                    deffered.reject();
                    return deffered.promise;
                }
                return deffered.promise;
            }
        };
    }]);

    itApp.factory('logoutService',['$q', '$http', 'ajaxUrlGeneration', function($q, $http, config){
        return {
            list:function(){
                var deffered = $q.defer();

                $http.post("/logout")
                    .then(function(result){
                        deffered.resolve(result);
                    }, function(error){
                        deffered.reject(error);
                    });

                return deffered.promise;
            }
        };
    }]);




})();