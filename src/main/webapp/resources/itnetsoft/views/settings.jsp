<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:directive.include file="/WEB-INF/partials/itnetsoft/dashboard-header.jsp"/>
<!--Sidebar-->
    <jsp:directive.include file="/WEB-INF/partials/itnetsoft/dashboard-sidebar.jsp"/>
<!--/Sidebar-->
    <!-- Page Content -->
    <div id="page-content-wrapper" data-ng-app="settingsApp" data-ng-cloack>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1>Settings page</h1>
                    <div class="bs-callout bs-callout-info">

                        <blockquote class="color-set">
                            <p>Information.</p>
                            <footer>Here you can add common settings for all application!</footer>
                        </blockquote>
                        <div class="add-setting" data-ng-controller="addSettingCtrl">
                            <div class="btn-group pull-left">
                                <button class="btn btn-md btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                                    <span data-ng-hide="isChosen">Choose field type</span><span data-ng-show="isChosen">{{selected}}</span> <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu reset-background" role="menu">
                                    <li data-ng-repeat="field in fieldTypes" data-key="{{field.key}}" data-ng-click="chooseItem($index)">{{field.value}}</li>
                                </ul>
                            </div>
                            <div class="btn-group pull-left setting_name">
                                <input type="text" class="form-control" placeholder="Settings field name" name="fieldname"/>
                            </div>
                            <button class="pull-left btn btn-success btn-md add-btn" data-ng-click="addSetting()">Add Settings Field</button>
                        </div>
                        <div class="setting-list">
                            <div class="form-group" data-ng-switch = "apply">{{apply}}
                                <div data-ng-switch-when="FIELD"><input name="field"  value=""/></div>
                                <div data-ng-switch-when="TEXT"><textarea name="text"></textarea></div>
                            </div>
                        </div>
                    </div>

                    <%--<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>--%>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

<jsp:directive.include file="/WEB-INF/partials/itnetsoft/dashboard-footer.jsp"/>