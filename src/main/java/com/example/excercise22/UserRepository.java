package com.example.excercise22;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User("Jan", "Kowalski", 21));
        users.add(new User("Marek", "Alanowski", 37));
        users.add(new User("Joeanna", "Kot", 24));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
