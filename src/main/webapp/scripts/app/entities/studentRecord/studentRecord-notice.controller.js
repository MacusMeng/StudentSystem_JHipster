'use strict';

angular.module('studentSystemApp')
	.controller('StudentRecordNoticeController', function($scope, $uibModalInstance,DataStore) {
        $scope.message=DataStore.getMessage();
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    });
