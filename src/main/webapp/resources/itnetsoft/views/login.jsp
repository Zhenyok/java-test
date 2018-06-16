<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><fmt:message key="login.title"/></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <link rel="shortcut icon" href="<c:url value="/resources/itnetsoft/favicon.ico"/>" type="image/x-icon">
    <link rel="icon" href="<c:url value="/resources/itnetsoft/favicon.ico"/>" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/metisMenu.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/itnetsoft/assets/css/dashboard.css"/>">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<c:choose>
<c:when test="${pageContext.request.userPrincipal.authenticated}">
    <c:url var="redirectUrl" value="/dashboard/#"/>
    <c:redirect url="${redirectUrl}"/>
</c:when>
    <c:otherwise>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger alert-dismissible toBottom" role="alert">
                            <strong><fmt:message key="login.error.title"/></strong><fmt:message key="login.error"/>
                        </div>
                    </c:if>
                    <div class="login-panel panel panel-default <c:if test="${not empty param.error}">login-panel-fix</c:if>">
                        <div class="panel-heading">
                            <h3 class="panel-title"><fmt:message key="login.title"/></h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" action="login-page" method="POST">
                                <fieldset>
                                    <div class="form-group">
                                        <label><fmt:message key="login.username"/></label>
                                        <input type="text" placeholder="<fmt:message key="login.username"/>" name="username"  class="form-control" id="username" autofocus>
                                    </div>
                                    <div class="form-group">
                                        <label><fmt:message key="login.password"/></label>
                                        <input type="password"  placeholder="<fmt:message key="login.password"/>"  name="password" class="form-control" id="password">
                                    </div>
                                    <div class="form-group">
                                        <c:out value="${recaptcha}" escapeXml="false"/>
                                    </div>
                                    <button value="submit" class="btn btn-success btn-lg btn-block" type="submit" name="submit"><fmt:message key="login.submit"/></button>
                                </fieldset>
                                <a href="<c:url value="/"/>" class="pull-right margin-top"><fmt:message key="login.toMain"/></a>
                                <sec:csrfInput/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

    <script src="<c:url value="/resources/itnetsoft/assets/js/libs/jquery.1.11.0.min.js"/>"></script>
    <script src="<c:url value="/resources/itnetsoft/assets/js/libs/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/itnetsoft/assets/js/libs/metisMenu.js"/>"></script>
</body>
</html>