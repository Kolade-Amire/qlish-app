package com.qlish.qlish_api.test.dtos;

import com.qlish.qlish_api.validation.ValidOption;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TestSubmissionRequest {
    @NotBlank
    private String testId;
    @NotEmpty
    private Map<String, @ValidOption String> submissions;
}

