<div data-ng-controller="fileUploadCtrl">
    <div data-ng-show="progressBar" class="progressLine center-block">
        <p class="itemHead text-left"><em>Uploading Progress:</em></p>
        <div  class="progress">
            <div class="progress-bar" role="progressbar" aria-valuenow="{{loaded}}"
                 aria-valuemin="0" aria-valuemax="100" data-ng-style={'width':loaded+'%'}>
                {{loaded}}%
            </div>
        </div>
    </div>
    <div class="text-center uploader-main uploader" ng-file-drop ng-model="uploaded" ng-multiple="true" accept="image/*"
         ng-file-change="fileDropped($files, $event, $rejectedFiles)"
            data-ng-hide="progressBar">
      <div class="uploader-inline-content has-upload-message">
        <h3 class="upload-message">No items found.</h3>
        <div class="upload-ui">
          <h3 class="upload-instructions drop-instructions">Drop files anywhere to upload</h3>
          <p class="upload-instructions drop-instructions">or</p>
          <button class="btn btn-default btn-lg" ng-file-select ng-file-change="selectedUpload($files, $event)"
                  ng-model="uploaded" multiple="true" accept="image/*"
                  resetOnClick="true"
                  >
            Select Files
          </button>
        </div>
        <div class="upload-inline-status"></div>
        <div class="post-upload-ui">
          <p class="max-upload-size">Maximum upload file size: 2 MB.</p>
        </div>
      </div>
    </div>
</div>

