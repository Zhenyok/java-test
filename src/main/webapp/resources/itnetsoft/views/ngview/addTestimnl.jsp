<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div data-ng-controller="addTestimnlController">
  <div class="settings">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header"><fmt:message key="page.testimonials.new"/></h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
  </div>
      <div class="row tmnl">
        <form name="userReview">
          <div class="form-group col-md-8 col-md-offset-2"
               ng-class="{ 'has-error' : userReview.author.$invalid && !userReview.author.$pristine
                || (userReview.author.$touched && userReview.author.$invalid)
                || userReview.submitted && userReview.author.$invalid}
                ">
            <label><fmt:message key="user.info"/> <span class="required">*</span></label>
            <input type="text" class="form-control" name="author" data-ng-model = "tmnl.author" ng-minlength="4"
                   placeholder="<fmt:message key="user.info"/>" required
              >
          </div>
          <div class="form-group col-md-8 col-md-offset-2"
               ng-class="{ 'has-error' : userReview.jobTitle.$invalid && !userReview.jobTitle.$pristine
               || (userReview.jobTitle.$touched && userReview.jobTitle.$invalid)
               || userReview.submitted && userReview.jobTitle.$invalid}">

            <label><fmt:message key="user.jobTitle"/> <span class="required">*</span></label>
            <input type="text" name="jobTitle" class="form-control" data-ng-model = "tmnl.jobTitle" ng-minlength="4"
                   placeholder="<fmt:message key="user.jobTitle"/>" required>
          </div>
          <div class="form-group col-md-8 col-md-offset-2"
               ng-class="{ 'has-error' : userReview.testimonial.$invalid && !userReview.testimonial.$pristine
               || (userReview.testimonial.$touched && userReview.testimonial.$invalid)
               || userReview.submitted && userReview.testimonial.$invalid}">
          <label><fmt:message key="user.tmnl"/> <span class="required">*</span></label>
            <textarea required name="testimonial" class="form-control" data-ng-model = "tmnl.testimonial" ng-minlength="8"
                      placeholder="<fmt:message key="user.tmnl"/>">
            </textarea>
          </div>
        </form>
      </div>
      <div class="row">
        <div class="col-md-8 col-md-offset-2">

          <button class="btn pull-right" data-ng-click="saveTmnl()"
                  data-ng-class="{'btn-info':submitted==false && userReview.$valid, 'btn-default':submitted==true || userReview.$invalid}">
            <fmt:message key="login.submit"/>
          </button>
          <img class="img-responsive pull-right delete-field fix-margin" data-ng-show="preloader" alt=""
               src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>">
        </div>
      </div>

</div>