package ru.guteam.customer_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.guteam.customer_service.entities.Customer;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}