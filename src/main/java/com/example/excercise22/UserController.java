package com.example.excercise22;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ResponseBody
    @RequestMapping("/users")
    public String showUsers() {
        List<User> userList = userRepository.getAllUsers();
        String result = "<h3>Lista użytkowników:</h3>";
        for (User user : userList) {
            result += user.getFirstName() + " " + user.getLastName() + ", wiek: " + user.getAge() + "<br/>";
        }
        return result;
    }

    @RequestMapping("/add")
    public String addUser(@RequestParam(name = "imie") String firstName,
                          @RequestParam(name = "nazwisko", defaultValue = "unknown") String lastName,
                          @RequestParam(name = "wiek", defaultValue = "0") String age) {
        if (firstName.isEmpty()) {
            return "/err.html";
        }
        try {
            int userAge = Integer.parseInt(age);
            User user = new User(firstName, lastName, userAge);
            userRepository.addUser(user);
        } catch (NumberFormatException e) {
            return "/err.html";
        }
        return "/success.html";
    }

    @PostMapping("/add-with-post-method")
    public String addUserWithPostMethod(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String age) {
        int userAge = Integer.parseInt(age);
        User user = new User(firstName, lastName, userAge);
        userRepository.addUser(user);
        if (firstName.isEmpty()) {
            return "redirect:/err.html";
        } else {
            return "redirect:/success.html";
        }
    }
}
