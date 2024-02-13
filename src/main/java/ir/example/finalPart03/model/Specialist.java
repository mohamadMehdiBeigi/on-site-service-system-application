package ir.example.finalPart03.model;

import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Specialist extends Users implements UserDetails {

    @Enumerated(value = EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Lob
    private byte[] image;

    Double averageScores;

    @ManyToMany
    @JoinTable(
            name = "users_sub_services",
            schema = "final_part3",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id")
    )
    Set<SubServices> subServices;

    public Specialist(String firstname, String lastname, String email, String password, LocalDateTime signupDate, SpecialistStatus specialistStatus, byte[] image, Double averageScores, Set<SubServices> subServices, Role role) {
        super(firstname, lastname, email, password, signupDate, role);
        this.specialistStatus = specialistStatus;
        this.image = image;
        this.averageScores = averageScores;
        this.subServices = subServices;
    }

    public Specialist() {
    }

    public SpecialistStatus getSpecialistStatus() {
        return specialistStatus;
    }

    public void setSpecialistStatus(SpecialistStatus specialistStatus) {
        this.specialistStatus = specialistStatus;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getAverageScores() {
        return averageScores;
    }

    public void setAverageScores(Double averageScores) {
        this.averageScores = averageScores;
    }

    public Set<SubServices> getSubServices() {
        return subServices;
    }

    public void setSubServices(Set<SubServices> subServices) {
        this.subServices = subServices;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
