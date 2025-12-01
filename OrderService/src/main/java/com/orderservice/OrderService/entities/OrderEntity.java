package com.orderservice.OrderService.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_entity")
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "total_cost")
    private int totalCost;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItemEntity> items;

    public OrderEntity() {
    }

    public OrderEntity(int userId, int totalCost, String status, LocalDateTime createdAt) {
        this.userId = userId;
        this.totalCost = totalCost;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> orderItemList) {
        this.items = orderItemList;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
