package com.qlish.qlish_api.test.repositories;

import com.qlish.qlish_api.test.models.Test;
import com.qlish.qlish_api.test.models.TestQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends MongoRepository<Test, ObjectId> {

    @Query("{ 'userId': ?0 }")
    Optional<List<Test>> findAllUserTests(ObjectId userId);
}
