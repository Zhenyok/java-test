<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
  <div class="settings">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header"><fmt:message key="page.testimonials.all"/></h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
  </div>

  <div class="row tmnl">
    <img class="img-responsive center-block"  alt=""
         src="<c:url value="/resources/itnetsoft/assets/images/ajax-loader.gif"/>" data-ng-show="preloader">
    <table id="dtable"
           class="table table-border col-md-12" data-datatable="ng" dt-options="options"
           dt-column-def="columns">
      <thead class="center-text ">
        <th></th>
        <th><fmt:message key="user.info"/></th>
        <th><fmt:message key="user.jobTitle"/></th>
        <th><fmt:message key="user.tmnl"/></th>
        <th><fmt:message key="tmnls.actions"/></th>
      </tr>
      </thead>
      <tbody class="tmnlBody" data-ng-form="test">
        <tr ng-repeat="tmnl in tmnlArrayList">
          <td>
            <input type="checkbox"
                     data-ng-model="itemsCheckIndex[$index]"
                     data-ng-checked="checkedArray.indexOf($index)> -1" data-ng-change="checkItem($index)">
          </td>
          <td>
            <div data-ng-hide="rowUpdated==$index">{{ tmnl.author }}</div>
            <div data-ng-show="rowUpdated==$index">
              <input type="text" required data-ng-minlength="3"
                     class="form-control" name="author_{{$index}}" placeholder="<fmt:message key="user.info"/>" data-ng-model="updateItem.author"
                     ng-class="{ 'has-error' : test.author_{{$index}}.$invalid && !test.author_{{$index}}.$pristine
               || (test.author_{{$index}}.$touched && test.author_{{$index}}.$invalid)
               || onUpdateClicked && test.author_{{$index}}.$invalid}"
                      />
            </div>
          </td>
          <td>
            <div data-ng-hide="rowUpdated==$index">{{ tmnl.jobTitle }}</div>
            <div data-ng-show="rowUpdated==$index">
              <input type="text" name="jobTitle_{{$index}}" class="form-control" placeholder="<fmt:message key="user.jobTitle"/>"
                     data-ng-model="updateItem.jobTitle" required data-ng-minlength="3"
                     ng-class="{ 'has-error' : test.jobTitle_{{$index}}.$invalid && !test.jobTitle_{{$index}}.$pristine
               || (test.jobTitle_{{$index}}.$touched && test.jobTitle_{{$index}}.$invalid)
               || onUpdateClicked && test.jobTitle_{{$index}}.$invalid}"
                      />
            </div>
          </td>
          <td>
            <div data-ng-hide="rowUpdated==$index">{{ tmnl.testimonial|cutString }}</div>
            <div data-ng-show="rowUpdated==$index">
              <textarea class="form-control table-area" placeholder="<fmt:message key="user.tmnl"/>" name="tmnl_{{$index}}"
                        data-ng-model="updateItem.testimonial" required data-ng-minlength="3"
                        ng-class="{ 'has-error' : test.tmnl_{{$index}}.$invalid && !test.tmnl_{{$index}}.$pristine
               || (test.tmnl_{{$index}}.$touched && test.tmnl_{{$index}}.$invalid)
               || onUpdateClicked && test.tmnl_{{$index}}.$invalid}"
                      >
              </textarea>
            </div>
          </td>
          <td class="col-md-2">
            <div data-ng-hide="rowUpdated==$index">
              <button type="button" class="btn btn-warning pull-left delete-field" data-ng-click="editTmnl($index)">
                <i class="glyphicon glyphicon-edit"></i>
              </button>
              <button type="button" class="btn btn-danger pull-left" data-ng-click="open($index)">
                <i class="glyphicon glyphicon-trash"></i>
              </button>
            </div>
            <div data-ng-show="rowUpdated==$index">
              <button type="button" class="btn btn-success pull-left delete-field"
                      data-ng-click="updateSave($index)"
                      data-ng-class="{'btn-success':!test.$invalid,'btn-default':test.$invalid}">
                <i class="glyphicon glyphicon-ok"></i>
              </button>
              <button type="button" class="btn btn-danger pull-left" data-ng-click="cancelUpdate()">
                <i class="glyphicon glyphicon-remove"></i>
              </button>
              <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
                   class="img-responsive pull-right fix-margin" alt="" data-ng-show="onUpdateClicked"/>
            </div>
          </td>
        </tr>
      </tbody>

    </table>
    <div class="row top">
        <div class="container">
          <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
               class="img-responsive pull-right fix-margin" alt="" data-ng-show="deleteClicked"/>
          <button class="btn pull-right" data-ng-click="deleteAll()"
                  data-ng-class="{'btn-default':deleteClicked,'btn-warning':!deleteClicked}">
            <i class="glyphicon glyphicon-trash"></i> <fmt:message key="page.deleteItems"/>
          </button>
          <button class="btn btn-success pull-right delete-field" data-ng-click="uncheckAll()">
            <i class="glyphicon glyphicon-trash"></i> <fmt:message key="page.uncheckAll"/></button>
        </div>
      </div>
  </div>

</div>