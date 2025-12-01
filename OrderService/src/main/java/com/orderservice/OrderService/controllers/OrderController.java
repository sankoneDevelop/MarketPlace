package com.orderservice.OrderService.controllers;

import com.orderservice.OrderService.dto.CreateOrderDTO;
import com.orderservice.OrderService.dto.OrderItemResponseDTO;
import com.orderservice.OrderService.dto.OrderResponseDTO;
import com.orderservice.OrderService.entities.OrderEntity;
import com.orderservice.OrderService.services.OrderService;
import com.orderservice.OrderService.utils.OrderErrorResponse;
import com.orderservice.OrderService.utils.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private CreateOrderDTO createOrderDTO;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CreateOrderDTO> index() {
        return orderService.findAll().stream().map(orderService::convertToOrderDTO).toList();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO show(@PathVariable("id") int id) {
        return orderService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDTO> create(@RequestBody CreateOrderDTO createOrderDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(createOrderDTO));
    }

    @ExceptionHandler
    public ResponseEntity<OrderErrorResponse> handleException(OrderNotFoundException e) {
        OrderErrorResponse orderErrorResponse = new OrderErrorResponse(
                "Order with that id not found, repeat request with another id",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(orderErrorResponse, HttpStatus.NOT_FOUND);
    }

}
