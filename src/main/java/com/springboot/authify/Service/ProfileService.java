package com.springboot.authify.Service;

import com.springboot.authify.IO.ProfileRequest;
import com.springboot.authify.IO.ProfileResponse;   

public interface ProfileService {
    ProfileResponse createProfile(ProfileRequest request); 

    ProfileResponse getProfile(String email);
}