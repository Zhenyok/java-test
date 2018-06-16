<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row" data-ng-controller="imageEditCtrls">
  <div clas="col-md-12"><h1 class="page-header">Edit Image</h1></div>
  <div class="row set">
    <div class="col-md-9">
      <input class="form-control" data-ng-model="imageInfo.title" placeholder=""/>
    </div>
  </div>
  <div class="col-md-6">
    <div ng-jcrop="imageInfo.url|imageFilter"  selection="imageInfo.coords"></div>
  </div>

    <div class="col-md-6 pull-left">
        <div class="col-md-5 well well-lg" data-ng-controller="scaleImageSizeCtrl">
          <h4>Scale Image</h4>
          <p>Original dimensions <code>{{width}}&times;{{height}}</code></p>
          <div class="form-group col-md-5 fix-right fix">
            <input type="text" class="form-control" data-ng-change="change(imageInfo.width, 'width')"
                   data-ng-model="imageInfo.width">
          </div>
          <div class="col-md-1 fix fix-right cross">&times;</div>
          <div class="form-group col-md-5 fix fix-right">
            <input type="text" class="form-control" data-ng-change="change(imageInfo.height,'height')"
                   data-ng-model = "imageInfo.height">
          </div>
          <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
               class="img-responsive fix-margin" alt="" data-ng-show="preloadScale"/>
          <button class="btn pull-right" style="margin-left:5px;"
                  data-ng-class="{'btn-info':submitted==true,'btn-default':submitted == false}"
                  data-ng-click="scaleImage()">Scale</button>
          <button class="btn btn-default pull-right" data-ng-click="scaleCancel()">Cancel</button>

        </div>

        <div class="col-md-6 pull-right well well-lg" data-ng-controller="saveImageInfoCtrl">
          <h4>Save</h4>
          <p><i class="glyphicon glyphicon-calendar"></i> Uploaded on:
            <strong>{{imageInfo.dateUpdated|date:"MMM d, yyyy"}}</strong>
          </p>
          <p>File name: <strong>{{imageInfo.url|filterName}}</strong></p>
          <div class="form-group">
            <label>Url:</label>
            <input type="text" class="form-control" readonly
                   value="{{imageInfo.url|imageFilter|replacerQuery}}">
          </div>
          <p>File type: <strong>{{imageInfo.url|filterType}}</strong></p>
          <p>Dimensions: <strong>{{width}}&times;{{height}}</strong></p>
          <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
               class="img-responsive fix-margin" alt="" data-ng-show="preloaderSave"/>
          <button class="btn pull-right" data-ng-class="{'btn-success':submitted==true,'btn-default':submitted == false}"
                  data-ng-click="saveImageInfo()">Save</button>
        </div>

      <div class="col-md-6 fix" data-ng-controller="cropImageCtrl">
        <div class="col-md-12 well well-lg">
          <h4>Crop image</h4>
          <label class="pull-left" style="font-weight: normal;line-height:24px;padding-right:5px;">Selection:</label>
          <div class="form-group col-md-3 fix-right fix">
            <input type="text" class="form-control fix"
                   data-ng-disabled="selected"
                   data-ng-blur="coordsChange('x', imageInfo.coords[4])"
                   data-ng-model="imageInfo.coords[4]">
          </div>
          <div class="col-md-1 fix fix-right cross">&times;</div>
          <div class="form-group col-md-3 fix fix-right">
            <input type="text" class="form-control fix"
                   data-ng-disabled="selected"
                   data-ng-blur="coordsChange('y', imageInfo.coords[5])"
                   data-ng-model="imageInfo.coords[5]">
          </div>
          <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
               class="img-responsive fix-margin" alt="" data-ng-show="preloaderCrop"/>
          <button class="btn btn-info pull-right" style="margin-left:5px;"
                  data-ng-class="{'btn-success':submitted==true,'btn-default':submitted == false}"
                  data-ng-click="cropImage()">Crop</button>
          <button class="btn btn-default pull-right" data-ng-click="release()">Cancel</button>
        </div>
      </div>

    </div>
  <div class="row set">
    <div class="col-md-9">
      <div class="form-group">
        <label>Alt:</label>
        <input type="text" class="form-control" data-ng-model="imageInfo.seoTitle" placeholder=""/>
      </div>
      <div class="form-group">
        <label>Caption:</label>
        <textarea type="text" class="form-control" data-ng-model="imageInfo.excerpt" placeholder=""/></textarea>
      </div>
      <div class="form-group">
        <label>Description:</label>
        <textarea type="text" class="form-control" data-ng-model="imageInfo.seoDescription" placeholder=""/></textarea>
      </div>
    </div>
  </div>
</div>

