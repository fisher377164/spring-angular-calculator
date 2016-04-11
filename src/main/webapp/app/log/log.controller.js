/**
 * Created by fisher on 4/9/16.
 */
angular.module('calcApp')
    .controller('LogController',
        function Pamm($scope, $http) {

            $scope.page = 1;

            function successCallback(response) {
                $scope.logs = response.data;
            }

            function errorCallback(response) {
                console.log('Error.');
                console.log(response);
            }

            // function sendPost(data) {
                var url = '/api/getLogs/' + $scope.page;
                $http.get(url).then(
                    successCallback,
                    errorCallback
                );
            // }
        });
