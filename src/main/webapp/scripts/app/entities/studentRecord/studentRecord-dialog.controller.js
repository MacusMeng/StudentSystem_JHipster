'use strict';

angular.module('studentSystemApp').controller('StudentRecordDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentRecord',
        function ($scope, $stateParams, $uibModalInstance, entity, StudentRecord) {
            $scope.genders = ["男生", "女生"];
            $scope.studentRecord = entity;
            $scope.load = function (id) {
                StudentRecord.get({id: id}, function (result) {
                    $scope.studentRecord = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('studentSystemApp:studentRecordUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function () {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.studentRecord.id != null) {
                    StudentRecord.update($scope.studentRecord, onSaveSuccess, onSaveError);
                } else {
                    StudentRecord.save($scope.studentRecord, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
