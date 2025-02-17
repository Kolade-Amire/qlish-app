package com.qlish.qlish_api.question.controllers;

import com.qlish.qlish_api.question.dtos.QuestionRequest;
import com.qlish.qlish_api.question.dtos.QuestionDto;
import com.qlish.qlish_api.question.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/subject/{subject}")
    public ResponseEntity<Page<QuestionDto>> retrieveBySubject(@PathVariable String subject, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        var questions = questionService.getBySubject(subject, page, size);
        return ResponseEntity.ok(questions);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/topic/{topic}")
    public ResponseEntity<Page<QuestionDto>> retrieveByTopic(@PathVariable String topic, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        var questions = questionService.getByTopic(topic, page, size);
        return ResponseEntity.ok(questions);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> retrieveById(@PathVariable String id) {
        var question = questionService.retrieveQuestion(id);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasAuthority('admin:write')")
    @PostMapping("/new")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody @Valid QuestionRequest request) {
        var response = questionService.createQuestion(request);
        URI newQuestionLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(newQuestionLocation).body(response);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('admin:write')")
    @PatchMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable String id, @RequestBody QuestionRequest request) {
        var response = questionService.updateQuestion(id, request);
        URI newQuestionLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(newQuestionLocation).body(response);
    }

}
