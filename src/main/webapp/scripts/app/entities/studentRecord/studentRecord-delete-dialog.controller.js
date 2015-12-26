'use strict';

angular.module('studentSystemApp')
	.controller('StudentRecordDeleteController', function($scope, $uibModalInstance, entity, StudentRecord) {

        $scope.studentRecord = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StudentRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
