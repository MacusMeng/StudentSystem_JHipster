'use strict';

angular.module('studentSystemApp')
    .controller('StudentRecordController', function ($scope, $state, StudentRecord) {

        $scope.studentRecords = [];
        $scope.loadAll = function() {
            StudentRecord.query(function(result) {
               $scope.studentRecords = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.studentRecord = {
                number: null,
                name: null,
                gender: null,
                classes: null,
                subject: null,
                scord: null,
                id: null
            };
        };
    });
