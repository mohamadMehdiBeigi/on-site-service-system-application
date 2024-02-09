package ir.example.finalPart03.dto.serviceDto;

public class ServiceResponseDto {

    private Long id;

    private String serviceName;

    public ServiceResponseDto(Long id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public ServiceResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
