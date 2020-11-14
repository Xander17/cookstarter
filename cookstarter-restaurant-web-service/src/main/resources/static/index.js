"use strict";

(function (){
    let app=angular.module("myApp",['ngRoute', 'ngStorage']);

    app.config(function ($routeProvider){
        $routeProvider
            .when('/', {
                template: '<h3>Добро пожаловать в CookStarter!</h3>'
            })
            .when('/login', {
                templateUrl: 'auth/login.html',
                controller: 'loginController'
            })
            .when('/restaurants', {
                templateUrl: 'restaurants/restaurants.html',
                controller: 'restaurantsController'
            })
            .when('/restaurant-form', {
                templateUrl: 'restaurants/restaurant-form.html',
                controller: 'restaurantsController'
            })
            .when('/updateRestaurant-form', {
                templateUrl: 'restaurants/updateRestaurant-form.html',
                controller: 'restaurantsController'
            })
            .when('/contact-form', {
                templateUrl: 'restaurants/contact-form.html',
                controller: 'restaurantsController'
            })
            .when('/updateContact-form', {
                templateUrl: 'restaurants/updateContact-form.html',
                controller: 'restaurantsController'
            })
            .when('/menu', {
                templateUrl: 'menu/menu.html',
                controller: 'menuController'
            })
            .when('/menu-form', {
                templateUrl: 'menu/menu-form.html',
                controller: 'menuController'
            })
            .when('/update-form', {
                templateUrl: 'menu/updateDish.html',
                controller: 'updateDishController'
            })
            .when('/order', {
                templateUrl: 'orders/orders.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

    app.controller('mainController', function ($scope, $window, $http){
        // будем проверять, есть ли в localStorage-е токен, если его нет то не отображаем некоторые ссылки

        console.log("localStorage: " + $window.localStorage.getItem('Authorization'));
        // метод проверяет localStorage на наличие токена
        $scope.isLoggedIn = function () {
            return !!$window.localStorage.getItem('Authorization');

        }
        console.log("isLoggedIn: " + $scope.isLoggedIn());

        // Метод для лог-аута
        $scope.tryToLogout = function () {
            $window.localStorage.removeItem('Authorization');
            $http.defaults.headers.common.Authorization = '';
            $window.location.href = '#/';
        };

    });


})();





