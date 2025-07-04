package com.hs.utils;

import com.hs.dao.UserDAO;
import com.ncu.Common.Users;

public class LoginController {
    private UserDAO userDAO;
    private LoginListener loginListener;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void handleLogin(String username, String password) {
        Users user = userDAO.getUserByUsername(username);

        if (user == null) {
            if (loginListener != null) {
                loginListener.onLoginFailed("用户名或密码错误");
            }
            return;
        }

        if (!user.getPassword().equals(password)) {
            if (loginListener != null) {
                loginListener.onLoginFailed("用户名或密码错误");
            }
            return;
        }

        // 判断用户角色
        if ("doctor".equals(user.getRole()) || "admin".equals(user.getRole())) {
            // 医护或管理员用户直接跳转到医护界面
            if (loginListener != null) {
                loginListener.onLoginSuccess(user);
            }
        } else {
            // 普通用户跳转到主界面
            if (loginListener != null) {
                loginListener.onLoginSuccess(user);
            }
        }
    }

    public Users login(String username, String password) {
        Users user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public interface LoginListener {
        void onLoginSuccess(Users user);
        void onFirstLogin(Users user);
        void onLoginFailed(String errorMessage);
        void onPasswordChangeSuccess(Users user);
        void onPasswordChangeFailed(String errorMessage);
    }


    public void handleChangePassword(Users user, String newPassword, String confirmPassword) {
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            if (loginListener != null) {
                loginListener.onPasswordChangeFailed("密码不能为空");
            }
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            if (loginListener != null) {
                loginListener.onPasswordChangeFailed("两次输入的密码不一致");
            }
            return;
        }

        if (newPassword.length() < 6) {
            if (loginListener != null) {
                loginListener.onPasswordChangeFailed("密码长度不能少于6位");
            }
            return;
        }

        UserDAO userDAO = new UserDAO();
        if (userDAO.updateUserPassword(user.getId(), newPassword)) {
            if (loginListener != null) {
                loginListener.onPasswordChangeSuccess(user);
            }
        } else {
            if (loginListener != null) {
                loginListener.onPasswordChangeFailed("密码修改失败");
            }
        }
    }
}

