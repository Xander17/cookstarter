(function (){
    "use strict";

    let app=angular.module("myApp");

    let getNumber=function(){
        return Math.floor((Math.random()*125)+1);
    };

    app.directive('fileInput', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                element.bind('change', function(){
                    $parse(attrs.fileInput)
                        .assign(scope, element[0].files)
                    scope.$apply();
                });
            }
        };
    }]);

  // ============ контроллер для ресторана ==============
    app.controller('restaurantsController', function($scope, $http, $window){

        let token = $window.localStorage.getItem('Authorization');
        let myRestaurantId = $window.localStorage.getItem('restaurantId');



        // в переменную $scope.token положили токен
        $scope.token='?Authorization=' + token;
        $scope.num='&num='+ getNumber();
        $scope.restaurant={};
        $scope.contacts= {};

        // $scope.getRestaurant=function (){  // не работает как отдельный метод - ?

        // ==============  получение ресторана ========
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.get("https://cookstarter-restaurant-service.herokuapp.com/restaurant/get/" + myRestaurantId)
                .then(function(response) {
                    $scope.restaurant = response.data;
                    console.log($scope.restaurant);
                    console.log("Get restaurant! restaurant name: " + response.data.name);
                    console.log("Get restaurant! status: " + response.status);
                    console.log($scope.editImgR);

                    $scope.fileLength = $scope.restaurant.length;
                    //пытаемся получить контакты
                    if (token) {
                        $http.defaults.headers.common.Authorization = token;
                    }
                    $http.get("https://cookstarter-restaurant-service.herokuapp.com/contact/get/" + myRestaurantId)
                        .then(function(response) {
                            console.log(response);
                            $scope.contacts = response.data;
                            $scope.contactsLength = $scope.contacts.length;
                            console.log("Success contact, status: " + response.status);
                        })
                        .catch(function(response){
                            if (response.status === 500) {
                                $window.location.href = '#/login';
                            } else {
                                console.log("Not contact: " + response.status);
                                $scope.contactsLength = 0;
                            }
                        });
                })
                .catch(function(response){
                    if (response.status === 404) {
                        console.log("404 Not found! Restaurant with id: " + myRestaurantId + " not found!");
                        $scope.fileLength = 0;
                    } else {
                        if (response.status === 403) {
                        console.log("403 Forbidden! Error checking the token!");
                        $window.location.href = '#/login';
                        } else
                            if (response.status === 500) {
                            console.log("500 Error!");
                            $window.location.href = '#/';
                        }
                    }
                });


        // }; // не работает как отдельный метод


       // ===== запрос на удаление карточки ресторана, работает ====
        $scope.deleteRestaurant=function(restaurant){
            let pictId=restaurant.pictureId;
            let restId=restaurant.id;
            console.log("Удаляем ресторан по id " +  restId);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.get("https://picture-service.herokuapp.com/picture/restaurant/api/delete/" + pictId)
                .success(function(data, status){
                    console.log("Фото ресторана успешно удалено! " + status);
                })
                .error(function(data, status){
                    console.log("Возникла ошибка при удалении фото ресторана! " + status);
                });

            $http.get("https://cookstarter-restaurant-service.herokuapp.com/restaurant/delete/" + restId)
                .then(function(response) {
                    $window.location.href = '#/restaurants';
                    console.log("Карточка ресторана удалена: " + response.status);
                })
                .catch(function(response){
                    if (response.status === 404) {
                        console.log("404 Not found! Restaurant with id: " + myRestaurantId + " not found!");
                        $scope.fileLength = 0;
                    } else {
                        if (response.status === 403) {
                            console.log("403 Forbidden! Error checking the token!");
                            $window.location.href = '#/login';
                        } else
                        if (response.status === 500) {
                            console.log("Возникла ошибка при удалении карточки ресторана!");
                            $window.location.href = '#/';
                        }
                    }
                });
        };
//====================================================================================================
        // для формы ресторана

        $scope.isUploadImj=false; // картинку еще не загружали

        //========= загрузка картинки ============
        $scope.uploadFile=function (){
            let conf={
                transformRequest:angular.identity,
                headers : {'Content-Type': undefined}
            };

            let fd = new FormData();
            angular.forEach($scope.files, function (file){
                fd.append('file', file);
            });

        // послали запрос на сохраниние картинки в бд, получили айди картинки
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            if ($scope.editImgR) { // если обновлять картинку
                $http.post("https://picture-service.herokuapp.com/picture/restaurant/api/update/"+$scope.restaurant.pictureId, fd, conf)
                    .success(function (d){
                        console.log(d);
                        console.log($scope.restaurant.pictureId);
                        $scope.isUploadImj=true;
                    })
                    .error(function (d){
                        console.log(d);
                    });
            } else {
                $http.post("https://picture-service.herokuapp.com/picture/restaurant/api/add", fd, conf)
                    .success(function (d){
                        console.log(d);
                        console.log(d.id + ' - получили id картинки, сохраненной в БД');
                        // присвоили индекс картинки свойству restaurant.picture
                        $scope.restaurant.pictureId=d.id;
                        console.log("id фото ресторана = ");
                        console.log($scope.restaurant.pictureId);
                        $scope.isUploadImj=true;
                    })
                    .error(function (d){
                        console.log(d);
                    });
            }

        };

        //============= запрос на добавление ресторана, работает ======
        $scope.addRestaurant=function (restaurant){
            console.log(restaurant);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.post("https://cookstarter-restaurant-service.herokuapp.com/restaurant/add", restaurant)
                .then(function(response) {
                    console.log("Сохранили карточку ресторана! " + response.status);
                    console.log(response.data);
                    let id = response.data;
                    console.log("Получили id карточки ресторана! " + id);
                    $window.localStorage.setItem('restaurantId', JSON.stringify(id));
                    $window.location.href = '#/restaurants';
                })
                .catch(function(response){
                    if (response.status === 404) {
                        console.log("404 Not found! Restaurant with id: " + myRestaurantId + " not found!");
                        $scope.fileLength = 0;
                    } else {
                        if (response.status === 403) {
                            console.log("403 Forbidden! Error checking the token!");
                            $window.location.href = '#/login';
                        } else
                        if (response.status === 500) {
                            console.log("500 Error!");
                            $window.location.href = '#/';
                        }
                    }
                });
        };

        //============= Запрос на обновление ресторана, работает =============

        $scope.editImgR=false;
        $scope.textEdit="Обновить фото"
        $scope.editImgRestaurant=function(){
            if ($scope.textEdit==="Обновить фото") {
                $scope.editImgR=true;
                console.log($scope.editImgR);
                $scope.textEdit="Отмена"
                return;
            }
            if ($scope.textEdit==="Отмена"){
                $scope.editImgR=false;
                $scope.textEdit="Обновить фото"
                console.log($scope.editImgR);
            }
        };

        $scope.editRestaurant=function (restaurant){
            console.log(restaurant.id);
            $scope.restaurant.name=restaurant.name;
            $scope.restaurant.description=restaurant.description;
            console.log(restaurant);
        };
        if (token) {
            $http.defaults.headers.common.Authorization = token;
        }
        $scope.updateRestaurant=function (restaurant){
            console.log("Обновление ресторана!");
            console.log(restaurant);
            $http.post("https://cookstarter-restaurant-service.herokuapp.com/restaurant/update", restaurant)
                .then(function(response) {
                    console.log("Успешное обновление ресторана! " + response.status);
                    $window.location.href = '#/restaurants';
                    console.log("После обновления ресторана, значение кнопки");
                    console.log($scope.editImgR);
                })
                .catch(function(response){
                    if (response.status === 404) {
                        console.log("404 Not found! Restaurant with id: " + myRestaurantId + " not found!");
                        $scope.fileLength = 0;
                    } else {
                        if (response.status === 403) {
                            console.log("403 Forbidden! Error checking the token!");
                            $window.location.href = '#/login';
                        } else
                        if (response.status === 500) {
                            console.log("500 Error!");
                            $window.location.href = '#/';
                        }
                    }
                });
        };

                                //============ для контактов ==============

        //============= запрос на добавление контакта, работает======
        $scope.addContact=function(contact){

            contact.restaurantId=myRestaurantId; // не забыть!
            console.log(contact);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
                $http.post("https://cookstarter-restaurant-service.herokuapp.com/contact/add", contact)
                    .success(function(data, status){
                        console.log("Контакты сохранены! " + status);
                        $window.location.href = '#/restaurants';
                    })
                    .error(function(data, status){
                        console.log("Ошибка при сохранении контакта! " + status);
                        $window.location.href = '#/login';
                    });
        };

        //============= запрос на обновление контакта, работает ====
        $scope.editContact=function(contact){
            console.log(contact);
            $scope.contacts.address =contact.address;
            $scope.contacts.phone = contact.phone;
            $scope.contacts.location = contact.location;
            $scope.contacts.mail = contact.mail;
            $scope.contacts.website = contact.website;
            $scope.contacts.restaurantId = contact.restaurantId;
        };
        $scope.updateContact=function (contact){
            console.log("Процес обновления контакта!");
            console.log(contact);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.post("https://cookstarter-restaurant-service.herokuapp.com/contact/update", contact)
                .success(function(data, status){
                    console.log("Контакты обновлены! " + status);
                    $window.location.href = '#/restaurants';
                })
                .error(function(data, status){
                    console.log("Ошибка при обновлении контактов! " + status);
                    $window.location.href = '#/login';
                });
        }

        //============= запрос на удаление контакта, работает =====
        $scope.deleteContact=function(){
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.get("https://cookstarter-restaurant-service.herokuapp.com/contact/delete/"+ myRestaurantId)
                .success(function(data, status){
                    console.log("Success delete contact " + status);
                    $window.location.href = '#/restaurants';
                })
                .error(function(data, status){
                    console.log("Error delete contact " + status);
                    $window.location.href = '#/login';
                });
        };
    });
})();