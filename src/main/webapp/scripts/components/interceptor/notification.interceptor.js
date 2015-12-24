 'use strict';

angular.module('studentSystemApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-studentSystemApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-studentSystemApp-params')});
                }
                return response;
            }
        };
    });
