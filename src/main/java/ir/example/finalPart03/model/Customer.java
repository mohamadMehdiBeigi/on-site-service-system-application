package ir.example.finalPart03.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Customer extends Users {


//    public Customer(String firstname, String lastname, String email, String password, LocalDateTime signupDate, Role role) {
//        super(firstname, lastname, email, password, signupDate, role);
//    }

}
