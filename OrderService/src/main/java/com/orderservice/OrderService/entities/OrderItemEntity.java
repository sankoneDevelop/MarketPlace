package com.orderservice.OrderService.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "card_id")
    private int cardId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "unit_price")
    private int unitPrice;

    @Column(name = "total_price")
    private int totalPrice;



    public OrderItemEntity() {
    }

    public OrderItemEntity(int cardId, int amount, int unitPrice, int totalPrice) {
        this.cardId = cardId;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", orderEntity=" + orderEntity +
                ", cardId=" + cardId +
                ", amount=" + amount +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
