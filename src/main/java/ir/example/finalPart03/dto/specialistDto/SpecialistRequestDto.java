package ir.example.finalPart03.dto.specialistDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class SpecialistRequestDto {

    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "firstname must have been included just English alphabet")
    private String firstname;

    @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "lastname must have been include just English alphabet")
    private String lastname;

    @Column(unique = true)
    @Email(message = "email syntax is incorrect")
    private String email;

    @Pattern(regexp = "^\\S+$", message = "password cant have space.")
    private String password;



    public SpecialistRequestDto(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public SpecialistRequestDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
