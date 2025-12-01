package com.orderservice.OrderService.services;

import com.orderservice.OrderService.dto.CreateOrderDTO;
import com.orderservice.OrderService.dto.OrderItemResponseDTO;
import com.orderservice.OrderService.dto.OrderResponseDTO;
import com.orderservice.OrderService.entities.OrderEntity;
import com.orderservice.OrderService.entities.OrderItemEntity;
import com.orderservice.OrderService.repositories.OrderRepository;
import com.orderservice.OrderService.utils.OrderNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    public OrderResponseDTO findOne(int id) {
        return convertToOrderResponseDTO(orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new));
    }

    @Transactional
    public void save(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    @Transactional
    public void update(int id, OrderEntity orderEntity) {
        orderEntity.setId(id);
        orderRepository.save(orderEntity);
    }

    @Transactional
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO) {
        OrderEntity order = new OrderEntity();
        order.setUserId(createOrderDTO.getUserId());
        order.setStatus("CREATED");

        List<OrderItemEntity> items = createOrderDTO.getItems().stream()
                .map(itemReq -> {
                    OrderItemEntity orderItemEntity = new OrderItemEntity();
                    orderItemEntity.setAmount(itemReq.getAmount());
                    orderItemEntity.setCardId(itemReq.getCardId());
                    orderItemEntity.setUnitPrice(itemReq.getUnitPrice());
                    orderItemEntity.setOrderEntity(order);
                    return orderItemEntity;
                })
                .toList();

        for (OrderItemEntity item : items) {
            order.setTotalCost(order.getTotalCost() + item.getAmount() * item.getUnitPrice());
        }

        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return convertToOrderResponseDTO(order);
    }

    public CreateOrderDTO convertToOrderDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, CreateOrderDTO.class);
    }

    public OrderEntity convertToOrderEntity(CreateOrderDTO createOrderDTO) {
        return modelMapper.map(createOrderDTO, OrderEntity.class);
    }

    public OrderResponseDTO convertToOrderResponseDTO(OrderEntity orderEntity) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(orderEntity.getId());
        orderResponseDTO.setUserId(orderEntity.getUserId());
        orderResponseDTO.setItems(orderEntity.getItems().stream()
                .map(this::convertToOrderItemResponseDTO)
                .toList());
        orderResponseDTO.setTotalCost(orderEntity.getTotalCost());

        return orderResponseDTO;
    }

    public OrderItemResponseDTO convertToOrderItemResponseDTO(OrderItemEntity orderItemEntity) {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setAmount(orderItemEntity.getAmount());
        orderItemResponseDTO.setUnitPrice(orderItemEntity.getUnitPrice());
        orderItemResponseDTO.setCardId(orderItemEntity.getCardId());

        return orderItemResponseDTO;
    }
}
