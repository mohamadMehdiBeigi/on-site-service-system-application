package ir.example.finalPart03.model.enums;

import ir.example.finalPart03.config.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST(0, "WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST"),

    WAITING_FOR_SPECIALIST_SELECTION(1, "WAITING_FOR_SPECIALIST_SELECTION"),

    WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE(2, "WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE"),

    STARTED(3, "STARTED"),

    DONE(4, "DONE"),

    PAID(4, "PAID");

    private final Integer id;
    private final String title;

    public static OrderStatus findByTitle(String title) {
        return Arrays.stream(values()).filter(e -> e.getTitle().equalsIgnoreCase(title)).findFirst().orElseThrow(() -> new NotFoundException("there is no order with this id"));
    }
    public static OrderStatus findById(int id) {
        return Arrays.stream(values()).filter(e -> e.getId() == id).findFirst().orElseThrow(() -> new NotFoundException("there is no order with this id"));
    }

}
