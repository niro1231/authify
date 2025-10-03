package com.springboot.authify.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

import com.springboot.authify.Service.ProfileService;
import com.springboot.authify.IO.ProfileRequest;
import com.springboot.authify.IO.ProfileResponse;
import com.springboot.authify.IO.UpdateNameRequest;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.createProfile(request);
        return response;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello, Authify is working!";
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        return profileService.getProfile(email);
    }

    @PutMapping("/profile/updatename")
    public ProfileResponse updateOwnName(
            @CurrentSecurityContext(expression = "authentication?.name") String email,
            @Valid @RequestBody UpdateNameRequest request) {
        return profileService.updateName(email, request);
    }

    @GetMapping("/admin/getallusers")
    public List<ProfileResponse> getAllUsers(
            @CurrentSecurityContext(expression = "authentication?.name") String emailOfCurrentUser) {
        return profileService.getAllProfiles(emailOfCurrentUser);
    }

    @PostMapping("/admin/adduser")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse addUserByAdmin(@Valid @RequestBody ProfileRequest request) {
        return profileService.addUser(request);
    }

    @DeleteMapping("/admin/deleteuser/{userId}")
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable String userId) {
        profileService.deleteUser(userId);
        // Return a success message in the response body
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
