(function(){
    "use strict";
    var app = angular.module('dashboardApp', ['ngRoute', 'commonFilters', 'ngAnimate', 'perfect_scrollbar', 'angularFileUpload',
        'ui.bootstrap', 'commonDirectives', 'provideConfig', 'configVars', 'commonFactory', 'commonConstants', 'ngJcrop',
        'datatables']);

    app.config(['$routeProvider', '$locationProvider', '$httpProvider', 'ajaxUrlGenerationProvider',
        'appUrls', configApp]);
    app.config(['ngJcropConfigProvider', jCropConfig]);

    function configureUrls(ajaxUrlGenerationProvider, appUrls) {
        ajaxUrlGenerationProvider.setDeleteItemUrl(appUrls.deleteItemUrl)
            .setSettingsUrl(appUrls.getSettings)
            .setAddSettingUrl(appUrls.AddSettingUrl)
            .setSaveSettingsUrl(appUrls.SaveChangesUrl)
            .setImageManagerUrl(appUrls.imageManagerList)
            .setSaveImageUrl(appUrls.SaveImageUrl)
            .setDeleteManagerImage(appUrls.DeleteImageItem)
            .setImageUploadItemUrl(appUrls.ImageUploadUrl)
            .setImageInfoUrl(appUrls.ImageInfoUrl)
            .setImageCropUrl(appUrls.ImageCropUrl)
            .setTmnlUrl(appUrls.saveTmnlUrl)
            .setTmnlListUrl(appUrls.tmnlListUrl)
            .setDelTmnlUrl(appUrls.delTmnlUrl);
    }

    function configApp($routeProvider, $locationProvider, $httpProvider, ajaxUrlGenerationProvider, appUrls) {
        configureRouter($routeProvider);
        configureUrls(ajaxUrlGenerationProvider, appUrls);
        var getTokenData = function () {
            var csrfTokenHeader = 'X-CSRF-TOKEN', token = angular.element("meta[name='_csrf']").attr('content');
            return {headerName: csrfTokenHeader, data: token};
        };
        var csrfTokenData = getTokenData();
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $httpProvider.interceptors.push(function ($q, $injector, $rootScope) {
            return {
                request: function (config) {
                    var header = getTokenData();
                    config.headers[header.headerName] = header.data;
                    return config || $q.when(config);
                },
                responseError: function (response) {
                    if (response.status == 403 || response.status == 401) {
                        var $window = $injector.get('$window');
                        $window.open('/login');
                    } else if (response.status == 404) {
                        displayNotification(1, "404 Page Not Found");
                    }
                    return $q.reject(response);
                },
                response: function (response) {
                    if (!angular.isUndefined(response) && !angular.isUndefined(response.data)
                        && !angular.isUndefined(response.data.message) && response.data.message.length > 0) {
                        displayNotification(response.data.error, response.data.message);
                    }
                    return response;
                }
            };
        });
    }

    function configureRouter($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'articles',
            controller: 'dashboardController'
        })
            .when('/settings', {
                templateUrl: 'settings',
                controller: 'settingsController'
            })
            .when('/editMedia/:id', {
                templateUrl: 'editMedia',
                controller: 'mediaEditController'
            })
            .when('/addPage', {
                templateUrl: 'pageAdd',
                controller: 'pageAddController'
            })
            .when('/testmnl',{
                templateUrl: 'testimnl',
                controller: 'viewTestimnlController'
            })
            .when('/addTestmnl',{
                templateUrl: 'addTestimnl',
                controller: 'addTestimnlController'
            })
            .otherwise({
                templateUrl: 'articles',
                controller: 'dashboardController'
            });
    }

    function displayNotification(error, message) {
        error = parseInt(error);
        if (message.length > 0) {
            if (error == 0) {
                jQuery.notify(message, {
                    "className": "info",
                    "style": "bootstrap",
                    "globalPosition": "bottom right"
                });
            } else {
                jQuery.notify(message, {
                    "className": "error",
                    "style": "bootstrap",
                    "globalPosition": "bottom right"
                });
            }
        }
    }

    function jCropConfig(jCropProvider) {
        jCropProvider.setJcropConfig({
            Opacity: 0.1,
            aspectRatio: 0
        });
    }


})();