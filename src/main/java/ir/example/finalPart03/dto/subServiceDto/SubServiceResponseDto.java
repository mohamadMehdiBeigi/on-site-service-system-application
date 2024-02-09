package ir.example.finalPart03.dto.subServiceDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;

public class SubServiceResponseDto {

    Long id;

    @Column(unique = true)
    private String subServiceName;

    @Column(nullable = false)
    @Min(value = 0, message = "price cant be lower than zero")
    private Double basePrice;

    private String description;

    public SubServiceResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
