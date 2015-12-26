'use strict';

angular.module('studentSystemApp')
    .factory('StudentRecord', function ($resource, DateUtils) {
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
