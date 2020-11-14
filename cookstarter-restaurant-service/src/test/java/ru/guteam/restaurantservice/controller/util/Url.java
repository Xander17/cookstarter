package ru.guteam.restaurantservice.controller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Url {
    public static final String ADD_RESTAURANT = "/restaurant/add";
    public static final String GET_RESTAURANT_BY_ID = "/restaurant/get/";
    public static final String GET_RESTAURANT_BY_NAME = "/restaurant/getByName/";
    public static final String GET_ALL_RESTAURANTS = "/restaurant/getAll";
    public static final String UPDATE_RESTAURANT = "/restaurant/update";
    public static final String DELETE_RESTAURANT = "/restaurant/delete/";
    public static final String ADD_CONTACT = "/contact/add";
    public static final String GET_CONTACT_BY_RESTAURANT_ID = "/contact/get/";
    public static final String UPDATE_CONTACT = "/contact/update";
    public static final String DELETE_CONTACT_BY_RESTAURANT_ID = "/contact/delete/";
    public static final String ADD_DISH = "/dish/add";
    public static final String UPDATE_DISH = "/dish/update";
    public static final String DELETE_DISH = "/dish/delete";
    public static final String ADD_MENU = "/menu/add";
    public static final String GET_MENU = "/menu/get/";
}
