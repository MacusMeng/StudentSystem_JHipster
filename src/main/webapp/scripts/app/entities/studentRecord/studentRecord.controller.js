'use strict';

angular.module('studentSystemApp')
    .controller('StudentRecordController', function ($scope, $state, StudentRecord,ParseLinks) {
	    var str='';
	    $scope.idSelected=false;
        $scope.studentRecords = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            StudentRecord.query({page:$scope.page - 1,size:8,sort:[$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'),'id']},function(result,headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.studentRecords = result;
            });
        };
        $scope.loadPage = function(page){
            $scope.page = page;
            $scope.loadAll();
        }
        $scope.searchCondition=function(){
            StudentRecord.query($scope.studentRecord,function(result) {
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
        $scope.check= function (id,selected) {//单选或者多选
          if (selected == true) {//选中
              str = str + id + ',';
          } else {
              str = str.replace(id + ',','');//取消选中
          }
      };
      $scope.deleteSelected= function () {// 操作CURD
          if (str.length != 0) {
              var ids=str.substring(0, str.length - 1);
              console.log(ids);
              //$scope.$broadcast('to-child', ids);
              $state.go('studentRecord.deleteSelected', {ids:ids});
          } else {//没有选择一个的时候提示
              console.log('请至少选中一条数据在操作!');
              return;
          }
      };
    });
