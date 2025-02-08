package com.qlish.qlish_api.test.repository;

import com.qlish.qlish_api.test.model.Test;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends MongoRepository<Test, ObjectId> {

    @Query("{ 'testDetails.userId': ?0 }")
    Optional<List<Test>> findAllUserTests(ObjectId userId);

    @Override
    @NonNull
    Optional<Test> findById(@NonNull ObjectId id);
}
