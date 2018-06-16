<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div data-ng-controller="pageAddController">
  <div class="pages">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header"><fmt:message key="page.pages.new"/></h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <div class="row">
      <textarea id="editor1"></textarea>
    </div>

    <script type="text/javascript">
      $(function() {
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        //bootstrap WYSIHTML5 - text editor
        $("#editor1").wysihtml5();
      });
    </script>

    </div>
  </div>