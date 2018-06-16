<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en" data-ng-app="dashboardApp">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><fmt:message key="dashboard.welcome"/></title>
    <sec:csrfMetaTags />

    <!-- Bootstrap Core CSS -->
    <link href='<c:url value="/resources/itnetsoft/assets/css/bootstrap-dashboard.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/itnetsoft/assets/css/font-awesome.min.css"/>' rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/perfect-scrollbar.min.css"/>" />
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/timeline.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/metisMenu.css"/>">

    <link href="<c:url value="/resources/itnetsoft/assets/css/dashboard.css"/>" rel="stylesheet">

    <link href="<c:url value="/resources/itnetsoft/assets/css/jquery.Jcrop.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/itnetsoft/assets/css/wyswig.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/itnetsoft/assets/css/datatables.bootstrap.min.css"/>" rel="stylesheet">

    <script src='<c:url value="/resources/itnetsoft/assets/js/libs/jquery.1.11.0.min.js"/>'></script>
    <script src='<c:url value="/resources/itnetsoft/assets/js/libs/jquery.dataTables.min.js"/>'></script>
    <script src="<c:url value="/resources/itnetsoft/assets/js/libs/angular.js"/>"></script>
    <script src="<c:url value="/resources/itnetsoft/assets/js/libs/angular-datatables.min.js"/>"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body data-ng-controller="rootCtrl">
<div id="wrapper">
    <div data-modal-click>
        <script type="text/ng-template" id="myModalContent.html">
            <div class="modal-header">
                <h3 class="modal-title"><fmt:message key="modal.info"/></h3>
            </div>
            <div class="modal-body">
                <strong><fmt:message key="modal.delete_message"/></strong>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" ng-click="delete()"><fmt:message key="modal.yes"/></button>
                <button class="btn btn-warning" ng-click="cancel()"><fmt:message key="modal.no"/></button>
            </div>
        </script>
    </div>
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ITNetSoft</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">
                    <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <%--          <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                              </li>
                              <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                              </li>
                              <li class="divider"></li>--%>
                    <li><a data-ng-click="logout()"><i class="fa fa-sign-out fa-fw"></i><fmt:message key="login.logout"/> </a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->
        <jsp:directive.include file="/WEB-INF/partials/itnetsoft/dashboard-sidebar.jsp"/>

        <!-- /.navbar-static-side -->
    </nav>








