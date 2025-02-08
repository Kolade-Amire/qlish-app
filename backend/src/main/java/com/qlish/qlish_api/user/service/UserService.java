package com.qlish.qlish_api.user.service;

import com.qlish.qlish_api.user.dto.UserDto;
import com.qlish.qlish_api.user.dto.UserUpdateRequest;
import com.qlish.qlish_api.user.model.User;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserService {

    User getUserByEmail(String email) throws UsernameNotFoundException;

    User findUserById(ObjectId id);

    UserDto retrieveUser(String id);

    User saveUser(User user);

    UserDto updateUser(String userId, UserUpdateRequest request);

    void updateUserAllTimePoints(ObjectId id, int testPoints);

    List<UserDto> getUsersWithTop20Points();

    boolean userExists(String email);

    void scheduleAccountForDeletion(String id);

    void deleteUsersDueForDeletion();


}
