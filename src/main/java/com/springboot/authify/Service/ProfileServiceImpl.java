package com.springboot.authify.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.authify.Entity.UserEntity;
import com.springboot.authify.IO.ProfileRequest;
import com.springboot.authify.IO.ProfileResponse;
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
            .password(passwordEncoder.encode(request.getPassword()))
            .isAccountVerified(false)
            .resetOtpExpireAt(LocalDateTime.now())
            .verifyOtp(null)
            .verifyOtpExpireAt(LocalDateTime.now())
            .resetOtp(null)
            .build();
    
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
            .email(newProfile.getEmail())
            .name(newProfile.getName())
            .userId(newProfile.getUserId())
            .build();
    }
}
