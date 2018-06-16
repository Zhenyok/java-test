(function(){
    "use strict";
    angular.module('configVars',[]).constant('appUrls',{
        "tweets":"testAjax/tweets.json",
        "construct_img":"images/slider/developing.jpg",
        "polls":"testAjax/pollsList.json",
        "portfolio":"testAjax/portfolioList.json",
        "deleteItemUrl":"deleteItem",
        "getSettings":"getSettingsParams",
        "AddSettingUrl":"addSetting",
        "SaveChangesUrl":"saveChanges",
        "imageManagerList":"getManagerImageList",
        "SaveImageUrl":"saveImageItem",
        "DeleteImageItem":"deleteManagerImage",
        "ImageUploadUrl":"imageUploadSet",
        "ImageInfoUrl":"getImageInfo",
        "ImageCropUrl":"CropImage",
        "saveTmnlUrl":"saveTmnl",
        "tmnlListUrl":"getTmnls",
        "delTmnlUrl":"delTmnls"

    });
})();
