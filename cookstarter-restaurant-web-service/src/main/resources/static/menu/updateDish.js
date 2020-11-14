"use strict";
(function (){
    let app=angular.module("myApp");

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

    app.controller('updateDishController', function($scope, $http, $window, $route){

        let token = $window.localStorage.getItem('Authorization');
        $scope.dish={};

        $scope.myDish = $window.localStorage.getItem('updatedDish');
        console.log($scope.myDish + " - myDish");
        $scope.dish = JSON.parse($scope.myDish);
        console.log($scope.dish + " - $scope.dish");
        console.log($scope.dish.name + " - $scope.dish.name");

        $scope.editImgD=false;
        $scope.textEdit="Обновить фото"

        $scope.editImgDish=function(){
            if ($scope.textEdit==="Обновить фото") {
                $scope.editImgD=true;
                console.log($scope.editImgD);
                $scope.textEdit="Отмена"
                return;
            }
            if ($scope.textEdit==="Отмена"){
                $scope.editImgD=false;
                $scope.textEdit="Обновить фото"
                console.log($scope.editImgD);
            }
        };

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

            // Запрос на сохраниние картинки в бд
            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            if ($scope.editImgD) { // если обновление, можно убрать ?
                $http.post("https://picture-service.herokuapp.com/picture/menu/api/update/"+$scope.dish.pictureId, fd, conf)
                    .success(function (d){
                        console.log(d);
                        console.log($scope.dish.pictureId + " - id обновляемой картинки блюда.");
                        $scope.isUploadImj=true;
                    })
                    .error(function (d){
                        console.log(d);
                    });
            } else {}
        };

        // === Запрос на обновление блюда ===
        $scope.updateDish=function (dish){
            console.log("Процесс обновления блюда начался");
            console.log(dish);

            if (token) {
                $http.defaults.headers.common.Authorization = token;
            }
            $http.post("https://cookstarter-restaurant-service.herokuapp.com/dish/update", dish)
                .success(function(data, status){
                    console.log("Удачное обновление блюда! " + status);
                    $window.location.reload();
                    $window.location.href = '#/menu';
                })
                .error(function(data, status){
                    console.log("Блюдо не обновилось! Ошибка! " + status);
                    $window.location.href = '#/menu';
                });

        };



    });
})();