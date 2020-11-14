package ru.guteam.customer_service.entities.utils.enums;

public enum UsersTypeEnum {
    CUSTOMER("C"), RESTAURANT("R");

    private String code;

    UsersTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
