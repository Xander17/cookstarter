package ru.guteam.customer_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.guteam.customer_service.entities.Customer;
import ru.guteam.customer_service.entities.User;
import ru.guteam.customer_service.entities.utils.SystemCustomer;
import ru.guteam.customer_service.repositories.CustomersRepository;

import java.util.Optional;

@Service
public class CustomersService {
    private CustomersRepository customersRepository;
    private UsersService usersService;
    @Autowired
    public void setUsersRepository(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }
    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }


    @Transactional
    public Customer saveBySystemCustomer(SystemCustomer systemCustomer) {
        final Customer customer = new Customer();
        customer.setFirstName(systemCustomer.getFirstName());
        customer.setLastName(systemCustomer.getLastName());
        customer.setEmail(systemCustomer.getEmail());
        User user = usersService.createBySystemCustomer(systemCustomer);
        user.setCustomer(customer);
        customer.setUser(user);
        return customersRepository.save(customer);
    }

    public boolean existsByEmail(String email) {
        return customersRepository.existsByEmail(email);
    }

    public Optional<Customer> findById(Long id) {
        return customersRepository.findById(id);
    }
}