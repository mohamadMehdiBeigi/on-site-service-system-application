package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@AllArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Order extends BaseEntity<Long> implements Serializable {

    Double suggestedPrice;

    String workDescription;

    LocalDateTime startDayOfWork;

    LocalDateTime endTimeOfWork;

    String address;

    @Enumerated(value = EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    SubServices subServices;

    @ManyToOne
    Customer customer;

    @OneToOne(mappedBy = "order")
    Comments comments;

    @PrePersist
    protected void onCreate() {
        this.orderStatus = OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST;
    }


    public Order() {
    }

    public Double getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public LocalDateTime getStartDayOfWork() {
        return startDayOfWork;
    }

    public void setStartDayOfWork(LocalDateTime startDayOfWork) {
        this.startDayOfWork = startDayOfWork;
    }

    public LocalDateTime getEndTimeOfWork() {
        return endTimeOfWork;
    }

    public void setEndTimeOfWork(LocalDateTime endTimeOfWork) {
        this.endTimeOfWork = endTimeOfWork;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public SubServices getSubServices() {
        return subServices;
    }

    public void setSubServices(SubServices subServices) {
        this.subServices = subServices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }
}
