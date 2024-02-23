package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Services extends BaseEntity<Long> implements Serializable {

    @Column(unique = true)
    String serviceName;





    public Services() {
    }

    public Services(Long aLong, String serviceName) {
        super(aLong);
        this.serviceName = serviceName;
    }

    public Services(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
