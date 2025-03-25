package com.qlish.qlish_api.generativeAI;

import com.qlish.qlish_api.test.dtos.TestRequest;

public interface GenerationService {
    String getSystemInstructions();
    String getPrompt(TestRequest request);
}
