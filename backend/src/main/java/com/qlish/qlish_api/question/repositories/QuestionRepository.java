package com.qlish.qlish_api.question.repositories;

import com.qlish.qlish_api.question.models.Question;
import com.qlish.qlish_api.test.enums.Subject;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, ObjectId> {

    Page<Question> findAllBySubject(Subject subject, Pageable pageable);

    Page<Question> findAllByTopic(String topic, Pageable pageable);

}
