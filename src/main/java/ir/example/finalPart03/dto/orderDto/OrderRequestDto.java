package ir.example.finalPart03.dto.orderDto;

import java.time.LocalDateTime;

public class OrderRequestDto {

    private Double suggestedPrice;

    private String workDescription;

    private LocalDateTime startDayOfWork;

    private String address;

    private Long subServicesId;

    private Long customerId;


    public OrderRequestDto() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSubServicesId() {
        return subServicesId;
    }

    public void setSubServicesId(Long subServicesId) {
        this.subServicesId = subServicesId;
    }
}
