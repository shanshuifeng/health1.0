package com.ncu.controller;

import com.healthsys.common.dao.UserDAO;
import com.healthsys.common.model.User;

import java.util.List;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public boolean updateUserProfile(User user) {
        return userDAO.updateUserProfile(user);
    }

    public List<User> getAllMedicalUsers() {
        return userDAO.getAllMedicalUsers();
    }

    public boolean resetUserPassword(int userId) {
        return userDAO.updateUserPassword(userId, UserDAO.INITIAL_PASSWORD);
    }
}