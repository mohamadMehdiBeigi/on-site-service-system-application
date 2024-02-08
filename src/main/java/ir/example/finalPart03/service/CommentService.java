package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Comments;

import java.util.List;

public interface CommentService {

    Comments saveComment(Comments comments, Long orderId);

    List<Comments> findAllBySpecialistId(Long specialistId);



}
