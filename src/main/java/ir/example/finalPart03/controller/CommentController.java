package ir.example.finalPart03.controller;

import ir.example.finalPart03.model.Comments;
import ir.example.finalPart03.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/save")
    ResponseEntity<Comments> saveComments(@RequestBody @Valid Comments comments) {
        return new ResponseEntity<Comments>(commentService.saveComment(comments), HttpStatus.CREATED);
    }

    @GetMapping("/comments/{specialistId}")
    ResponseEntity<List<Comments>> findAllBySpecialistId(@PathVariable Long specialistId) {
        return ResponseEntity.accepted().body(commentService.findAllBySpecialistId(specialistId));
    }
}
