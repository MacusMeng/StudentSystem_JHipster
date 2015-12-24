'use strict';

angular.module('studentSystemApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


