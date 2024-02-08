package ir.example.finalPart03.dto.orderDto;

import ir.example.finalPart03.model.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponseDto {

    private Double suggestedPrice;

    private Long id;

    private String workDescription;

    private LocalDateTime startDayOfWork;

    private String address;

    private OrderStatus orderStatus;


    public OrderResponseDto() {
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

}
