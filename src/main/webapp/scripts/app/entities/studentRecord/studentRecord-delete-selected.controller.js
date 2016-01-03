'use strict';

angular.module('studentSystemApp')
	.controller('StudentSelectedDeleteController', function($scope, $uibModalInstance, $stateParams, StudentRequest,DataStore) {
        $scope.confirmMessage=DataStore.getMessage();
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function () {
            StudentRequest.remove({ids: DataStore.getData()},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
