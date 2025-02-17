package com.qlish.qlish_api.user.controllers;

import com.qlish.qlish_api.user.dto.UserDto;
import com.qlish.qlish_api.user.dto.UserUpdateRequest;
import com.qlish.qlish_api.user.dto.UserViewResponse;
import com.qlish.qlish_api.user.services.UserService;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') || T(java.util.UUID).fromString(#id) == authentication.principal.getId()")
    public ResponseEntity<UserDto> retrieveUser(@PathVariable String id) {
        UserDto user = userService.retrieveUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/view-user/{id}")
    public ResponseEntity<UserViewResponse> viewUser(@PathVariable String id) {
        UserDto user = userService.retrieveUser(id);
        UserViewResponse response = new UserViewResponse(user.getProfilePictureUrl(), user.getProfileName(), user.getAllTimePoints());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("T(java.util.UUID).fromString(#id) == authentication.principal.getId()")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest userDetails) {
        UserDto updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("T(java.util.UUID).fromString(#id) == authentication.principal.getId()")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        userService.scheduleAccountForDeletion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Login</title>
                </head>
                <body>
                    <h1>Successfully logged in!.</h1>
                </body>
                </html>
                """;

        return ResponseEntity.ok().body(html);
    }
}
