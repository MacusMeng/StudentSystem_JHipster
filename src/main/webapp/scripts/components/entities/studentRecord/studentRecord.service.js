'use strict';

angular.module('studentSystemApp')
    .factory('StudentRecord', function ($resource) {
        return $resource('api/studentRecords/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
angular.module('studentSystemApp')
    .factory('StudentRequest', function ($resource) {
        return $resource('api/studentRecords/selected/:ids', {}, {
            'remove': { method: 'POST'}
        });
    });
angular.module('studentSystemApp')
    .service('DataStore', function () {
        this.data=[];
        this.getData= function(){
            return this.data;
        }
        this.message='';
        this.getMessage=function(){
            return this.message;
        }
    });
