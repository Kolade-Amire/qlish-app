package com.qlish.qlish_api.user.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.exception.UserPointsUpdateException;
import com.qlish.qlish_api.user.dto.UserDto;
import com.qlish.qlish_api.user.dto.UserMapper;
import com.qlish.qlish_api.user.dto.UserUpdateRequest;
import com.qlish.qlish_api.user.model.User;
import com.qlish.qlish_api.user.repository.UserRepository;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.qlish.qlish_api.util.AppConstants.TIME_NOW;
import static com.qlish.qlish_api.util.AppConstants.UPDATE_USER_POINTS_ERROR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(ObjectId id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with ID: %s not found", id.toHexString())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto retrieveUser(String id) {
        try {
            ObjectId userId = new ObjectId(id);
            User user = findUserById(userId);
            return UserMapper.mapUserToDto(user);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new CustomQlishException("error retrieving user");
        }
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (MongoWriteException e) {
            LOGGER.error("Mongo write error: {}", e.getMessage(), e);
            throw new CustomQlishException("an error occurred while saving user");
        } catch (MongoTimeoutException e) {
            LOGGER.error("Mongo timeout error: {}", e.getMessage(), e);
            throw new CustomQlishException("an error occurred while saving user");
        } catch (DataAccessException e) {
            LOGGER.error("Data access error: {}", e.getMessage(), e);
            throw new CustomQlishException("an error occurred while saving user");
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new CustomQlishException("an error occurred while saving user");
        }
    }

    @Override
    @Transactional
    public UserDto updateUser(String userId, UserUpdateRequest request) {

        try {
            var id = new ObjectId(userId);
            User user = findUserById(id);

            //allowed updates
            if (request.getFirstname() != null) {
                user.setFirstname(request.getFirstname());
            }
            if (request.getLastname() != null) {
                user.setFirstname(request.getLastname());
            }
            if (request.getProfileName() != null) {
                if (isProfileNameTaken(request.getProfileName())) {
                    throw new CustomQlishException("Username already taken.");
                }
                user.setFirstname(request.getProfileName());
            }

            User savedUser = saveUser(user);
            return UserMapper.mapUserToDto(savedUser);

        } catch (Exception e) {
            LOGGER.error("Error occurred while updating user. {}", e.getMessage(), e);
            throw new CustomQlishException("an error occurred while saving updates");
        }
    }

    @Override
    @Transactional
    public void updateUserAllTimePoints(ObjectId id, int testPoints) {
        try {
            User user = findUserById(id);
            var existingPoints = user.getAllTimePoints();
            user.setAllTimePoints(existingPoints + testPoints);
        } catch (Exception e) {
            throw new UserPointsUpdateException(UPDATE_USER_POINTS_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersWithTop20Points() {
        var topUsers = userRepository.findTop20ByOrderByAllTimePointsDesc();
        return UserMapper.mapUserListToDto(topUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void scheduleAccountForDeletion(String id) {
        ObjectId userId = new ObjectId(id);
        User user = findUserById(userId);
        var today_sDate = LocalDate.from((TIME_NOW).plusDays(30));
        user.setDeletionDate(today_sDate);
        saveUser(user);
    }

    private boolean isProfileNameTaken(String profileName) {
        return userRepository.findByProfileNameIgnoreCase(profileName).isPresent();
    }

    @Override
    @Transactional
    public void deleteUsersDueForDeletion() {
        var today_sDate = LocalDate.now();
        try {
            userRepository.deleteAllByDeletionDateBefore(today_sDate);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while trying to delete accounts due for deletion today {} ", today_sDate, e);
        }
    }
}
