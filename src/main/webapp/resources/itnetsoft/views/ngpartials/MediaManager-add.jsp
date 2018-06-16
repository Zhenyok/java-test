    <div data-manager class="modal manager media-manager-div" role="dialog" data-ng-controller="mediaRootCtrl">
      <div class="modal-reassigned-dialog col-md-12 col-lg-12">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Select Image</h4>
          </div>
          <div class="modal-body" data-ng-controller="mediaBodyCtrl">
            <ul class="nav nav-tabs">
              <li role="presentation" class="manager-tab" data-ng-class="{active:isUpload===true}" data-ng-click="activate(1)"><a>Upload</a></li>
              <li role="presentation" class="manager-tab" data-ng-class="{active:isUpload===false}" data-ng-click="activate(2)"><a>Media Library </a></li>
            </ul>

            <div data-ng-show="isUpload" class="clearfix">
              <div class="col-md-12 col-lg-12 paddings">
                <jsp:directive.include file="/resources/itnetsoft/views/ngpartials/MediaManager-upload.jsp"/>

              </div>
            </div>
            <div data-ng-hide="isUpload" class="clearfix scroller bg-col" data-perfect-scrollbar wheel-propagation="true" wheel-speed="10"
                 min-scrollbar-length="20" use-both-wheel-axes = "false" suppress-scroll-x = "true" data-always-visible-y = "true">
              <div class="col-md-9 col-lg-9">
                <div class="text-center" data-ng-class="{'ng-show':imageList.length===0,'ng-hide':imageList.length>0}">
                  <jsp:directive.include file="/resources/itnetsoft/views/ngpartials/MediaManager-upload.jsp" />
                </div>

                <div class="row gallery" data-ng-if="imageList.length > 0">
                  <div class="image-box col-md-3" data-ng-repeat="image in imageList"
                       data-ng-class="{'selected-repl':selected && selected.id ==image.id}"
                  >
                      <a title="Deselect"  class="check" data-ng-if="selected && selected.id ==image.id"
                         ng-mouseenter="hover = true"
                         ng-mouseleave="hover = false"
                         data-ng-click="unselectImage()"
                              >
                          <i class="glyphicon text-center" ng-class="{'glyphicon-minus': hover,'glyphicon-ok':!hover}" ></i>
                      </a>
                      <div class="box-sized" data-ng-click="selectImg($index)">
                          <img data-fallback="{{image.url|imageFilter}}" data-ng-src="{{image.url|thumbFilter}}" class="pull-left select-replacer"
                               alt=""/>
                      </div>
                  </div>
                </div>
            </div>
            <div class="col-md-3 col-lg-3">
             <div data-ng-if="rejectFiles" class="upload-errors">
                 <div class="upload-error" data-ng-repeat="error in rejectFiles">
                     <span class="upload-error-label">Error</span>
                     <span class="upload-error-filename">{{error}}</span>
                     <p class="upload-error-message">Sorry, this file type is not permitted for security reasons.</p>
                 </div>
             </div>
              <div class="info-details" data-ng-if="selected.id">
                <h3 class="fix-top">
                  Attachment Details
                </h3>
                <div class="attachment-info">
                  <div class="col-md-12 fix">
                    <img draggable="false"
                         data-fallback="{{selected.url|imageFilter}}"
                         data-ng-src="{{selected.url|thumbFilter}}"
                         class="img-responsive fix pull-left">
                    <img src="<c:url value="/resources/itnetsoft/assets/images/spinner.gif"/>"
                         class="img-responsive pull-left fix-margin" alt="" data-ng-hide="toggleSpinner"/>
                    <span data-ng-if="saved"><em><fmt:message key="page.settings.message.saved"/></em></span>
                  </div>
                  <div class="clearfix bottom"></div>
                  <div>
                    <div class="details"><strong>{{selected.url|filterName}}</strong></div>
                    <div class="details">{{selected.dateUpdated|date:"MMM d, yyyy"}}</div>

                    <a class="attachment attachment-edit" data-ng-click="imageEditorShow(selected['id'])">
                        Edit Image
                    </a>
                    <a class="attachment" data-ng-click="deleteImageItem()">Delete Permanently</a>
                    <div class="compat-meta">
                    </div>
                  </div>
                </div>
                <div class="detail-info" data-ng-controller="infoSelectedUpdateCtrl">
                    <div class="form-group">
                      <label>Url:</label>
                      <input type="text" class="form-control" readonly
                             value="{{selected.url|imageFilter}}">
                    </div>
                    <div class="form-group">
                      <label>Title:</label>
                      <input type="text" class="form-control" data-ng-focus="itemFocus()" data-ng-blur="itemBlured()"
                             data-ng-model="selected.title">
                    </div>
                    <div class="form-group">
                      <label>Alt text:</label>
                      <input type="text" class="form-control" data-ng-focus="itemFocus()" data-ng-blur="itemBlured()"
                             data-ng-model="selected.seoTitle">
                    </div>
                    <div class="form-group">
                      <label>Caption:</label>
                      <textarea class="form-control" data-ng-model="selected.excerpt" data-ng-focus="itemFocus()" data-ng-blur="itemBlured()">
                      </textarea>
                    </div>
                    <div class="form-group">
                      <label>Description:</label>
                      <textarea class="form-control" data-ng-model="selected.seoDescription" data-ng-focus="itemFocus()" data-ng-blur="itemBlured()">
                      </textarea>
                    </div>
                </div>
              </div>


            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-ng-click="updateImage()">Select</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>