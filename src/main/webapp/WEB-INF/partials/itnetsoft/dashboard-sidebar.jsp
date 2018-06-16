<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar-default sidebar" role="navigation">
  <div class="sidebar-nav navbar-collapse">
    <ul class="nav" id="side-menu">
      <li>
        <a href="#/"><i class="fa fa-dashboard fa-fw"></i> <fmt:message key="menu.index"/></a>
      </li>
      <li data-ng-class="{'':isActiveLinkPage==false,'active':isActiveLinkPage==true}"
          data-ng-click="isActiveLinkPage == true?isActiveLinkPage=false:isActiveLinkPage=true">
        <a href="javascript:void(0)" ><i class="fa  fa-file-o  fa-fw"></i>
          <fmt:message key="page.pages"/>
          <span class="fa arrow"></span></a>
        <ul class="nav nav-second-level" data-ng-show="isActiveLinkPage">
          <li>
            <a href="#/pages"><fmt:message key="page.pages.all"/></a>
          </li>
          <li>
            <a href="#/addPage"><fmt:message key="page.pages.new"/></a>
          </li>
        </ul>
        <!-- /.nav-second-level -->
      </li>

      <li data-ng-class="{'':isActiveLinkTestimonials==false,'active':isActiveLinkTestimonials==true}"
          data-ng-click="isActiveLinkTestimonials == true?isActiveLinkTestimonials=false:isActiveLinkTestimonials=true">
        <a href="javascript:void(0)" ><i class="fa   fa-comments  fa-fw"></i>
          <fmt:message key="page.testimonials"/>
          <span class="fa arrow"></span></a>
        <ul class="nav nav-second-level" data-ng-show="isActiveLinkTestimonials">
          <li>
            <a href="#/testmnl"><fmt:message key="page.testimonials.all"/></a>
          </li>
          <li>
            <a href="#/addTestmnl"><fmt:message key="page.testimonials.new"/></a>
          </li>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <li>
        <a href="#/settings"><i class="fa fa-wrench fa-fw"></i> <fmt:message key="menu.settings"/></a>
      </li>
      <li>
        <a data-ng-click="logout()"><i class="glyphicon glyphicon-log-out"></i> <fmt:message key="login.logout"/></a>
      </li>
    </ul>
  </div>
  <!-- /.sidebar-collapse -->
</div>