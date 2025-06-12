package com.codingshuttle.ecommerce.inventory_service.controller;

import com.codingshuttle.ecommerce.inventory_service.clients.OrdersFeignClient;
import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestDto;
import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrdersFeignClient ordersFeignClient;

    private final static Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, DiscoveryClient discoveryClient, RestClient restClient, OrdersFeignClient ordersFeignClient) {
        this.productService = productService;
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
        this.ordersFeignClient = ordersFeignClient;
    }

    @GetMapping("/fetchOrders")
    public String fetchFromOrdersService(HttpServletRequest httpServletRequest) {

        log.info(httpServletRequest.getHeader("x-custom-header"));

//        ServiceInstance orderService = discoveryClient.getInstances("order-service").getFirst();

//        return restClient.get()
//                .uri(orderService.getUri()+"/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id) {
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

}
