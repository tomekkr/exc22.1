package com.example.excercise22;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return createResultString(userList);
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

    @ResponseBody
    @GetMapping("/find")
    public String findUsers(@RequestParam(name = "imie", defaultValue = "unknown") String firstName,
                            @RequestParam(name = "nazwisko", defaultValue = "unknown") String lastName,
                            @RequestParam(name = "wiek", defaultValue = "0") String age) {
        try {
            int ageInt = Integer.parseInt(age);
            List<User> userList = userRepository.getAllUsers();

            boolean allParametersDefault = firstName.equals("unknown") & lastName.equals("unknown") & age.equals("0");
            boolean onlyAgeCompleted = firstName.equals("unknown") & lastName.equals("unknown");
            boolean onlyFirstNameCompleted = lastName.equals("unknown") & age.equals("0");
            boolean onlyLastNameCompleted = firstName.equals("unknown") & age.equals("0");
            boolean onlyFirstNameIsDefault = firstName.equals("unknown");
            boolean onlyLastNameIsDefault = lastName.equals("unknown");
            boolean onlyAgeIsDefault = age.equals("0");

            if (allParametersDefault) {
                return "Wprowadzono niepoprawne dane";
            } else if (onlyAgeCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else if (onlyLastNameCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getLastName().equals(lastName))
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else if (onlyFirstNameCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else if (onlyFirstNameIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getLastName().equals(lastName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else if (onlyLastNameIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else if (onlyAgeIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getLastName().equals(lastName))
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            } else {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getLastName().equals(lastName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                return createResultString(collectedUsers);
            }
        } catch (NumberFormatException e) {
            return "Wprowadzono niepoprawne dane";
        }
    }


    @ResponseBody
    @GetMapping("/delete")
    public String deleteUsers(@RequestParam(name = "imie", defaultValue = "unknown") String firstName,
                              @RequestParam(name = "nazwisko", defaultValue = "unknown") String lastName,
                              @RequestParam(name = "wiek", defaultValue = "0") String age) {
        final String successString = "Poprawnie usunięto użytkownika/ów";
        final String failString = "Nie udało się usunąć użytkownika/ów";
        try {
            int ageInt = Integer.parseInt(age);
            List<User> userList = userRepository.getAllUsers();

            boolean allParametersDefault = firstName.equals("unknown") & lastName.equals("unknown") & age.equals("0");
            boolean onlyAgeCompleted = firstName.equals("unknown") & lastName.equals("unknown");
            boolean onlyFirstNameCompleted = lastName.equals("unknown") & age.equals("0");
            boolean onlyLastNameCompleted = firstName.equals("unknown") & age.equals("0");
            boolean onlyFirstNameIsDefault = firstName.equals("unknown");
            boolean onlyLastNameIsDefault = lastName.equals("unknown");
            boolean onlyAgeIsDefault = age.equals("0");

            if (allParametersDefault) {
                return failString;
            } else if (onlyAgeCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else if (onlyLastNameCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getLastName().equals(lastName))
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else if (onlyFirstNameCompleted) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else if (onlyFirstNameIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getLastName().equals(lastName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else if (onlyLastNameIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else if (onlyAgeIsDefault) {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getLastName().equals(lastName))
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            } else {
                List<User> collectedUsers = userList.stream()
                        .filter(user -> user.getFirstName().equals(firstName))
                        .filter(user -> user.getLastName().equals(lastName))
                        .filter(user -> user.getAge() == ageInt)
                        .collect(Collectors.toList());
                userList.removeAll(collectedUsers);
                return successString;
            }
        } catch (NumberFormatException e) {
            return "Wprowadzono niepoprawne dane";
        }
    }

    private String createResultString(List<User> collectedUsers) {
        String result = "<h3>Lista użytkowników:</h3>";
        for (User user : collectedUsers) {
            result += user.getFirstName() + " " + user.getLastName() + ", wiek: " + user.getAge() + "<br/>";
        }
        return result;
    }

    private String success() {
        return "Operacja zakończona powodzeniem";
    }

    private String fail() {
        return "Operacja zakończona powodzeniem";
    }
}