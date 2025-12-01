package com.orderservice.OrderService.services;

import com.orderservice.OrderService.entities.OrderItemEntity;
import com.orderservice.OrderService.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemEntity> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItemEntity findOne(int id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item with that id is not found"));
    }

    @Transactional
    public void save(OrderItemEntity orderItemEntity) {
        orderItemRepository.save(orderItemEntity);
    }

    @Transactional
    public void update(int id, OrderItemEntity orderItemEntity) {
        orderItemEntity.setId(id);
        orderItemRepository.save(orderItemEntity);
    }

    @Transactional
    public void delete(int id) {
        orderItemRepository.deleteById(id);
    }

}
