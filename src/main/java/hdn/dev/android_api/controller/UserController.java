package hdn.dev.android_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hdn.dev.android_api.model.*;
import hdn.dev.android_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("user/{id}")
    public ResponseEntity<ResponseObject> getUser(@PathVariable Long id) {
        User user = userRepository.findByUserId(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Success", user));
        }
        return null;
    }

    @PutMapping("user/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updateUser = userRepository.findByUserId(id);
        if (updateUser == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "You are updating a user that does not exist", null));
        } else {
            if (updateUser.getUsername() != user.getUsername())
                updateUser.setUsername(user.getUsername());
            if (updateUser.getFullname() != user.getFullname())
                updateUser.setFullname(user.getFullname());
            if (updateUser.getPassword() != user.getPassword())
                updateUser.setPassword(user.getPassword());
            if (updateUser.getPhone() != user.getPhone())
                updateUser.setPhone(user.getPhone());
            if (updateUser.getEmail() != user.getEmail())
                updateUser.setEmail(user.getEmail());
            if (updateUser.getAddress() != user.getAddress())
                updateUser.setAddress(user.getAddress());
            if (updateUser.getAvatar() != user.getAvatar())
                updateUser.setAvatar(user.getAvatar());
            if (updateUser.getYear() != user.getYear())
                updateUser.setYear(user.getYear());
            userRepository.save(updateUser);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Update user successfully", updateUser));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseObject> checkLogin(@RequestBody User user) throws JsonProcessingException {
        // Kiểm tra xem user có tồn tại trong cơ sở dữ liệu hay không
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Tài khoản này không tồn tại!", null));
        }
        // Kiểm tra xem mật khẩu có đúng không
        User dbUser = optionalUser.get();
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Tài khoản hoặc mật khẩu bị sai!", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Đăng nhập thành công!", dbUser));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody User user) throws JsonProcessingException {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Username is already taken!", ""));
        } else {

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Registration successful!", user));
        }
    }
}
