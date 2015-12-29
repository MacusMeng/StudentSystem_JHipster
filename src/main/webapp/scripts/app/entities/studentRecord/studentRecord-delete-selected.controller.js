'use strict';

angular.module('studentSystemApp')
	.controller('StudentSelectedDeleteController', function($scope, $uibModalInstance, $stateParams, StudentRequest) {
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function () {
            //$scope.$on('to-child', function(d,data) {
            //    console.log(data);
            //});
            var data=$stateParams.ids.split(',');
            console.log(data);
            StudentRequest.remove({ids: data},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
