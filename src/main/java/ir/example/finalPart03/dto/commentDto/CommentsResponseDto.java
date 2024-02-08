package ir.example.finalPart03.dto.commentDto;

public class CommentsResponseDto {

    Double score;

    String comment;

    public CommentsResponseDto(Double score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public CommentsResponseDto() {
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
}
