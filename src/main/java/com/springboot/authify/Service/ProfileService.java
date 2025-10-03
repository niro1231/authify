package com.springboot.authify.Service;

import java.util.List;

import com.springboot.authify.IO.ProfileRequest;
import com.springboot.authify.IO.ProfileResponse;
import com.springboot.authify.IO.UpdateNameRequest;   

public interface ProfileService {
    ProfileResponse createProfile(ProfileRequest request); 
    ProfileResponse getProfile(String email);
    ProfileResponse updateName(String emailOfCurrentUser, UpdateNameRequest request);
    List<ProfileResponse> getAllProfiles(String emailOfCurrentUser);
    ProfileResponse addUser(ProfileRequest request);
    void deleteUser(String userId);
}