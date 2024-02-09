package ir.example.finalPart03.dto.subServiceDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;

public class SubServiceRequestDto {

    @Column(unique = true)
    private String subServiceName;

    @Column(nullable = false)
    @Min(value = 0, message = "price cant be lower than zero")
    private Double basePrice;

    private String description;

    private Long serviceId;


    public SubServiceRequestDto() {
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
