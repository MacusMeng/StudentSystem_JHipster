'use strict';

angular.module('studentSystemApp')
    .controller('StudentRecordController', function ($scope, $state, StudentRecord, ParseLinks, DataStore) {
        var str = '';
        var idArray = [];
        var flag = '';
        $scope.idSelected = false;
        $scope.studentRecords = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function () {
            StudentRecord.query({
                page: $scope.page - 1,
                size: 8,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.studentRecords = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        }
        $scope.searchCondition = function () {
            StudentRecord.query($scope.studentRecord, function (result) {
                $scope.studentRecords = result;
                $scope.refresh();
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
        $scope.selectAll = function (master) {
            if (master == true) {
                $scope.idSelected = true;
                idArray = _.map($scope.studentRecords, 'id');
            } else {
                $scope.idSelected = false;
                idArray = [];
            }
            flag = 'a';
        };
        $scope.check = function (id, selected) {//单选或者多选
            if (flag == 'a') {
                str=idArray.join(',')+',';
            }
            if (selected == true) {//选中
                str = str + id + ',';
            } else {
                str = str.replace(id + ',', '');//取消选中
            }
            idArray=(str.substring(0,str.length-1)).split(',');
        };
        $scope.deleteSelected = function () {// 操作CURD
            if (idArray.length==0||idArray[0]=="") {
                if(idArray.length>1){
                    idArray.shift();
                    DataStore.data=idArray;
                    DataStore.message='删除所选择的信息吗?';
                    $state.go('studentRecord.deleteSelected');
                }
                DataStore.message='请至少选中一条数据在操作!';
                return;
            } else {//没有选择一个的时候提示
                DataStore.data=idArray;
                DataStore.message='删除所选择的信息吗?';
                $state.go('studentRecord.deleteSelected');
            }
        };
    });
