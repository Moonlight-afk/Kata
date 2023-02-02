package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Вася", "Пупкин", (byte) 15);
        userService.saveUser("Петя", "Петрович", (byte) 18);
        userService.saveUser("Роман", "Романович", (byte) 70);
        userService.saveUser("Генадий", "Федорович", (byte) 38);

        userService.removeUserById(3);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
