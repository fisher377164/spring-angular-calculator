/**
 * Created by fisher on 4/9/16.
 */
angular.module('calcApp')
    .config(function ($stateProvider, $urlRouterProvider, $locationProvider) {

        $locationProvider.html5Mode(true).hashPrefix('!');

        $urlRouterProvider.when('/', '');
        $urlRouterProvider.otherwise('app');
        $stateProvider
            .state('app', {
                    abstract: true,
                    views: {
                        'headers@': {
                            templateUrl: "app/header/header.html"
                        },
                        'navbar@': {
                            templateUrl: "app/navbar/navbar.html"
                        },
                        'footers@': {
                            templateUrl: "app/footer/footer.html"
                        }
                    }
                }
            )
            .state('calc', {
                url: '/',
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: "app/calc/calc.html"
                    }
                }
            })
            .state('log', {
                url: '/log',
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: "app/log/log.html"
                    }
                }
            })
    });
