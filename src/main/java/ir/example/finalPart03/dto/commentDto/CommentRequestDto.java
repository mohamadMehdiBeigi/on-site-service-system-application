package ir.example.finalPart03.dto.commentDto;

import jakarta.persistence.Column;
import org.hibernate.validator.constraints.Range;

public class CommentRequestDto {

    @Range(min = 0, max = 5, message = "score should've been between 0 to 5")
    @Column(nullable = false)
    Double score;

    String comment;

    Long orderId;


    public CommentRequestDto(Double score, String comment, Long orderId) {
        this.score = score;
        this.comment = comment;
        this.orderId = orderId;
    }

    public CommentRequestDto() {
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
