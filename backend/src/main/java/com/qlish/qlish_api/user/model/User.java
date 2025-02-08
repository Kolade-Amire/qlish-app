package com.qlish.qlish_api.user.model;

import com.qlish.qlish_api.security.enums.AuthProvider;
import com.qlish.qlish_api.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String profilePictureUrl;
    @Indexed(unique = true)
    private String profileName;
    private String password;
    private Role role;
    private AuthProvider authProvider;
    private LocalDateTime passwordLastChangedDate;
    private boolean isBlocked;
    private boolean isAccountExpired;
    private boolean isEmailVerified;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    private long allTimePoints;
    private LocalDate deletionDate;


    @Override
    public String toString() {
        return "User{" +
                "_id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profilePicture='" + profilePictureUrl + '\'' +
                ", email='" + email + '\'' +
                ", username='" + profileName + '\'' +
                ", role='" + role.toString() + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", isEmailVerified='" + isEmailVerified + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                ", totalPoints='" + allTimePoints + '\'' +
                '}';
    }
}
