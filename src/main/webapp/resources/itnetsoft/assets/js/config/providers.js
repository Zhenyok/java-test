(function(){
    "use strict";
    var itApp = angular.module('provideConfig',[]);
    itApp.provider('ajaxUrlGeneration', function ajaxUrlGeneration(){
        this.setAddSettingUrl =function(url) {
            this.addSettingUrl = url;
            return this;
        };
        this.setImageManagerUrl = function(url){
            this.imageManagerUrl = url;
            return this;
        };
        this.setTmnlUrl = function(url){
            this.tmnlUrl = url;
            return this;
        };
        this.setImageCropUrl = function(url){
            this.imageCropUrl = url;
            return this;
        };
        this.setImageInfoUrl = function(url){
            this.imageInfoUrl = url;
            return this;
        };
        this.setDeleteManagerImage = function(url) {
            this.imageDeleteManager = url;
            return this;
        };
        this.setPortfolioUrl = function(text) {
            this.portfolioUrl = text;
            return this;
        };
        this.setSaveSettingsUrl = function(url) {
            this.saveSettingsUrl = url;
            return this;
        };
        this.setPollsUrl = function(url) {
            this.pollsUrl = url;
            return this;
        };
        this.setSettingsUrl = function(url) {
            this.settingsUrl = url;
            return this;
        };
        this.setDefaultImage = function(url) {
            this.defaultImg = url;
            return this;
        };
        this.setTweetsUrl = function(url) {
            this.tweetUrl = url;
            return this;
        };
        this.setDeleteItemUrl = function(url) {
            this.deleteItemUrl = url;
            return this;
        };
        this.setSaveImageUrl = function(url){
            this.saveImageItem = url;
            return this;
        };
        this.setImageUploadItemUrl = function(url){
            this.imageUploadItemUrl = url;
            return this;
        };
        this.setTmnlListUrl =function(url){
            this.tmnlListUrl = url;
            return this;
        };
        this.setDelTmnlUrl = function(url){
            this.tmnlDelUrl = url;
            return this;
        };
        this.getImageCropUrl = function(){
            var crop = this.imageCropUrl;
            return crop;
        };
        this.getDeleteManagerImage = function() {
            var imgUrl = this.imageDeleteManager;
            return imgUrl;
        };
        this.getImageUploadItemUrl = function(){
            var imgUploadItemUrl = this.imageUploadItemUrl;
            return imgUploadItemUrl;
        };
        this.getSaveImageUrl = function(){
            var imageSaveItemUrl = this.saveImageItem;
            return imageSaveItemUrl;
        };
        this.getSaveSettingsUrl = function() {
            var saveSettings = this.saveSettingsUrl;
            return saveSettings;
        };
        this.getAddSettingUrl =function() {
            var addSettingGetter = this.addSettingUrl
            return addSettingGetter;
        };
        this.getSettingsUrl = function() {
            var settings = this.settingsUrl;
            return settings;
        };
        this.getDeleteItemUrl = function() {
            var deleteUrl = this.deleteItemUrl;
            return deleteUrl;
        };
        this.getPortfolioUrl = function(){
            var portfolio = this.portfolioUrl;
            return portfolio;
        };
        this.getImageManagerUrl = function(){
            var imageList = this.imageManagerUrl;
            return imageList;
        };
        this.getPollsUrl = function(){
            var polls = this.pollsUrl;
            return polls;
        };
        this.getDefaultImage = function(){
            var url = this.defaultImg;
            return url;
        };
        this.getTweetsUrl = function(){
            var tweet = this.tweetUrl;
            return tweet;
        };

        this.getImageInfoUrl = function(){
            var imgInfo = this.imageInfoUrl;
            return imgInfo;
        };
        this.getTmnlUrl = function(){
            var tmnlUrl = this.tmnlUrl;
            return tmnlUrl;
        };
        this.getTmnlListUrl =function(){
            var tmnlGetListUrl = this.tmnlListUrl;
            return tmnlGetListUrl;
        };
        this.getDelTmnlUrl = function(){
            var tmnlGetDelUrl = this.tmnlDelUrl;
            return tmnlGetDelUrl;
        };
        this.$get = function(){
            return {
                getPortfolioUrl: this.getPortfolioUrl(),
                getPollsUrl:this.getPollsUrl(),
                getDefaultImage:this.getDefaultImage(),
                getTweetsUrl:this.getTweetsUrl(),
                getDeleteItemUrl:this.getDeleteItemUrl(),
                getSettingsUrl:this.getSettingsUrl(),
                getAddSettingUrl:this.getAddSettingUrl(),
                getSaveSettings:this.getSaveSettingsUrl(),
                getImageManager:this.getImageManagerUrl(),
                getImageItem:this.getSaveImageUrl(),
                getImageDelete:this.getDeleteManagerImage(),
                getImageUploadItemUrl:this.getImageUploadItemUrl(),
                getImageInfoUrl:this.getImageInfoUrl(),
                getImageCropUrl:this.getImageCropUrl(),
                getTestimonialUrl:this.getTmnlUrl(),
                getTestimonialListUrl:this.getTmnlListUrl(),
                getTestimonialDelUrl:this.getDelTmnlUrl()
            };
        };
     });

})();