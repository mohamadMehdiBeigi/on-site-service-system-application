package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.commentDto.CommentRequestDto;
import ir.example.finalPart03.dto.commentDto.CommentsResponseDto;
import ir.example.finalPart03.model.Comments;
import ir.example.finalPart03.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
    private ModelMapper modelMapper;

    @PostMapping("/save")
    ResponseEntity<CommentsResponseDto> saveComments(@RequestBody CommentRequestDto commentRequestDto) {
        Comments comments = modelMapper.map(commentRequestDto, Comments.class);
        Comments savedComment = commentService.saveComment(comments, commentRequestDto.getOrderId());
        CommentsResponseDto commentsResponseDto = modelMapper.map(savedComment, CommentsResponseDto.class);
        return new ResponseEntity<>(commentsResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{specialistId}")
    ResponseEntity<List<CommentsResponseDto>> findAllBySpecialistId(@PathVariable Long specialistId) {
        List<Comments> allBySpecialistId = commentService.findAllBySpecialistId(specialistId);

        List<CommentsResponseDto> commentsResponseDtoList = new ArrayList<>();

        for (Comments c : allBySpecialistId) {
            CommentsResponseDto commentsResponseDto = modelMapper.map(c, CommentsResponseDto.class);
            commentsResponseDtoList.add(commentsResponseDto);
        }
        return ResponseEntity.accepted().body(commentsResponseDtoList);
    }
}
