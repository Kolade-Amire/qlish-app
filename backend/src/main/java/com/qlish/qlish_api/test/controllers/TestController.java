package com.qlish.qlish_api.test.controllers;

import com.qlish.qlish_api.exceptions.GenerativeAIException;
import com.qlish.qlish_api.test.dtos.*;
import com.qlish.qlish_api.test.models.TestResult;
import com.qlish.qlish_api.test.services.TestService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTest(@PathVariable String id) {
        var test = testService.retrieveTest(id);
        return ResponseEntity.ok(test);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') || hasAuthority('admin:write')")
    public ResponseEntity<TestResponse> createTest(@RequestBody @Valid TestRequest testRequest) throws GenerativeAIException {
        var response = testService.createTest(testRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/take-test")
    @PreAuthorize("hasRole('USER') || hasAuthority('admin:write')")
    public ResponseEntity<List<TestQuestionResponse>> startTest(@PathVariable String id) {
        var response = testService.getTestQuestions(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{testId}/submit")
    @PreAuthorize("hasRole('USER') || hasAuthority('admin:write')")
    public ResponseEntity<TestResponse> submitTest(@RequestBody @Schema(example = """
        {
          "testId": "3a6dff88d94de9",
          "submissions": {
            "4a6dff88d94de6": "A",
            "98a6dff8d94de3": "B"
          }
        }
        """) @Valid TestSubmissionRequest request) {
        var response = testService.submitTest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/result")
    @PreAuthorize("hasRole('USER') || hasAuthority('admin:read')")
    public ResponseEntity<TestResult> getTestResult(@PathVariable String id) {
        var result = testService.getTestResult(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('USER') || hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteTest(@PathVariable String id){
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }


}
