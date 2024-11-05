package org.example.polezno.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.polezno.Entities.Role;
import org.example.polezno.Entities.User;
import org.example.polezno.Repositories.ProductRepository;
import org.example.polezno.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", new User());
        return "main";
    }
    @GetMapping("/order")
    public String order(Model model){
        model.addAttribute("products",productRepository.findAll() );
        return "order";
    }
    @GetMapping("/account")
    public String account(){
        return "account";
    }
    @PostMapping("/login")
    public String loginSent(HttpServletResponse response, @ModelAttribute("user") User User) {
        User user = userRepository.findByLogin(User.getLogin());
        if (user != null) {
            if (user.getPassword().equals(User.getPassword())) {
                setCookie(response, user);
                return "redirect:/order";
            } else {
                return "redirect:/?error";
            }
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/signup")
    public String signUpSent(HttpServletResponse response, @ModelAttribute("user") User user) {
        user.setPassword(user.getPassword());
        user.setRole(Role.USER);
        userRepository.save(user);
        setCookie(response, user);
        return "redirect:/order";

    }

    private void setCookie(HttpServletResponse response, User user) {
        Cookie cookie = new Cookie("Haruka", user.getLogin());
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }
}
