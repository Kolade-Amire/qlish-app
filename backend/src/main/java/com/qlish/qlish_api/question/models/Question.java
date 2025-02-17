package com.qlish.qlish_api.question.models;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import com.qlish.qlish_api.test.enums.Subject;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "questions")
public class Question{
    @Id
    private ObjectId id;
    private String questionText;
    private Map<String, String> options;
    private Subject subject;
    private String topic;
    private DifficultyLevel level;
    private String answer;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
