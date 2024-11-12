package org.example.polezno.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.polezno.Entities.*;
import org.example.polezno.Repositories.CartItemRepository;
import org.example.polezno.Repositories.OrderRepository;
import org.example.polezno.Repositories.ProductRepository;
import org.example.polezno.Repositories.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", new User());
        return "main";
    }

    @GetMapping("/order")
    public String order(Model model, HttpServletRequest request) {
        model.addAttribute("products", productRepository.findAll());
        String login = getCookie("Haruka", request);
        User user = userRepository.findByLogin(login);
        user.setOrders(null);
        model.addAttribute("user", user);
        return "order";
    }

    @PostMapping("/order")
    @ResponseBody
    public String SentOrder(@RequestBody String requestDataList, HttpServletRequest request) throws ParseException {
        JSONObject jsonData = (JSONObject) new JSONParser().parse(requestDataList);
        String nameUser = getCookie("Haruka", request);
        User user = userRepository.findByLogin(nameUser);
        String address = (String) jsonData.get("address");
        Double totalSum = (Double) jsonData.get("total_sum");
        Order order = new Order(user, Status.ACTIVE, address,totalSum);
        order.setOrderDateTime(LocalDateTime.now());
        orderRepository.save(order);
        List<Order> orders = orderRepository.findAll();
        int orderId = orders.getLast().getId();
        int i = 1;
        JSONObject cartObject = (JSONObject) jsonData.get("cart");
        while (cartObject.get(String.valueOf(i)) != null) {
            JSONObject cartItemObject = (JSONObject) cartObject.get(String.valueOf(i));
            int quantity = ((Long) cartItemObject.get("quantity")).intValue();
            double totalPrice = ((Number) cartItemObject.get("totalPrice")).doubleValue();
            String name = (String) cartItemObject.get("name");
            Product product = productRepository.findByNameProd(name);
            CartItem cartItem = new CartItem(quantity, totalPrice, orderRepository.findById(orderId), product);
            cartItemRepository.save(cartItem);
            i++;
        }
        return "{result:'complete'}";
    }

    @GetMapping("/account")
    public String account(Model model, HttpServletRequest request) {
        String login = getCookie("Haruka", request);
        User user = userRepository.findByLogin(login);
        model.addAttribute("user", user);
        List<Order> orders = orderRepository.findByUser(user);
        for (Order o : orders) {
            o.setUser(null);
            for (CartItem cartItem : o.getCartItems()) {
                cartItem.setOrder(null);
            }
        }
        model.addAttribute("orders", orders);
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

    @PostMapping("/saveUserInfo")
    public String saveUserInfo(@ModelAttribute("user") User updateUser, HttpServletRequest request, HttpServletResponse response) {
        String login = getCookie("Haruka", request);
        User user = userRepository.findByLogin(login);
        if (!login.equals(updateUser.getLogin())) {
            setCookie(response, updateUser);
        }
        updateUser.setId(user.getId());
        updateUser.setPassword(user.getPassword());
        updateUser.setRole(user.getRole());
        updateUser.setOrders(user.getOrders());
        updateUser.setAddresses(user.getAddresses());
        userRepository.save(updateUser);
        return "redirect:/aсcount";
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestParam("address") String address, HttpServletRequest request) {
        String login = getCookie("Haruka", request);
        User user = userRepository.findByLogin(login);
        user.addAddress(address);
        userRepository.save(user);
        return "redirect:/account";
    }

    @PostMapping("delAddress")
    public String delAddress(@RequestParam("companyAddress") String address, HttpServletRequest request) {
        String login = getCookie("Haruka", request);
        User user = userRepository.findByLogin(login);
        user.delAddress(address);
        userRepository.save(user);
        return "redirect:/account";
    }

    private void setCookie(HttpServletResponse response, User user) {
        Cookie cookie = new Cookie("Haruka", user.getLogin());
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    public String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
