package ir.example.finalPart03.model.enums;

import ir.example.finalPart03.config.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@AllArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN(0, "ROLE_ADMIN"),
    ROLE_CUSTOMER(1, "ROLE_CUSTOMER"),
    ROLE_SPECIALIST(2, "ROLE_SPECIALIST");

    private final Integer id;

    private final String title;

    public static Role findByTitle(String title) {
        return Arrays.stream(values()).filter(e -> e.getTitle().equalsIgnoreCase(title)).findFirst().orElseThrow(() -> new NotFoundException("there is no Role with this id"));
    }
}
