package ir.example.finalPart03.dto.serviceDto;

public class ServiceRequestDto {

    private String serviceName;

    public ServiceRequestDto(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceRequestDto() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


}
