package com.qlish.qlish_api.util;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AppConstants {
    public static final LocalDateTime TIME_NOW = LocalDateTime.now(ZoneOffset.UTC);
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String USER_NOT_FOUND = "User does not exist.";
    public static final String TEST_QUESTIONS_RETRIEVAL_ERROR = "Failed to retrieve test questions. Please check request.";
    public static final String NO_MAPPING_FOUND = "No mapping found for this url";
    public static final String DATABASE_ERROR = "Unexpected Database Error.";

    public static final String QUESTION_NOT_FOUND = "Question not found.";

    public static final String GENERAL_ERROR_MESSAGE = "An unexpected error occurred";


    public static final String EXPIRED_SESSION = "Expired Session. Login again.";

    public static final String UNSUPPORTED_OPERATION = "Unsupported Operation.";

    public static final String TEST_SUBMISSION_ERROR = "An unexpected error occurred while submitting test";
    public static final String TEST_RESULT_ERROR = "An unexpected error occurred while getting test result.";
    public static final String PAGE_NOT_FOUND = "This page was not found";

    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    public static final String INCORRECT_CREDENTIALS = "Invalid Email/Password, please try again.";
    public static final String UPDATE_USER_POINTS_ERROR = "Error occurred while updating user points.";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration.";
    public static final String NOT_ENOUGH_PERMISSIONS = "You do not have sufficient permissions to access this resource.";
    public static final String ERROR_PATH = "/error";

    public static final String ALL_TIME_LEADERBOARD_KEY = "all_time_leaderboard";
    public static final String DAILY_LEADERBOARD_KEY_PREFIX = "daily_leaderboard:";

    public static ObjectId idFromString(String id){
        try{
            return new ObjectId(id);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("invalid id!");
        }
    }



}
