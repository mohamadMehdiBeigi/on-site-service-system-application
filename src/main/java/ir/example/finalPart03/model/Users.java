package ir.example.finalPart03.model;


import ir.example.finalPart03.config.PasswordGenerator;
import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Users extends BaseEntity<Long> implements Serializable {

    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "firstname must have been included just English alphabet")
    String firstname;

    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "lastname must have been include just English alphabet")
    String lastname;

    @Column(unique = true)
    @Email(message = "email syntax is incorrect")
    String email;

    @Pattern(regexp = "^\\S+$", message = "password cant have space.")
    String password;

    @ValidLocalDateTime
    LocalDateTime signupDate;
    @PrePersist
    private void preparePasswordAndOthers() {
        if (password == null || password.isEmpty()) {
            PasswordGenerator generator = new PasswordGenerator(8);
            this.password = generator.nextPassword();
        }
        this.signupDate = LocalDateTime.now();
    }


}
