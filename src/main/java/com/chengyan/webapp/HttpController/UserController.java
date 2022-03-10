package com.chengyan.webapp.HttpController;

import com.chengyan.webapp.ConfigController.AwsS3Config;
import com.chengyan.webapp.ExceptionController.*;
import com.chengyan.webapp.ModelController.ProfilePic;
import com.chengyan.webapp.ModelController.ProfilePicRepository;
import com.chengyan.webapp.ModelController.User;
import com.chengyan.webapp.ModelController.UserRepository;
import com.chengyan.webapp.ServiceController.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfilePicRepository profilePicRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private AwsS3Config awsS3Config;

    @PostMapping(value = "/user")
    public User addUser(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new UserExistedException(user.getUsername());

        System.out.println(user);
        // encode password
        user.setPassword(passwordEncoder.encode(user.obtainPasswordBeforeEncoded()));

        return userRepository.save(user);
    }

    @GetMapping(value = "/user/self")
    public User getUser(Principal principal) {
        String username = principal.getName();
        System.out.println(username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @PutMapping(value = "/user/self")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateUser(Principal principal, @Valid @RequestBody Map<String, String> requestParams) {
        Set<String> set = new HashSet<>();
        set.add("password");
        set.add("last_name");
        set.add("first_name");
        set.add("username");
        for (String k : requestParams.keySet()) {
            if (!set.contains(k)) {
                throw new UndesiredParameterException("Undesired Parameter: " + k);
            }
        }

        if (requestParams.containsKey("username")) {
            if (!requestParams.get("username").equals(principal.getName())) {
                throw new UndesiredParameterException("username cannot be updated!");
            }
        }

        User userData = userRepository.findByUsername(principal.getName()).get();
        if (requestParams.containsKey("first_name"))
            userData.setFirstName(requestParams.get("first_name"));
        if (requestParams.containsKey("last_name"))
            userData.setLastName(requestParams.get("last_name"));
        if (requestParams.containsKey("password"))
            userData.setPassword(passwordEncoder.encode(requestParams.get("password")));
        userRepository.save(userData);
    }

    @GetMapping(value = "/user/self/pic")
    public ProfilePic getPic(Principal principal) {
        String username = principal.getName();
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found"))
                .getId();
        return profilePicRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Profile pic not found"));
    }

    @PostMapping(value = "/user/self/pic")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfilePic postPic(Principal principal, @RequestParam("profilePic") MultipartFile file) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        // validate
        String suffix = file.getContentType().split("/")[1];
        Pattern filenamePattern = Pattern.compile("^(jpg|png|jpeg)$");
        if (!filenamePattern.matcher(suffix).find())
            throw new BadRequestException("bad exception");

        String url = s3Service.getProfilePic(user.getId().toString(), suffix);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("filename", file.getOriginalFilename());
        metadata.put("url", url);
        metadata.put("contentType", file.getContentType());
        metadata.put("size", Long.toString(file.getSize()));

        // delete if profile pic existed
        Optional<ProfilePic> existedProfilePic = profilePicRepository.findByUserId(user.getId());
        if (existedProfilePic.isPresent()) {
            ProfilePic existedProfilePicModel = existedProfilePic.get();
            profilePicRepository.delete(
                    existedProfilePicModel
            );
            s3Service.deleteFile(existedProfilePicModel.getUrl());
        }

        ProfilePic profilePicModel = new ProfilePic();
        profilePicModel.setFilename(file.getOriginalFilename());
        profilePicModel.setUserId(user.getId());
        profilePicModel.setUrl(awsS3Config.getBucketName()+"/"+url);

        // upload to S3
        try {
            s3Service.uploadFile(
                    url,
                    file.getBytes(),
                    metadata
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profilePicRepository.save(profilePicModel);
    }

    @DeleteMapping(value = "/user/self/pic")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePic(Principal principal) {
        String username = principal.getName();
        System.out.println(username);
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found"))
                .getId();
        ProfilePic profilePicModel = profilePicRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Profile pic not found")); // TODO: not found exception
        s3Service.deleteFile(
                profilePicModel.getUrl().replace(awsS3Config+"/", "")
        );
        profilePicRepository.delete(profilePicModel);
    }
}
