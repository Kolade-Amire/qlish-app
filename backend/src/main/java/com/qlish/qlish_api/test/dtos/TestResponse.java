package com.qlish.qlish_api.test.dtos;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class TestResponse {
    private String testId;
    private String message;
}
