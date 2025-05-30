package com.qlish.qlish_api.user.services;


import com.qlish.qlish_api.exceptions.CustomQlishException;
import com.qlish.qlish_api.security.enums.AuthProvider;
import com.qlish.qlish_api.user.model.User;
import com.qlish.qlish_api.user.model.UserPrincipal;
import com.qlish.qlish_api.user.model.OAuth2UserInfo;
import com.qlish.qlish_api.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomOAuth2UserService.class);


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        LOGGER.info("OAuth2 User Attributes: {}", oauth2User.getAttributes());

        try {
            return processOAuth2User(userRequest, oauth2User);
        } catch (Exception exception) {
            LOGGER.error("Exception occurred: {}",exception.getMessage(), exception);
            throw new OAuth2AuthenticationException("Error processing OAuth2 user: " + exception.getMessage());
        }
    }

    public OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration()
                        .getRegistrationId(), oauth2User.getAttributes()
        );

        User user;
        try {
            user = userService.getUserByEmail(oAuth2UserInfo.getEmail());
            user = updateExistingUser(user, oAuth2UserInfo);
        } catch (UsernameNotFoundException ex) {
            user = registerUser(userRequest, oAuth2UserInfo);
        }catch (Exception e){
            throw new CustomQlishException("Unable to process user");
        }
        return UserPrincipal.create(user, oauth2User.getAttributes());
    }


    public User registerUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) throws OAuth2AuthenticationException {

        var user = buildUserFromOAuth2UserInfo(userRequest, oAuth2UserInfo);
        return userService.saveUser(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {

        if(existingUser.getFirstname() == null && existingUser.getLastname() == null){
            var name = oAuth2UserInfo.getName().split(" ");
            var firstname = name[0];
            var lastname = name[1];

            existingUser.setFirstname(firstname);
            existingUser.setLastname(lastname);
        }

        if(existingUser.getProfilePictureUrl() == null){
            existingUser.setProfilePictureUrl(oAuth2UserInfo.getImageUrl());
        }

        existingUser.setLastLoginAt(LocalDateTime.now());
        return  userService.saveUser(existingUser);
    }

    private User buildUserFromOAuth2UserInfo(OAuth2UserRequest userRequest, OAuth2UserInfo userInfo){

        var name = userInfo.getName().split(" ");
        var firstname = name[0];
        var lastname = name[1];

        var emailSplit = userInfo.getEmail().split("@");

        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .profileName(emailSplit[0])
                .email(userInfo.getEmail())
                .profilePictureUrl(userInfo.getImageUrl())
                .authProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase()))
                .role(Role.USER)
                .isBlocked(false)
                .isAccountExpired(false)
                .isEmailVerified(true)
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();
    }
}
