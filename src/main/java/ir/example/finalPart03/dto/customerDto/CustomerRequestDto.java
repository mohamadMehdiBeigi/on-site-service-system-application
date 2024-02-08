package ir.example.finalPart03.dto.customerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class CustomerRequestDto {
    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "firstname must have been included just English alphabet")
    private String firstname;

    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "lastname must have been include just English alphabet")
    private String lastname;

    @Email(message = "email syntax is incorrect")
    private String email;

    public CustomerRequestDto() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
