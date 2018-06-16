(function () {
    "use strict";

    var app = angular.module('dashboardApp');

    app.controller('settingsController', ['$scope', settingsController]);

    app.controller('settingParamsCtrl', ['$scope', '$rootScope', 'defaultSettings', 'getSettings', 'applySettings',
        'helperFunctions', '$window',
        settingParams]);

    app.controller('deleteFieldCtrl', ['$scope', '$modalInstance', 'item', '$rootScope', 'deleteItem', deleteField]);
    app.controller('rootCtrl', ['$scope', 'logoutService','$window', rootCtrl]);
    app.controller('addSettingCtrls', ['$scope', '$rootScope', 'addSetting', 'options', addSettingCtrls]);
    app.controller('settingsMediaManagerCtrl', ['$scope', '$rootScope', '$http', 'imageList', '$timeout', settingsMediaManagerCtrl]);

    app.controller('mediaRootCtrl', ['$scope', '$rootScope', 'deleteImageManagerItem', mediaRootCtrl]);
    app.controller('mediaBodyCtrl', ['$scope', '$rootScope', mediaBodyCtrl]);
    app.controller('infoSelectedUpdateCtrl', ['$scope', '$rootScope', 'autoUpdate', infoSelectedUpdateCtrl]);
    app.controller('fileUploadCtrl', ['$scope', '$rootScope', '$upload', 'imgUploaderAdm', '$timeout', fileUploadCtrl]);
    app.controller('mediaEditController', ['$scope', mediaEditController]);
    app.controller('imageEditCtrls', ['$scope', '$routeParams', 'getImageItemInfo', imageEditCtrls]);
    app.controller('saveImageInfoCtrl', ['$scope','autoUpdate', saveImageInfo]);
    app.controller('cropImageCtrl', ['$scope', 'imageCrop', cropImageCtrl]);
    app.controller('scaleImageSizeCtrl', ['$scope','imageCrop', scaleImageSize]);
    function mediaRootCtrl(s, r, deleteImageManagerItem) {
        s.indexToUpdate;
        s.deleteImageItem = function () {
            var i = 0;
            if (confirm('Do you really want to delete this image?')) {
                if (!angular.isUndefined(s.selected)) {
                    deleteImageManagerItem.list(s.selected.id)
                        .then(function (result) {
                            var status = result.status;
                            if (status == 200 && !angular.isUndefined(result.data) && result.data.error == 0) {
                                if (!angular.isUndefined(r.settings)) {
                                    for (i; i < r.settings.length; i++) {
                                        if (r.settings[i].fieldType == "IMAGE" && !angular.isUndefined(r.settings[i].value) &&
                                            parseInt(r.settings[i].value.id, 10) == parseInt(s.selected.id, 10)) {
                                            r.settings[i].value = "";
                                        }
                                    }
                                }

                                if (!angular.isUndefined(s.imageList)) {
                                    angular.copy(s.imageList.filter(function (el) {
                                        return !angular.equals(el, s.selected);
                                    }), s.imageList);
                                }
                                s.selected = {};
                            }
                        });
                }
            }
        };
    }


    function mediaBodyCtrl(s, r) {
        s.toggleSpinner = true;
        s.uploaded = [];
        s.rejectFiles = [];
        s.saved = false;
        s.isUpload = false;
        s.activate = function (tab) {
            tab = parseInt(tab, 10);
            if (tab == 1) {
                s.isUpload = true;
                angular.copy({}, s.selected);
            } else {
                s.isUpload = false;
            }
        };
        s.selectImg = function (index) {
            s.rejectFiles.length = 0;
            index = parseInt(index, 10);
            s.indexToUpdate = index;
            angular.copy(s.imageList[index], s.selected);
        };
        s.unselectImage = function () {
            s.indexToUpdate = '';
            angular.copy({}, s.selected);

        };

        s.updateImage = function () {
            if (!angular.isUndefined(s.selected) && !isNaN(s.selectedIndex) && !angular.isUndefined(s.selectedIndex)
                && !angular.isUndefined(r.settings[s.selectedIndex])) {
                r.settings[s.selectedIndex].value = {};
                angular.copy(s.selected, r.settings[s.selectedIndex].value);
                s.$emit("mediaManagerHide");
                angular.copy({}, s.selected);
            }
        };
    }

    function infoSelectedUpdateCtrl(s, r, autoUpdate) {
        s.itemFocus = function () {
            if (!angular.isUndefined(s.selected)) {
                angular.copy(s.selected, s.compare);
            }
            s.$parent.saved = false;
        };
        s.itemBlured = function () {
            if (!angular.isUndefined(s.selected) && !angular.equals(s.selected, s.compare)) {
                angular.copy(s.selected, s.compare);
                if (parseInt(s.selected.id, 10) == parseInt(s.imageList[s.indexToUpdate].id, 10)) {
                    angular.copy(s.selected, s.imageList[s.indexToUpdate]);
                }
                s.$parent.toggleSpinner = false;
                autoUpdate.list(s.selected)
                    .then(function (result) {

                    })
                    .finally(function () {
                        s.compare = {};
                        s.$parent.toggleSpinner = true;
                        s.$parent.saved = true;
                    });
            }
        };

    }

    function rootCtrl(s, logoutService, w) {
        s.logout = function(){
            logoutService.list().then(function(result){
                w.location.reload();
            });
        }
    }

    function deleteField(s, m, i, r, deleteItem) {
        s.delete = function () {
            var item = parseInt(i['item']), index;
            if (!angular.isUndefined(i.index)) {
                index = parseInt(i.index, 10);
            }
            if (item > 0) {
                r.$broadcast('showLoader', {item: index});
                deleteItem.list(item)
                    .then(function (result) {
                        var status = result.status;
                        if (status == 200 && !angular.isUndefined(index)) {
                            r.$broadcast('hideLoader', {item: index});
                            r.settings.splice(index, 1);
                        } else {
                            r.$broadcast('hideLoader', {item: index});
                        }
                    });
            }
            m.close();
        };

        s.cancel = function () {
            m.dismiss('cancel');
        };
    }



    function mediaEditController($scope) {

    }


    function settingsController($scope) {

    }

    function addSettingCtrls(s, r, addSetting, options) {
        s.options = options;
        s.settingName = '';
        s.selOpt = s.options[0];
        s.addButton = false;
        s.addBlock = true;
        s.loadAdding = false;
        s.submitted = false;
        s.addSettingItem = function () {
            if (!angular.isUndefined(s.settingName) && s.settingName.length > 3) {
                if (s.selOpt.value != "FIELD" && s.selOpt.value != "TEXT" && s.selOpt.value != "IMAGE" &&
                    s.selOpt.value != "SNIPPET") {
                    s.selOpt.value = "FIELD";
                }
                var transferObject = {
                    value: '',
                    fieldName: 'common_' + s.settingName.toLowerCase().replace(/\s/g, '_'),
                    fieldType: s.selOpt.value,
                    fieldAccess: 'COMMON'
                };
                s.loadAdding = true;
                if (s.submitted == false) {
                    s.submitted = true;
                    addSetting.list(transferObject)
                        .then(function (result) {
                            var status = result.status, fields;
                            if (!angular.isUndefined(result.data) && !angular.isUndefined(result.data.data) &&
                                result.data.data != null) {
                                fields = result.data.data;
                            }
                            if (status == 200) {
                                if (!angular.isUndefined(fields)) {
                                    r.settings.push(fields);
                                }
                                s.CancelAdding();
                            }
                        })
                        .finally(function () {
                            s.loadAdding = false;
                            s.submitted = false;
                            s.$broadcast('untouchFields');
                        });
                }
            }
        };
        s.CancelAdding = function () {
            s.selOpt = s.options[0];
            s.settingName = '';
            s.addButton = false;
            s.addBlock = true;
            s.$broadcast('untouchFields');
        };
        s.addElement = function () {
            s.addButton = true;
            s.addBlock = false;
        };

    }

    function settingParams(s, r, defaultSettings, getSettings, applySettings, helperFunctions, $window) {
        r.settings = [];
        s.preloader = false;
        s.submitted = false;
        getSettingsList(r, getSettings, defaultSettings, helperFunctions);

        s.imageEditorShow = function (id) {
            mediaEditTabOpen($window, id);
        };

        s.saveChanges = function () {
            s.preloader = true;
            if (s.submitted == false) {
                s.submitted = true;
                applySettings.list(r.settings)
                    .then(function (result) {
                        var status = result.status, fields;
                        if (!angular.isUndefined(result.data.data) && result.data.data != null) {
                            fields = result.data.data;
                        }
                        if (status == 200) {
                            if (!angular.isUndefined(fields) && fields.length > 0) {
                                fields = helperFunctions.imgToJson(fields);
                                angular.copy(fields, r.settings);
                            }
                        }
                    })
                    .finally(function () {
                        s.preloader = false;
                        s.submitted = false;
                    });
            }
        };

        s.deleteImage = function (item) {
            item = parseInt(item, 10);
            r.settings[item].value = '';
        };
        s.open = function (index) {
            index = parseInt(index, 10);
            if (angular.isUndefined(r.settings[index].fieldAccess) || r.settings[index].fieldAccess != 'COMMON') {
                return;
            }
            if (angular.isUndefined(r.settings[index].id) || parseInt(r.settings[index].id, 10) <= 0) {
                r.settings.splice(index, 1);
                return;
            }
            r.$broadcast('triggerModal', {item: parseInt(r.settings[index].id, 10), index: index});

        };

    }

    function getSettingsList(r, getSettings, defaultSettings, helperFunctions) {
        getSettings.list()
            .then(function (result) {
                var status = result.status, fields;
                if (!angular.isUndefined(result.data.data) && result.data.data != null) {
                    fields = result.data.data;
                }
                if (status == 200) {
                    if (angular.isUndefined(fields) || fields.length == 0) {
                        angular.copy(defaultSettings, r.settings);
                    } else {
                        fields = helperFunctions.imgToJson(fields);
                        angular.copy(fields, r.settings);
                    }
                }
            });
    }


    function settingsMediaManagerCtrl(s, r, h, imageList, t) {
        s.imageList = [];
        s.selected = {};
        s.compare = {};
        s.progressBar = false;
        s.loaded = 0;
        s.emptyList = false;
        s.selectedIndex;
        s.mediaManager = function (index) {
            angular.copy({}, s.selected);
            if (angular.isUndefined(index) || isNaN(index)) {
                return;
            }
            index = parseInt(index, 10);
            if (angular.isUndefined(r.settings[index]) || r.settings[index].fieldType != "IMAGE") {
                return;
            }
            s.selectedIndex = index;
            r.$broadcast('showLoader', {item: index});
            imageList.list()
                .then(function (result) {
                    var status = result.status, fields;
                    if (!angular.isUndefined(result.data.data) && result.data.data != null) {
                        fields = result.data.data;
                    }
                    if (status == 200) {
                        if (angular.isUndefined(fields) || fields.length == 0) {
                            s.emptyList = true;
                        } else {
                            angular.copy(fields, s.imageList);
                        }
                    } else {
                        s.emptyList = true;
                    }
                })
                .finally(function () {
                    r.$broadcast('hideLoader', {item: index});
                    s.$broadcast("mediaManagerTrigger", {item: index});
                });


        };

    }

    function fileUploadCtrl(s, r, u, imgUploaderAdm, $timeout) {
        var i = 0;
        s.accept = true;
        s.upload = true;
        s.fileDropped = function (files, event, rejected) {
            uploadImages(s, r, files, rejected, u, imgUploaderAdm, $timeout);

        };
        s.selectedUpload = function (files, event) {
            var reject = [];
            reject = files.filter(function (el) {
                if (!angular.isUndefined(el.type)) {
                    return !el.type.match(/image\/*/);
                }
                return true;
            });
            files = files.filter(function (el) {
                if (!angular.isUndefined(el.type)) {
                    return el.type.match(/image\/*/);
                }
                return false;
            });
            uploadImages(s, r, files, reject, u, imgUploaderAdm, $timeout);
        };
    }

    function uploadImages(scope, rootScope, files, rejected, upload, imgUploaderAdm, $timeout) {
        if (!angular.isUndefined(files) && angular.isArray(files) && files.length > 0) {
            uploadInfo(scope, rootScope, files, imgUploaderAdm, $timeout);
        }
        if (!angular.isUndefined(rejected) && angular.isArray(rejected) && rejected.length > 0) {
            angular.copy({}, scope.selected);
            scope.rejectFiles.length = 0;
            var i = 0;
            for (i; i < rejected.length; i++) {
                scope.rejectFiles.push(rejected[i].name);
            }
            scope.activate(2);
        }
    }

    function uploadInfo(scope, rootScope, files, imgUploaderAdm, $timeout) {
        imgUploaderAdm.list(files, progressCallback, scope).then(function (result) {
            $timeout(function () {
                if (!angular.isUndefined(result.data) && result.data != null) {
                    if (!angular.isUndefined(result.data.data) && result.data.data.length > 0) {
                        var i = 0;
                        for (i; i < result.data.data.length; i++) {
                            scope.imageList.unshift(result.data.data[i]);

                        }
                        scope.$parent.isUpload = false;
                        scope.progressBar = false;
                    }
                }
            });
        });
    }

    function progressCallback(event, scope) {
        scope.progressBar = true;
        scope.loaded = parseInt((100.0 * event.loaded / event.total), 10);
    }

    function mediaEditTabOpen(window, id) {
        id = parseInt(id, 10);
        if (window != null && !angular.isUndefined(window) && id > 0) {
            window.open('#editMedia/' + id, '_blank');
            window.location.reload();
        }
    }


    function imageEditCtrls(s, param, getImageItemInfo) {
        s.imageInfo = {};
        s.selected = true;
        s.submitted = true;
        s.preloadScale = false;
        s.preloaderSave = false;
        s.preloaderCrop = false;
        s.width = 0, s.height = 0;
        s.initCoords = [0, 0, 0, 0, 0, 0];
        s.imageInfo.coords = [0, 0, 0, 0, 0, 0];
        getImageItemInfo.list(param.id).then(function (result) {
            if (result.status == 200 && !angular.isUndefined(result.data)) {
                if (!angular.isUndefined(result.data.data)) {
                    angular.copy(result.data.data, s.imageInfo);
                    s.width = s.imageInfo.width;
                    s.height = s.imageInfo.height;
                }
            }
        });
        s.$on("selectedDisable", function(evt, data){
            if (!angular.isUndefined(data)) {
                if(data=="off") {
                    s.selected = true;
                } else if(data == "on") {
                    s.selected = false;
                }
            }
        });

        s.change = function (dimension, type) {
            scaleSize(dimension, s.imageInfo, s.width, s.height, type);
        };
        s.coordsChange = function(type, dimension){
            dimension = parseInt(dimension, 10);
            if (type == "x") {
                if (dimension <= s.width) {
                    s.imageInfo.coords[4] = dimension;
                    s.imageInfo.coords[2] =Math.round(s.imageInfo.coords[0]+dimension);
                    s.$broadcast("reSelected", s.imageInfo.coords);
                }
            } else if(type == "y") {
                if (dimension <= s.height) {
                    s.imageInfo.coords[5] = dimension;
                    s.imageInfo.coords[3] = Math.round(s.imageInfo.coords[1]+dimension);
                    s.$broadcast("selection");
                }
            }
        };

    }

    function scaleImageSize(s, imageCrop) {
        s.scaleCancel = function () {
            s.imageInfo.width = s.width;
            s.imageInfo.height = s.height;
        };

        s.scaleImage = function () {
            if (s.imageInfo.width > 0  && s.imageInfo.height > 0
                && (s.imageInfo.width < s.width || s.imageInfo.height < s.height)) {
                if (s.submitted == true) {
                    releaseCrop(s);
                    s.preloadScale = true;
                    s.submitted = false;
                    imageCrop.list({
                        id:parseInt(s.imageInfo.id, 10),
                        width:s.imageInfo.width,
                        height:s.imageInfo.height,
                        editType:"scale"
                    }).then(function(result){
                        if(result.status == 200 && !angular.isUndefined(result.data)) {
                            if (!angular.isUndefined(result.data.data)) {
                                angular.copy(result.data.data, s.imageInfo);
                                s.imageInfo.url = s.imageInfo.url+"?"+new Date();
                                s.$parent.width = s.imageInfo.width;
                                s.$parent.height = s.imageInfo.height;
                            }
                        }
                    }).finally(function(){
                        s.submitted = true;
                        s.preloadScale = false;
                    });
                }
            }

        };
    }

    function cropImageCtrl(s, imageCrop){
        s.cropImage = function () {
            if (angular.isArray(s.imageInfo.coords) && s.imageInfo.coords[4] != 0 && s.imageInfo.coords[5] != 0
                && !angular.equals(s.initCoords, s.imageInfo.coords)) {
                if (parseInt(s.imageInfo.id, 10) > 0 && s.submitted == true) {
                    s.submitted = false;
                    s.preloaderCrop = true;
                    imageCrop.list({
                        id:parseInt(s.imageInfo.id, 10),
                        xCoord:s.imageInfo.coords[0],
                        yCoord:s.imageInfo.coords[1],
                        width:s.imageInfo.coords[4],
                        height:s.imageInfo.coords[5],
                        editType:"crop"
                    }).then(function(result){
                        if (result.status ==200 && !angular.isUndefined(result.data)) {
                            if(result.data.data != null && !angular.isUndefined(result.data.data)) {
                                angular.copy(result.data.data, s.imageInfo);
                                s.imageInfo.url = s.imageInfo.url+"?"+new Date();
                                s.$parent.width = s.imageInfo.width;
                                s.$parent.height = s.imageInfo.height;
                            }
                        }
                    }).finally(function(){
                        s.preloaderCrop = false;
                        s.submitted = true;
                        s.$parent.selected = true;
                    });
                }
            }
        };
        s.release = function () {
            releaseCrop(s);
        };
    }

    function releaseCrop(scope) {
        scope.imageInfo.coords = [0, 0, 0, 0, 0,0];
        scope.$parent.$broadcast("jcropRelease");
    }

    function saveImageInfo(s, autoUpdate) {
        s.saveImageInfo = function(){
            if (s.submitted == true) {
                releaseCrop(s);
                s.submitted = false;
                s.preloaderSave = true;
                s.imageInfo.url = s.imageInfo.url.replace(/(\?(.+)?)$/,"");
                autoUpdate.list(s.imageInfo)
                    .then(function(result){
                    }).finally(function(){
                        s.preloaderSave = false;
                        s.submitted = true;
                    });
            }
        };
    }

    function scaleSize(dimension, imageInfo, width, height, type) {
        var ratio = 0.00;
        dimension = parseInt(dimension, 10);
        if (dimension > 0 && !angular.isUndefined(imageInfo) && width > 0 && height > 0) {
            if (angular.equals(type,"height")) {
                if (dimension > height) {
                    imageInfo.height = height;
                    imageInfo.width = width;
                } else {
                    if (dimension < height) {
                        imageInfo.width = Math.round((dimension/height)*width);
                    } else {
                        imageInfo.width = width;
                    }

                }
            } else if(angular.equals(type,"width")) {
                if (dimension > width) {
                    imageInfo.width = width;
                    imageInfo.height = height;
                } else {
                    if (dimension < width) {
                        imageInfo.height = Math.round((dimension/width)*height);
                    }
                    else {
                        imageInfo.height = height;
                    }
                }
            }

        }
    }

})();


