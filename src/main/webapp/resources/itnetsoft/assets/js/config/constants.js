(function(){
    "use strict";
    var app = angular.module('commonConstants', []);
    app.constant('defaultSettings',[
        {
            'fieldName': 'system_skype',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_phone',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_phone_second',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_email',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_address',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_copyright',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        },
        {
            'fieldName': 'system_tech',
            'value': '',
            'fieldType': 'FIELD',
            'fieldAccess': 'SYSTEM'
        }
    ]);

    app.constant('options', [
        { label: 'Text field', value: "FIELD" },
        { label: 'Textarea', value: "TEXT" },
        { label: 'Image', value: "IMAGE" },
        { label: 'Code Snippet', value: "SNIPPET" }
    ]);
})();