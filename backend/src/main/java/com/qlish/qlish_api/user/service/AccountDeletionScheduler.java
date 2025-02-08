package com.qlish.qlish_api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDeletionScheduler {

    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void deleteScheduledAccounts(){
        userService.deleteUsersDueForDeletion();
    }
}
