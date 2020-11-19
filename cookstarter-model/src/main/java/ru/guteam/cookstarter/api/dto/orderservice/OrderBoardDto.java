package ru.guteam.cookstarter.api.dto.orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBoardDto {
    private Long orderId;
    private String userName;
    private Long restaurantId;
    private List<Dish> dishes;

    @Override
    public String toString() {
        return "{\n" +
                "   orderId='" + orderId + "',\n" +
                "   restaurantId='" + restaurantId + "',\n" +
                "   username='" + userName + "',\n" +
                "   dishes=" + dishes + "\n" +
                '}';
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dish {
        private String name;
        @JsonProperty("qty")
        private int quantity;
        private BigDecimal price;

        @Override
        public String toString() {
            return "{\n" +
                    "       name='" + name + "',\n" +
                    "       quantity=" + quantity + ",\n" +
                    "       price=" + price + "\n" +
                    "   }";
        }
    }
}
