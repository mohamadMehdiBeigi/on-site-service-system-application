package ir.example.finalPart03.dto.orderDto;

import ir.example.finalPart03.model.Comments;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.model.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private Double suggestedPrice;

    Long id;

    String workDescription;

    LocalDateTime startDayOfWork;

    String address;

    @Enumerated(value = EnumType.STRING)
    OrderStatus orderStatus;



    SubServices subServices;

    Customer customer;

    Comments comments;


    public OrderResponseDto(Double suggestedPrice, Long id, String workDescription, LocalDateTime startDayOfWork, String address, OrderStatus orderStatus, SubServices subServices, Customer customer, Comments comments) {
        this.suggestedPrice = suggestedPrice;
        this.id = id;
        this.workDescription = workDescription;
        this.startDayOfWork = startDayOfWork;
        this.address = address;
        this.orderStatus = orderStatus;
        this.subServices = subServices;
        this.customer = customer;
        this.comments = comments;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
