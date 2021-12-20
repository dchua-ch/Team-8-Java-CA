package sg.edu.iss.team8.leaveApp.service;

import sg.edu.iss.team8.leaveApp.model.User;

import java.util.ArrayList;

public interface UserService {

    ArrayList<User> findAllUsers();

    User findUser(String userId);

    User createUser(User user);

    User changeUser(User user);

    void removeUser(User user);

}
