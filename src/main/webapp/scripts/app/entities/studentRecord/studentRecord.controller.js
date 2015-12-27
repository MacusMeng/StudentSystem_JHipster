'use strict';

angular.module('studentSystemApp')
    .controller('StudentRecordController', function ($scope, $state, StudentRecord) {
     	var flag='';
	    var str='';
	    $scope.idSelected=false;
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
        $scope.check= function (id,selected) {//单选或者多选
          if (selected == true) {//选中
              str = str + id + ',';
          } else {
              str = str.replace(id + ',','');//取消选中
          }
      };
      $scope.deleteSelected= function () {// 操作CURD
          if(str.length==0){//没有选择一个的时候提示
          	   console.log('请至少选中一条数据在操作!');
              return;
          }else{
          	console.log(str);
          	$state.go('studentRecord.delete',{str:str})
          }
      };
    });
