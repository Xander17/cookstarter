package ru.guteam.customer_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.guteam.customer_service.entities.Customer;
import ru.guteam.customer_service.entities.User;
import ru.guteam.customer_service.entities.Role;
import ru.guteam.customer_service.entities.utils.SystemCustomer;
import ru.guteam.customer_service.entities.utils.SystemRestaurant;
import ru.guteam.customer_service.entities.utils.enums.UsersTypeEnum;
import ru.guteam.customer_service.repositories.UsersRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesService rolesService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
                true, true, user.isEnable(), mapRolesToAuthorities(Arrays.asList(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Невозможно найти пользователя по логину = " + username));
    }

    public Optional<User> findOptionalByUsername(String username) {
        return usersRepository.findOneByUsername(username);
    }

    @Transactional
    public User saveRestaurant(SystemRestaurant systemRestaurant) {
        final User user = new User();
        Customer customer = new Customer();
        customer.setId(systemRestaurant.getId());
        user.setUserType(UsersTypeEnum.RESTAURANT);
        user.setCustomer(customer);
        user.setUsername(systemRestaurant.getUsername());
        user.setPassword(passwordEncoder.encode(systemRestaurant.getPassword()));
        user.setRole(rolesService.findByName(systemRestaurant.getRole().toString()));
        user.setEnable(true);
        return usersRepository.save(user);
    }

    public User createBySystemCustomer(SystemCustomer systemCustomer) {
        User user = new User();
        user.setUserType(UsersTypeEnum.CUSTOMER);
        user.setUsername(systemCustomer.getUsername());
        user.setPassword(passwordEncoder.encode(systemCustomer.getPassword()));
        user.setRole(rolesService.findByName("CUSTOMER"));
        user.setEnable(true);
        return user;
    }

    public boolean existsByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }


}