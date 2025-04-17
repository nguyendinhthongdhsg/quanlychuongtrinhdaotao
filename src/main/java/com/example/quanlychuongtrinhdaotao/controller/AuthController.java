package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Use PasswordEncoder interface, which will use BCryptPasswordEncoder

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu đã đăng nhập rồi (không phải anonymous)
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }

        // Nếu chưa đăng nhập
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());  // Thêm đối tượng 'user' vào mô hình
        return "register";  // Trả về trang đăng ký
    }

    @PostMapping("/register")
    public String register(User user, Model model) {

        // Kiểm tra xem tên đăng nhập đã tồn tại hay chưa
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
            return "register";  // Trả về trang đăng ký nếu có lỗi
        }

        user.setVaiTro("giangvien");
        user.setTrangThai(true);

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Lưu người dùng mới vào cơ sở dữ liệu
        userRepository.save(user);

        // Thêm thông báo thành công và chuyển hướng đến trang đăng nhập
        model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "register";  // Quay lại trang đăng ký để hiển thị thông báo
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }
}
