package com.qlish.qlish_api.user.repositories;

import com.qlish.qlish_api.user.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmail(String email);

    List<User> findTop20ByOrderByAllTimePointsDesc();

    Optional<User> findByProfileNameIgnoreCase(String profileName);

    void deleteAllByDeletionDateBefore(LocalDate now);

}
