package hdn.dev.android_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.model.User;
import hdn.dev.android_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody User user) throws JsonProcessingException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Username is already taken!", ""));
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setPhone(user.getPhone());

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Registration successful!", newUser));
    }


}
