"use strict";

(function (){
    var app=angular.module("myApp");

    // контроллер для заказов, (здесь временно тестируем передачу токена)
    app.controller('orderController', function($scope, $http, $window){

        let token = $window.localStorage.getItem('Authorization');
        $scope.orders= [
            {
                id: 22,
                customerId: 1,
                restaurantId: 24,
                status: "SAVED",
                dateCreated: "04-10-2020 13:23:29.614+0300",
                dishes: {
                    "1": {
                        id: 64,
                        price: 0.99,
                        quantity: 5
                    },
                    "2": {
                        id: 65,
                        price: 1.99,
                        quantity: 3
                    }
                }
            },
            {
                id: 23,
                customerId: 2,
                restaurantId: 24,
                status: "SAVED",
                dateCreated: "04-10-2020 13:27:20.316+0300",
                dishes: {
                    "1": {
                        id: 66,
                        price: 0.99,
                        quantity: 5
                    },
                    "2": {
                        id: 67,
                        price: 1.99,
                        quantity: 3
                    }
                }
            }
        ]

        if (token) {
            $http.defaults.headers.common.Authorization = token;
        }
        $http.get("https://cookstarter-restaurant-service.herokuapp.com/restaurant/get/" + 1)
            .then(function(response) {
                $scope.restaurants = response.data;
                console.log("Get restaurant! restaurant id: " + response.data.id);
                console.log("Get restaurant! restaurant name: " + response.data.name);

                angular.forEach($scope.orders, function (value, key){
                let restId = value.restaurantId; // временно сохраняем пришедший индекс картинки
                console.log("в переменную restId сохранили айди ресторана - " + restId);
                value.restaurantId=response.data.name;
                console.log("Теперь значение value.restaurantId - " + value.restaurantId);
            });
                angular.forEach($scope.orders, function (value, key){
                    if (token) {
                        $http.defaults.headers.common.Authorization = token;
                    }
                    $http.get("https://cookstarter-users-service.herokuapp.com/users/customer/" + value.customerId)
                        .success(function(data){
                            console.log("Success get customer! " + data);
                            value.customerId=data;
                            console.log("Теперь значение customerId - " + value.customerId);
                        })
                        .error(function(data){
                            console.log("Error get customer! " + data);
                            $window.location.href = '#/login';
                        });

                });


            })
            .catch(function(data){
                console.log("Error get restaurants");
                $scope.fileLength=0;
            });
        // https://cookstarter-users-service.herokuapp.com/users/customer/1







    });
})();

