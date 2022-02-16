package com.chengyan.webapp.HttpController;

import com.chengyan.webapp.ExceptionController.UndesiredParameterException;
import com.chengyan.webapp.ExceptionController.UserExistedException;
import com.chengyan.webapp.ExceptionController.UserNotFoundException;
import com.chengyan.webapp.ModelController.User;
import com.chengyan.webapp.ModelController.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    // Map<String, String> allParams
    public void updateUser(Principal principal, @Valid @RequestBody Map<String, String> requestParams) {
        Set<String> set = new HashSet<>();
        set.add("password");
        set.add("last_name");
        set.add("first_name");
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


}
