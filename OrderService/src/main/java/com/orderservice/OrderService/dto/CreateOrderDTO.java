package com.orderservice.OrderService.dto;

import java.util.List;

public class CreateOrderDTO {

    private int userId;
    private List<OrderItemDTO> items;

    public CreateOrderDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CreateOrderDTO{" +
                "userId=" + userId +
                ", items=" + items +
                '}';
    }
}
