package ru.guteam.cookstarter.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import ru.guteam.cookstarter.api.enums.OrderStatus;
import ru.guteam.cookstarter.orderservice.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(@NonNull Long id);

    List<Order> findAllByRestaurantId(@NonNull Long id);

    @Modifying
    @Query("update Order set status=?2 where id=?1")
    void setStatusById(@NonNull Long id, OrderStatus status);
}
