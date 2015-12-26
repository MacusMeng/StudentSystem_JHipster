'use strict';

angular.module('studentSystemApp')
    .controller('StudentRecordDetailController', function ($scope, $rootScope, $stateParams, entity, StudentRecord) {
        $scope.studentRecord = entity;
        $scope.load = function (id) {
            StudentRecord.get({id: id}, function(result) {
                $scope.studentRecord = result;
            });
        };
        var unsubscribe = $rootScope.$on('studentSystemApp:studentRecordUpdate', function(event, result) {
            $scope.studentRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
