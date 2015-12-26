'use strict';

describe('StudentRecord Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockStudentRecord;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockStudentRecord = jasmine.createSpy('MockStudentRecord');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'StudentRecord': MockStudentRecord
        };
        createController = function() {
            $injector.get('$controller')("StudentRecordDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'studentSystemApp:studentRecordUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
