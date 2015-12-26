'use strict';

angular.module('studentSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studentRecord', {
                parent: 'entity',
                url: '/studentRecords',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'StudentRecords'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentRecord/studentRecords.html',
                        controller: 'StudentRecordController'
                    }
                },
                resolve: {
                }
            })
            .state('studentRecord.detail', {
                parent: 'entity',
                url: '/studentRecord/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'StudentRecord'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentRecord/studentRecord-detail.html',
                        controller: 'StudentRecordDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'StudentRecord', function($stateParams, StudentRecord) {
                        return StudentRecord.get({id : $stateParams.id});
                    }]
                }
            })
            .state('studentRecord.new', {
                parent: 'studentRecord',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentRecord/studentRecord-dialog.html',
                        controller: 'StudentRecordDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    number: null,
                                    name: null,
                                    gender: null,
                                    classes: null,
                                    subject: null,
                                    scord: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('studentRecord', null, { reload: true });
                    }, function() {
                        $state.go('studentRecord');
                    })
                }]
            })
            .state('studentRecord.edit', {
                parent: 'studentRecord',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentRecord/studentRecord-dialog.html',
                        controller: 'StudentRecordDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StudentRecord', function(StudentRecord) {
                                return StudentRecord.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentRecord', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('studentRecord.delete', {
                parent: 'studentRecord',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentRecord/studentRecord-delete-dialog.html',
                        controller: 'StudentRecordDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StudentRecord', function(StudentRecord) {
                                return StudentRecord.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentRecord', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
