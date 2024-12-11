package org.example.users.service;

import org.example.users.entity.UserProfile;
import org.example.users.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Flux<UserProfile> getUserProfiles(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }
}

