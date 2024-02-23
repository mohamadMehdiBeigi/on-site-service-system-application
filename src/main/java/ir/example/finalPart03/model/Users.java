package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Users extends BaseEntity<Long> implements UserDetails {

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

    @Enumerated(EnumType.STRING)
    Role role;

    Boolean locked;

    Boolean enabled;

    public Users(String firstname, String lastname, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = false;
        this.enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @PrePersist
    private void preparePasswordAndOthers() {
        this.signupDate = LocalDateTime.now();
        this.enabled = false;
        this.locked = false;
    }
}
