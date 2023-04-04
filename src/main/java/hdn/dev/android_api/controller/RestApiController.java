package hdn.dev.android_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hdn.dev.android_api.model.ResponseObject;
import hdn.dev.android_api.model.User;
import hdn.dev.android_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class RestApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> checkLogin(@RequestBody User user) throws JsonProcessingException {
        // Kiểm tra xem user có tồn tại trong cơ sở dữ liệu hay không
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("failure", "Tài khoản hoặc mật khẩu bị sai!", ""));
        }

        // Kiểm tra xem mật khẩu có đúng không
        User dbUser = optionalUser.get();
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject("failure", "Tài khoản hoặc mật khẩu bị sai!", ""));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Đăng nhập thành công!", dbUser));
        // Đăng nhập thành công, trả về thông báo thành công
    }

}
