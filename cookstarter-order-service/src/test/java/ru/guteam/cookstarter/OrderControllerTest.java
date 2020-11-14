package ru.guteam.cookstarter;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.guteam.cookstarter.api.dto.RequestMessageHeaders;
import ru.guteam.cookstarter.api.dto.StatusResponse;
import ru.guteam.cookstarter.api.dto.orderservice.OrderDto;
import ru.guteam.cookstarter.api.enums.OrderStatus;
import ru.guteam.cookstarter.orderservice.model.Order;
import ru.guteam.cookstarter.orderservice.repository.OrderItemRepository;
import ru.guteam.cookstarter.orderservice.repository.OrderRepository;
import ru.guteam.cookstarter.util.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderControllerTest {
    private static final String ADD_ORDER_URL = "/orders/add";

    private static final String UPDATE_ORDER_URL = "/orders/update";
    private static final String DELETE_ORDER_URL = "/orders/delete/%s";
    private static final String DELETE_ITEM_URL = "/orders/delete/item/%s";
    private static final String GET_ORDER_URL = "/orders/get/%s";
    private static final String GET_ORDER_BY_CUSTOMER_URL = "/orders/get/customer/%s";
    private static final String GET_ORDER_BY_RESTAURANT_URL = "/orders/get/restaurant/%s";

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9.1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow";
    private static final String WRONG_TOKEN = "Bearer 123";

    @LocalServerPort
    private int port;

    private final String HOST = "http://localhost";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TestUtils utils;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @BeforeEach
    void init() {
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
    }

    @Test
    void addOrderTest() throws IOException, URISyntaxException {
        ResponseEntity<StatusResponse> response = addOrder("order/order1.json");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Optional<Order> orderOptional = orderRepository.findById(Long.valueOf(response.getBody().getId()));
        assertTrue(orderOptional.isPresent());
        Order order = orderOptional.get();
        assertEquals(OrderStatus.SAVED, order.getStatus());
        assertNotNull(order.getDateCreated());
    }

    @Test
    void wrongTokenAddOrderTest() throws IOException, URISyntaxException {
        ResponseEntity<StatusResponse> response = postWithWrongToken(ADD_ORDER_URL, "order/order1.json", OrderDto.class, StatusResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(orderRepository.findAll().isEmpty());
    }

    @Test
    void addAndUpdateExistItemsTest() throws IOException, URISyntaxException {
        addAndUpdate("order/order1.json", "order/orderWithUpdatedExistDishes.json");
    }

    private void addAndUpdate(String originalOrderPath, String updatedOrderPath) throws IOException, URISyntaxException {
        addAndUpdate(originalOrderPath, updatedOrderPath, updatedOrderPath);
    }

    private void addAndUpdate(String originalOrderPath, String updatedOrderPath, String expectedOrderPath) throws IOException, URISyntaxException {
        OrderDto order = utils.readJson(originalOrderPath, OrderDto.class);
        OrderDto updatedOrder = utils.readJson(updatedOrderPath, OrderDto.class);
        OrderDto expectedOrder = utils.readJson(expectedOrderPath, OrderDto.class);

        Long id = Long.valueOf(addOrder(order).getBody().getId());
        OrderDto addedOrder = getById(id).getBody();
        addedOrder.setDishes(updatedOrder.getDishes());
        updateOrder(addedOrder);
        addedOrder = cleanNotComparableFields(getById(id).getBody());
        assertEquals(expectedOrder, addedOrder);
    }

    @Test
    void addAndUpdateWithDeleteItemTest() throws IOException, URISyntaxException {
        addAndUpdate("order/order1.json", "order/orderWithDeletedDishes.json");
    }

    @Test
    void addAndUpdateWithDeleteAllItemsTest() throws IOException, URISyntaxException {
        updateWithZeroQty("order/order1.json", "order/orderWithDeletedAllDishes.json");
    }

    private void updateWithZeroQty(String orderPath, String updatedOrderPath) throws IOException, URISyntaxException {
        OrderDto order = utils.readJson(orderPath, OrderDto.class);
        OrderDto updatedOrder = utils.readJson(updatedOrderPath, OrderDto.class);

        Long id = Long.valueOf(addOrder(order).getBody().getId());
        OrderDto addedOrder = getById(id).getBody();
        addedOrder.setDishes(updatedOrder.getDishes());
        updateOrder(addedOrder);
        ResponseEntity<StatusResponse> response = get(String.format(GET_ORDER_URL, id), StatusResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addAndUpdateWithZeroQtyItemTest() throws IOException, URISyntaxException {
        addAndUpdate("order/order1.json", "order/orderWithZeroQtyDishes.json", "order/orderWithDeletedDishes.json");
    }

    @Test
    void addAndUpdateWithZeroQtyAllItemsTest() throws IOException, URISyntaxException {
        updateWithZeroQty("order/order1.json", "order/orderWithZeroQtyAllDishes.json");
    }

    @Test
    void addAndUpdateWithOnlyNewItemsTest() throws IOException, URISyntaxException {
        addAndUpdate("order/order1.json", "order/orderWithOnlyNewDishes.json");
    }

    @Test
    void addAndUpdateWithNewItemsTest() throws IOException, URISyntaxException {
        addAndUpdate("order/order1.json", "order/orderWithNewDishes.json");
    }

    @Test
    void addAndDeleteOrderTest() throws IOException, URISyntaxException {
        Long id1 = Long.valueOf(addOrder("order/order1.json").getBody().getId());
        Long id2 = Long.valueOf(addOrder("order/order2.json").getBody().getId());

        ResponseEntity<StatusResponse> response = get(String.format(DELETE_ORDER_URL, id1), StatusResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertFalse(orderRepository.findById(id1).isPresent());
        assertTrue(orderRepository.findById(id2).isPresent());
    }

    @Test
    void addOrderAndDeleteOneItemTest() throws IOException, URISyntaxException {
        Long orderId = Long.valueOf(addOrder("order/order1.json").getBody().getId());
        List<Long> dishIds = getById(orderId).getBody().getDishes().values().stream()
                .map(OrderDto.Item::getId)
                .collect(Collectors.toList());
        ResponseEntity<StatusResponse> response = deleteById(dishIds.get(0));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Long> newIds = getById(orderId).getBody().getDishes().values().stream()
                .map(OrderDto.Item::getId)
                .collect(Collectors.toList());

        assertFalse(newIds.contains(dishIds.get(0)));
        for (int i = 1; i < dishIds.size(); i++) {
            assertTrue(newIds.contains(dishIds.get(i)));
        }
    }

    @Test
    void addOrderAndDeleteAllItemsTest() throws IOException, URISyntaxException {
        Long orderId = Long.valueOf(addOrder("order/order1.json").getBody().getId());
        List<Long> dishIds = getById(orderId).getBody().getDishes().values().stream()
                .map(OrderDto.Item::getId)
                .collect(Collectors.toList());
        for (Long id : dishIds) {
            ResponseEntity<StatusResponse> response = deleteById(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }
        assertFalse(orderRepository.findById(orderId).isPresent());
    }

    @Test
    void addAndGetByIdTest() throws IOException, URISyntaxException {
        OrderDto orderDto1 = utils.readJson("order/order1.json", OrderDto.class);
        OrderDto orderDto2 = utils.readJson("order/order2.json", OrderDto.class);
        Long id1 = Long.valueOf(addOrder(orderDto1).getBody().getId());
        Long id2 = Long.valueOf(addOrder(orderDto2).getBody().getId());

        ResponseEntity<OrderDto> orderResponse = getById(id1);
        assertNotNull(orderResponse.getBody());
        assertEquals(orderDto1, cleanNotComparableFields(orderResponse.getBody()));

        orderResponse = getById(id2);
        assertNotNull(orderResponse.getBody());
        assertEquals(orderDto2, cleanNotComparableFields(orderResponse.getBody()));
    }

    private OrderDto cleanNotComparableFields(OrderDto order) {
        order.setId(null);
        order.setStatus(null);
        order.setDateCreated(null);
        order.getDishes().values().forEach(item -> item.setId(null));
        return order;
    }

    @Test
    void addAndGetAllByCustomerIdTest() throws IOException, URISyntaxException {
        List<Long> customerIds = new ArrayList<>();
        List<Integer> orderCount = new ArrayList<>();
        List<OrderDto> orders = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(3, 5); i++) {
            long customerId = RandomUtils.nextInt(1, 100);
            OrderDto orderDto = utils.readJson("order/order1.json", OrderDto.class);
            orderDto.setCustomerId(customerId);
            int count = RandomUtils.nextInt(3, 10);
            for (int j = 0; j < count; j++) {
                addOrder(orderDto);
            }
            customerIds.add(customerId);
            orderCount.add(count);
            orders.add(orderDto);
        }
        for (int i = 0; i < orders.size(); i++) {
            ResponseEntity<List<OrderDto>> listResponse = getByCustomerId(customerIds.get(i));
            assertNotNull(listResponse.getBody());
            assertEquals(orderCount.get(i), listResponse.getBody().size());
        }
    }

    @Test
    void addAndGetAllByRestaurantIdTest() throws IOException, URISyntaxException {
        List<Long> restaurantIds = new ArrayList<>();
        List<Integer> orderCount = new ArrayList<>();
        List<OrderDto> orders = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(3, 5); i++) {
            long restaurantId = RandomUtils.nextInt(1, 100);
            OrderDto orderDto = utils.readJson("order/order1.json", OrderDto.class);
            orderDto.setRestaurantId(restaurantId);
            int count = RandomUtils.nextInt(3, 10);
            for (int j = 0; j < count; j++) {
                addOrder(orderDto);
            }
            restaurantIds.add(restaurantId);
            orderCount.add(count);
            orders.add(orderDto);
        }
        for (int i = 0; i < orders.size(); i++) {
            ResponseEntity<List<OrderDto>> listResponse = getByRestaurantId(restaurantIds.get(i));
            assertNotNull(listResponse.getBody());
            assertEquals(orderCount.get(i), listResponse.getBody().size());
        }
    }

    private ResponseEntity<StatusResponse> addOrder(String path) throws IOException, URISyntaxException {
        return post(ADD_ORDER_URL, path, OrderDto.class, StatusResponse.class);
    }

    private ResponseEntity<StatusResponse> addOrder(OrderDto orderDto) throws IOException, URISyntaxException {
        return post(ADD_ORDER_URL, orderDto, StatusResponse.class);
    }

    private ResponseEntity<StatusResponse> updateOrder(OrderDto orderDto) throws IOException, URISyntaxException {
        return post(UPDATE_ORDER_URL, orderDto, StatusResponse.class);
    }

    private ResponseEntity<StatusResponse> deleteById(Long id) {
        return get(String.format(DELETE_ITEM_URL, id), StatusResponse.class);
    }

    private ResponseEntity<OrderDto> getById(Long id) {
        return get(String.format(GET_ORDER_URL, id), OrderDto.class);
    }

    private ResponseEntity<List<OrderDto>> getByCustomerId(Long id) {
        return getList(String.format(GET_ORDER_BY_CUSTOMER_URL, id), OrderDto.class);
    }

    private ResponseEntity<List<OrderDto>> getByRestaurantId(Long id) {
        return getList(String.format(GET_ORDER_BY_RESTAURANT_URL, id), OrderDto.class);
    }

    private <R, T> ResponseEntity<T> post(String url, String requestPath, Class<R> requestClass, Class<T> responseClass)
            throws IOException, URISyntaxException {
        R requestBody = utils.readJson(requestPath, requestClass);
        HttpEntity<R> request = getHttpEntity(requestBody, TOKEN);
        return restTemplate.exchange(getFullUrl(url), HttpMethod.POST, request, responseClass);
    }

    private <R, T> ResponseEntity<T> post(String url, R requestBody, Class<T> responseClass)
            throws IOException, URISyntaxException {
        HttpEntity<R> request = getHttpEntity(requestBody, TOKEN);
        return restTemplate.exchange(getFullUrl(url), HttpMethod.POST, request, responseClass);
    }

    private <R> HttpEntity<R> getHttpEntity(R request, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(RequestMessageHeaders.JWT_TOKEN_HEADER, token);
        return new HttpEntity<>(request, httpHeaders);
    }

    private <R, T> ResponseEntity<T> postWithWrongToken(String url, String requestPath, Class<R> requestClass, Class<T> responseClass)
            throws IOException, URISyntaxException {
        R requestBody = utils.readJson(requestPath, requestClass);
        HttpEntity<R> request = getHttpEntity(requestBody, WRONG_TOKEN);
        return restTemplate.exchange(getFullUrl(url), HttpMethod.POST, request, responseClass);
    }

    private <T> ResponseEntity<T> get(String url, Class<T> responseClass) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(RequestMessageHeaders.JWT_TOKEN_HEADER, TOKEN);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(getFullUrl(url), HttpMethod.GET, request, responseClass);
    }

    private <T> ResponseEntity<List<T>> getList(String url, Class<T> responseClass) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(RequestMessageHeaders.JWT_TOKEN_HEADER, TOKEN);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(getFullUrl(url), HttpMethod.GET, request, new ParameterizedTypeReference<>() {
        });
    }

    private String getFullUrl(String url) {
        return HOST + ":" + port + url;
    }
}
