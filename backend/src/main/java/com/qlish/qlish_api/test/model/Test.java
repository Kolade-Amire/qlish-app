package com.qlish.qlish_api.test.model;

import com.qlish.qlish_api.test.enums.TestStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tests")
public class Test {
    @Id
    private ObjectId id;
    private TestDetails testDetails;
    private TestStatus testStatus;
    private List<TestQuestion> questions;
}
