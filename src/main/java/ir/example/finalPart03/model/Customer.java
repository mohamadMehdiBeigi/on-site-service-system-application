package ir.example.finalPart03.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Customer extends Users {


    public Customer(@Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "firstname must have been included just English alphabet") String firstname, @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "lastname must have been include just English alphabet") String lastname, @Email(message = "email syntax is incorrect") String email, @Pattern(regexp = "^\\S+$", message = "password cant have space.") String password, LocalDateTime signupDate, @Min(value = 0, message = "credit cant be lower than zero") Double credit) {
        super(firstname, lastname, email, password, signupDate);
    }
}
