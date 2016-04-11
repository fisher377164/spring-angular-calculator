/**
 * Created by fisher on 4/9/16.
 */
angular.module('calcApp')
    .controller('CalcController',
        function ($scope, $http) {

            /* Controller handles calculations and binding
             */

            // Bound to the output display
            $scope.output = "0";

            // Used to evaluate whether to start a new number
            // in the display and when to concatenate
            $scope.newNumber = true;

            // Holds the pending operation so calculate knows
            // what to do
            $scope.pendingOperation = null;

            // Bound to the view to display a token indicating
            // the current operation
            $scope.operationToken = "";

            // Holds the running total as numbers are added/subtracted
            $scope.runningTotal = null;

            // Holds the number value of the string in the display output
            $scope.pendingValue = null;

            // Tells calculate what to do when the equals buttons is clicked repeatedly
            $scope.lastOperation = null;

            // Constants
            var ADD = "adding";
            var SUBTRACT = "subtracting";
            var ADD_TOKEN = "+";
            var SUBTRACT_TOKEN = "-";
            var MULT = "MULTIPLICATION";
            var DIV = "DIVISION";
            var MULT_TOKEN = "*";
            var DIV_TOKEN = "/";


            setOutput = function (outputString) {
                $scope.output = outputString;
                $scope.newNumber = true;
            };

            setOperationToken = function (operation) {
                if (operation == ADD) {
                    $scope.operationToken = ADD_TOKEN;
                } else if (operation == SUBTRACT) {
                    $scope.operationToken = SUBTRACT_TOKEN;
                } else if (operation == MULT) {
                    $scope.operationToken = MULT_TOKEN;
                } else if (operation == DIV) {
                    $scope.operationToken = DIV_TOKEN;
                } else {
                    $scope.operationToken = "";
                }
            };

            /* Converts a string to a number so we can
             * perform calculations. Simply multiplies
             * by one to do so
             */
            toNumber = function (numberString) {
                var result = 0;
                if (numberString) {
                    result = numberString * 1;
                }
                return result;
            };


            $scope.updateOutput = function (btn) {
                if ($scope.output == "0" || $scope.newNumber) {
                    $scope.output = btn;
                    $scope.newNumber = false;
                } else {
                    $scope.output += String(btn);
                }
                $scope.pendingValue = toNumber($scope.output);
            };

            function calcPressOperationAndNoSecondArg(token) {
                data.leftOperand = $scope.runningTotal;
                data.rightOperand = $scope.pendingValue;
                data.operation = token;
                sendPost(data);
            }

            function pressOperation(param) {
                if ($scope.pendingValue) {
                    if ($scope.runningTotal && $scope.pendingOperation == ADD) {
                        calcPressOperationAndNoSecondArg(ADD_TOKEN);
                    } else if ($scope.runningTotal && $scope.pendingOperation == SUBTRACT) {
                        calcPressOperationAndNoSecondArg(SUBTRACT_TOKEN);
                    } else if ($scope.runningTotal && $scope.pendingOperation == MULT) {
                        calcPressOperationAndNoSecondArg(MULT_TOKEN);
                    } else if ($scope.runningTotal && $scope.pendingOperation == DIV) {
                        calcPressOperationAndNoSecondArg(DIV_TOKEN);
                    } else {
                        $scope.runningTotal = $scope.pendingValue;
                        setOutput(String($scope.runningTotal));
                    }
                }
                setOperationToken(param);
                $scope.pendingOperation = param;
                $scope.newNumber = true;
                $scope.pendingValue = null;
            }

            $scope.add = function () {
                pressOperation(ADD);
            };

            $scope.multiplication = function () {
                pressOperation(MULT);
            };


            $scope.subtract = function () {
                pressOperation(SUBTRACT);
            };

            $scope.division = function () {
                pressOperation(DIV);
            };


            function successCallback(response) {
                setOutput(response.data.result);
                $scope.runningTotal = response.data.result;
            }

            function errorCallback(response) {
                console.log('Error.');
                console.log(response);
            }

            function sendPost(data) {
                var url = '/api/calculate';
                $http.post(url, data).then(
                    successCallback,
                    errorCallback
                );
            }

            var data = {
                leftOperand: null,
                rightOperand: null,
                operation: null
            };

            function calcPressEqlAndNoSecondArg(token) {
                if ($scope.runningTotal) {
                    data.operation = token;
                    data.leftOperand = $scope.runningTotal;
                    data.rightOperand = $scope.lastValue;
                    sendPost(data);
                } else {
                    $scope.runningTotal = 0;
                }
            }

            function calcPressEqlAndHasSecondArg(token, operation) {
                data.leftOperand = $scope.runningTotal;
                data.rightOperand = $scope.pendingValue;
                data.operation = token;
                sendPost(data);
                setOperationToken();
                $scope.pendingOperation = null;
                $scope.pendingValue = null;
                $scope.lastOperation = operation;
            }

            $scope.calculate = function () {

                if (!$scope.newNumber) {
                    $scope.pendingValue = toNumber($scope.output);
                    $scope.lastValue = $scope.pendingValue;
                }
                if ($scope.pendingOperation == ADD) {
                    calcPressEqlAndHasSecondArg(ADD_TOKEN, ADD);
                } else if ($scope.pendingOperation == SUBTRACT) {
                    calcPressEqlAndHasSecondArg(SUBTRACT_TOKEN, SUBTRACT);
                } else if ($scope.pendingOperation == MULT) {
                    calcPressEqlAndHasSecondArg(MULT_TOKEN, MULT);
                } else if ($scope.pendingOperation == DIV) {
                    calcPressEqlAndHasSecondArg(DIV_TOKEN, DIV);
                } else {
                    if ($scope.lastOperation) {
                        if ($scope.lastOperation == ADD) {
                            calcPressEqlAndNoSecondArg(ADD_TOKEN);
                        } else if ($scope.lastOperation == SUBTRACT) {
                            calcPressEqlAndNoSecondArg(SUBTRACT_TOKEN);
                        } else if ($scope.lastOperation == MULT) {
                            calcPressEqlAndNoSecondArg(MULT_TOKEN);
                        } else if ($scope.lastOperation == DIV) {
                            calcPressEqlAndNoSecondArg(DIV_TOKEN);
                        }
                    } else {
                        $scope.runningTotal = 0;
                    }
                }

            };

            $scope.clear = function () {
                $scope.runningTotal = null;
                $scope.pendingValue = null;
                $scope.pendingOperation = null;
                setOutput("0");
            };


        }
    );

