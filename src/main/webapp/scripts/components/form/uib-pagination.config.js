'use strict';

angular.module('studentSystemApp')
    .config(function (uibPaginationConfig) {
        uibPaginationConfig.itemsPerPage = 8;
        uibPaginationConfig.maxSize = 5;
        uibPaginationConfig.boundaryLinks = true;
        uibPaginationConfig.firstText = '«';
        uibPaginationConfig.previousText = '‹';
        uibPaginationConfig.nextText = '›';
        uibPaginationConfig.lastText = '»';
    });
