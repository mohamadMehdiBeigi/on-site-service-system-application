package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
    private ModelMapper modelMapper;


}
