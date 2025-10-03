package com.springboot.authify.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.authify.Entity.UserEntity;
import com.springboot.authify.IO.ProfileRequest;
import com.springboot.authify.IO.ProfileResponse;
import com.springboot.authify.IO.UpdateNameRequest;
import com.springboot.authify.Repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        UserEntity newProfile = convertoUserEntity(request);
        if(!userRepository.existsByEmail(newProfile.getEmail())) {
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");     
        
    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException( "Profile not found"+ email));

        return convertToProfileResponse(existingUser);       
    }
    
    private UserEntity convertoUserEntity(ProfileRequest request) {
        return UserEntity.builder()
            .email(request.getEmail())
            .userId(UUID.randomUUID().toString())
            .name(request.getName())
            .role(request.getRole())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
    
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
            .email(newProfile.getEmail())
            .name(newProfile.getName())
            .userId(newProfile.getUserId())
            .role(newProfile.getRole())
            .build();
    }

    @Override
    public ProfileResponse updateName(String emailOfCurrentUser, UpdateNameRequest request) {
        UserEntity user = userRepository.findByEmail(emailOfCurrentUser)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailOfCurrentUser));

        user.setName(request.getName());
        UserEntity updatedUser = userRepository.save(user);

        return convertToProfileResponse(updatedUser);
    }

    @Override
    public List<ProfileResponse> getAllProfiles(String emailOfCurrentUser) {
        // 1. Fetch all users from the database
        return userRepository.findAll()
                .stream()
                // 2. Filter out the user who is making the request
                .filter(user -> !user.getEmail().equals(emailOfCurrentUser))
                // 3. Convert each UserEntity to a ProfileResponse
                .map(this::convertToProfileResponse)
                // 4. Collect the results into a List
                .collect(Collectors.toList());
    }

    @Override
    public ProfileResponse addUser(ProfileRequest request) {
        // Reuse the logic from createProfile to prevent duplicate emails
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        UserEntity newUser = convertoUserEntity(request);
        newUser = userRepository.save(newUser);
        return convertToProfileResponse(newUser);
    }

    // --- ADD THIS METHOD TO DELETE A USER ---
    @Override
    public void deleteUser(String userId) {
        // First, check if a user with the given userId exists
        if (!userRepository.existsByUserId(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
        userRepository.deleteByUserId(userId);
    }
}
