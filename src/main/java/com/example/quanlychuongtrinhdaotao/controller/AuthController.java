package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.Role;
import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.entity.UserRole;
import com.example.quanlychuongtrinhdaotao.repository.RoleRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRoleRepository;
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
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

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

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));

        // Lưu người dùng mới vào cơ sở dữ liệu
        userRepository.save(user);

        // Lấy đối tượng Role (Giả sử role_id = 2 là Giảng viên)
        Role teacherRole = roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Role not found"));

        // Tạo đối tượng UserRole mới và gán role_id = 2
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(teacherRole);

        // Lưu UserRole vào bảng user_roles
        userRoleRepository.save(userRole);

        // Thêm thông báo thành công và chuyển hướng đến trang đăng nhập
        model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "register";  // Quay lại trang đăng ký để hiển thị thông báo
    }


    @GetMapping("/")
    public String homePage(Principal principal, Model model) {
        String username = principal.getName();  // Lấy tên người dùng từ đối tượng Principal
        model.addAttribute("username", username);  // Truyền tên người dùng vào model
        return "home";  // Trả về trang home.html
    }
}
