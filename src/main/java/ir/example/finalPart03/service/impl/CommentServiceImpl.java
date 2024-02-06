package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Comments;
import ir.example.finalPart03.repository.CommentRepository;
import ir.example.finalPart03.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comments saveComment(Comments comments) {
        try {
            return commentRepository.save(comments);
        } catch (Exception e) {
            throw new BadRequestException("invalid comment input, cant save comment" + e.getMessage());
        }
    }

    @Override
    public List<Comments> findAllBySpecialistId(Long specialistId) {
        try {
            return commentRepository.findAllBySpecialistId(specialistId);
        } catch (Exception e) {
            throw new NotFoundException("wrong specialistId to find Comments and scores" + e.getMessage());
        }
    }
}
