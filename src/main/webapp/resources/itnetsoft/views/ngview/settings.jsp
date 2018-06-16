<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div data-ng-controller="settingsMediaManagerCtrl">
    <div data-ng-controller="settingParamsCtrl" class="settings">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><fmt:message key="page.settings"/></h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <jsp:directive.include file="/resources/itnetsoft/views/ngpartials/MediaManager-add.jsp"/>
        <div data-ng-controller="addSettingCtrls">
            <button class="btn btn-warning add-button" data-ng-hide="addButton" data-ng-click="addElement()"><fmt:message
                    key="page.settings.addSetting"/></button>
            <div class="row add-setting-row" data-ng-hide="addBlock">
                <div class="form-group pull-left col-md-3">
                    <label>Field type</label>
                    <select class="form-control" data-ng-model="selOpt"
                            data-ng-options="opt as opt.label for opt in options">
                    </select>
                </div>
                <div class="form-group pull-left col-md-4">
                    <label>Field Name</label>
                    <form name="fieldAdding" field-validate>
                        <input type="text" name="newField" class="form-control" data-ng-model="settingName" popover-trigger="focus"
                               popover="<fmt:message key="page.settings.name"/> "  minlength="4" required placeholder="New Setting name"
                       data-ng-class="{invalidField:(fieldAdding.newField.$dirty || fieldAdding.newField.$touched) &&
                       fieldAdding.newField.$invalid, validField:fieldAdding.newField.$dirty && fieldAdding.newField.$valid}"
                        />
                    </form>
                </div>
                <div class="form-group pull-left col-md-3 add-button-group">
                    <button class="btn" data-ng-click="addSettingItem()"
                            data-ng-class="{'btn-default':submitted,'btn-success':!submitted}">
                        <fmt:message key="page.settings.addSetting"/>
                    </button>
                    <button class="btn btn-warning" data-ng-click="CancelAdding()">Cancel</button>
                    <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
                         class="img-responsive pull-right" style="margin-top:6px" alt="" data-ng-show="loadAdding"/>
                </div>
            </div>
        </div>
        <div class="row settings-margin" data-ng-repeat="item in settings">
            <div class="form-group" data-ng-switch="settings[$index].fieldName">
                <label data-ng-switch-when="system_skype" for="Skype" class="col-sm-3 control-label"><fmt:message
                        key="page.settings.skype"/>: </label>
                <label data-ng-switch-when="system_phone" for="Phone" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.phone"/>:
                </label>
                <label data-ng-switch-when="system_phone_second" for="PhoneSecond"
                       class="col-sm-3 control-label">
                    <fmt:message key="page.settings.phone"/>:
                </label>
                <label data-ng-switch-when="system_email" for="Email" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.email"/>:
                </label>
                <label data-ng-switch-when="system_office" for="Office" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.office"/>:
                </label>
                <label data-ng-switch-when="system_address" for="Address" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.office"/>:
                </label>
                <label data-ng-switch-when="system_copyright" for="Copyright" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.copyright"/>:
                </label>
                <label data-ng-switch-when="system_tech" for="Tech" class="col-sm-3 control-label">
                    <fmt:message key="page.settings.tech"/>:
                </label>
                <label data-ng-switch-default for="{{settings[$index].fieldName}}"
                       class="col-sm-3 control-label">
                    <fmt:message key="page.settings.common"/>
                    <br/>&laquo;{{settings[$index].fieldName|replacer}}&raquo;:
                </label>

                <div class="col-md-4">
                    <input data-ng-switch-when="system_skype" type="text" class="form-control" id="Skype"
                           placeholder="<fmt:message key="page.settings.skype"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_phone" type="text" class="form-control" id="Phone"
                           placeholder="<fmt:message key="page.settings.phone"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_phone_second" type="text" class="form-control" id="PhoneSecond"
                           placeholder="<fmt:message key="page.settings.phone"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_email" type="text" class="form-control" id="Email"
                           placeholder="<fmt:message key="page.settings.email"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_office" type="text" class="form-control" id="Office"
                           placeholder="<fmt:message key="page.settings.office"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_address" type="text" class="form-control" id="Address"
                           placeholder="<fmt:message key="page.settings.office"/>" data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_copyright" type="text" class="form-control" id="Copyright"
                           placeholder="<fmt:message key="page.settings.copyright"/>"
                           data-ng-model="settings[$index].value">
                    <input data-ng-switch-when="system_tech" type="text" class="form-control" id="Tech"
                           placeholder="<fmt:message key="page.settings.tech"/>" data-ng-model="settings[$index].value">

                    <p data-ng-switch-when="system_tech" class="help-block text-right text-muted clearfix"><em>
                        <fmt:message key="page.settings.tech.helpBlock"/></em></p>

                    <div data-ng-switch-default>
                        <div data-ng-switch="settings[$index].fieldType">
                            <input data-ng-switch-when="FIELD" type="text" class="form-control"
                                   id="{{settings[$index].fieldName}}"
                                   placeholder="<fmt:message key="page.settings.field"/>"
                                   data-ng-model="settings[$index].value">
                            <textarea class="form-control" data-ng-switch-when="TEXT" id="{{settings[$index].fieldName}}"
                                      placeholder="<fmt:message key="page.settings.text"/>"
                                      data-ng-model="settings[$index].value"></textarea>

                            <textarea class="form-control" data-ng-switch-when="SNIPPET" id="{{settings[$index].fieldName}}"
                                      placeholder="<fmt:message key="page.settings.snippet"/>"
                                      data-ng-model="settings[$index].value"></textarea>

                            <div data-ng-switch-when="IMAGE" class="image-field">
                                <img data-ng-if="settings[$index].value"
                                     data-fallback="{{settings[$index]['value']['url']|imageFilter}}"
                                     data-ng-src="{{settings[$index]['value']['url']|thumbFilter}}"
                                     class="img-responsive fix-dimensions center-block" alt=""/>
                                <ul data-ng-if="settings[$index].value" class="settings-image">
                                    <li class="delete-image" data-ng-click="deleteImage($index)"><i
                                            class="glyphicon glyphicon-remove"></i></li>
                                    <li class="edit-image" data-ng-click="imageEditorShow(settings[$index]['value']['id'])">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </li>
                                </ul>
                                <button data-ng-if="!settings[$index].value" class="btn btn-success" data-ng-click="mediaManager($index)">
                                    <fmt:message key="page.settings.addImage"/> </button>
                                <div data-ng-if="settings[$index].value" class="image_alt">
                                    <p data-ng-if="settings[$index].value">
                                        <label>Title:</label>
                                        <input type="text" class="form-control"
                                              id="{{settings[$index].fieldName}}_title"
                                              data-ng-model="settings[$index]['value']['title']"
                                              placeholder="<fmt:message key="page.settings.common"/>">
                                    </p>
                                    <p data-ng-if="settings[$index].value">
                                        <label>Alt:</label>
                                        <input type="text" class="form-control"
                                               id="{{settings[$index].fieldName}}_alt"
                                               data-ng-model="settings[$index]['value']['seoTitle']"
                                               placeholder="<fmt:message key="page.settings.common"/>">
                                    </p>
                                    <p data-ng-if="settings[$index].value">
                                        <label>Caption:</label>
                                        <textarea class="form-control"
                                                  id="{{settings[$index].fieldName}}_caption"
                                                  placeholder="<fmt:message key="page.settings.common"/>"
                                                  data-ng-model="settings[$index]['value']['excerpt']">
                                        </textarea>
                                    </p>
                                    <p data-ng-if="settings[$index].value">
                                        <label>Description:</label>
                                        <textarea class="form-control"
                                                  id="{{settings[$index].fieldName}}_desc"
                                                  placeholder="<fmt:message key="page.settings.common"/>"
                                                  data-ng-model="settings[$index]['value']['seoDescription']">
                                          </textarea>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <p data-ng-if="settings[$index].fieldName|isDeletable">
                    <a class="delete-field pull-left" data-ng-click="open($index)">
                        <fmt:message key="page.settings.dynamic.delete"/>
                    </a>&nbsp;
                    <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
                         class="img-responsive pull-left preloader-{{$index}} preloader" alt=""/>
                </p>
            </div>
        </div>
        <div class="row">
            <button class="btn pull-left" data-ng-click="saveChanges()"
                    data-ng-class="{'btn-default':submitted,'btn-info':!submitted}">
                <fmt:message key="page.settings.save"/></button>
            <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
                 class="img-responsive pull-left fix-margin" alt="" data-ng-show="preloader"/>

        </div>
    </div>
</div>