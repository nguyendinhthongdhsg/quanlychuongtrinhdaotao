package com.example.quanlychuongtrinhdaotao.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            int status = Integer.parseInt(statusCode.toString());

            if (status == 404) {
                model.addAttribute("errorMessage", "Trang bạn tìm không tồn tại.");
                return "error-404";
            } else if (status == 500) {
                model.addAttribute("errorMessage", "Lỗi máy chủ nội bộ.");
                return "error-500";
            } else {
                model.addAttribute("errorMessage", "Đã xảy ra lỗi không xác định.");
                return "error";
            }
        }

        model.addAttribute("errorMessage", "Đã xảy ra lỗi.");
        return "error";
    }
}
