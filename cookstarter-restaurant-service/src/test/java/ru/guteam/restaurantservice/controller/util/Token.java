package ru.guteam.restaurantservice.controller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Token {
    public static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9" +
            ".1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow";
    public static final String BAD_TOKEN = "Bearer enJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9" +
            ".1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow";
}
