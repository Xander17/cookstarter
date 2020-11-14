"use strict";

(function (){
    let app=angular.module("myApp");

    let getNumberD=function(){
        return Math.floor((Math.random()*325)+1);
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

    // контроллер для меню
    app.controller('menuController', function($scope, $http, $window, $route){
// при отрисовки html появляется лишний запрос на получение картинки, надо понять в чем дело

        let token = $window.localStorage.getItem('Authorization');
        let myRestaurantId = $window.localStorage.getItem('restaurantId');

        $scope.menu=[];
        $scope.dish={};
        $scope.token='?Authorization=' + token;
        $scope.num='&num='+ getNumberD();
        console.log("Генерируемое число для бюда");
        console.log($scope.num);

        //=== получение всего меню, работает =======
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.get("https://cookstarter-restaurant-service.herokuapp.com/menu/get/"+myRestaurantId)
                .then(function(response) {
                    $scope.menu=response.data;
                    console.log($scope.menu);

                    $scope.currentPage=1; // текущая страница
                    $scope.dataLimit=5;  // количество выводимых строк
                    console.log("На странице отображается " + $scope.dataLimit + " блюд");

                    $scope.fileLength=$scope.menu.length;
                    $scope.pageCount=Math.ceil($scope.fileLength / $scope.dataLimit);
                    console.log("Количество страниц: " + $scope.pageCount);

                    $scope.prevPage=function (){
                        return $scope.currentPage--;
                    };

                    $scope.nextPage=function (){
                        return $scope.currentPage++;
                    };

                    $scope.firstPage=function (){
                        return $scope.currentPage === 1;
                    };

                    $scope.lastPage=function (){
                        return $scope.currentPage === $scope.pageCount;
                    };

                    $scope.start=function (){
                        return ($scope.currentPage - 1) * $scope.dataLimit;
                    };
                })
                .catch(function(response){
                    if (response.status === 404) {
                        console.log("404 Not found! Menu with restaurantId: " + myRestaurantId + " not found!");
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

        $scope.sort_width=function(base){
            $scope.base=base;
            $scope.reverse=!$scope.reverse
        };

        //=== для формы добавления блюда ===
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
                $http.post("https://picture-service.herokuapp.com/picture/menu/api/add", fd, conf)
                    .success(function (d){
                        console.log(d);
                        console.log(d.id + ' - получили id картинки');
                        // присвоили индекс картинки свойству dish.pictureId
                        $scope.dish.pictureId=d.id;
                        console.log($scope.dish.pictureId);
                        $scope.isUploadImj=true;
                    })
                    .error(function (d){
                        console.log(d);
                    });
            };

        //======= запрос на добавление блюда, работает ======
        $scope.addDish=function(dish){
            $scope.dish.restaurantId=myRestaurantId;
            console.log(dish);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.post("https://cookstarter-restaurant-service.herokuapp.com/dish/add", dish)
                .success(function(data, status){
                    console.log("Добавили новое блюдо! " + status);
                    $window.location.href = '#/menu';
                })
                .error(function(data, status){
                    console.log("Ошибка при добавлении нового блюда! " + status);
                });

        };

        //==== Редактирование блюда, работает ========
        $scope.editDish=function (item){
            console.log("Кликнули по кнопке редактирования блюда:");
            console.log(item);
            console.log("Сохранили блюдо в хранилище.");
            $window.localStorage.setItem('updatedDish', JSON.stringify(item));
        };

        //============= запрос на удаление блюда ======
        $scope.deleteDish=function(dish){
            console.log(dish)
            let deletedDishImgId = dish.pictureId;
            let dishName = dish.name;
            let restId ="/" + dish.restaurantId;
            console.log("deletedDishImgId = " + deletedDishImgId);
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.get("https://picture-service.herokuapp.com/picture/menu/api/delete/" + deletedDishImgId)
                .success(function(data, status){
                    console.log("Фото блюда успешно удалено! " + status);
                })
                .error(function(data, status){
                    console.log("Возникла ошибка при удалении фото блюда! " + status);
                });


            $http.get("https://cookstarter-restaurant-service.herokuapp.com/dish/delete/"+ dishName + restId)
                .success(function(data, status){
                    console.log("Блюдо успешно удалено! " + status);
                    $window.location.reload();
                    $window.location.href = '#/menu';
                })
                .error(function(data, status){
                    console.log("Возникла ошибка при удалении блюда! " + status);
                });
        };

    });
})();

