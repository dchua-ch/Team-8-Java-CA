package sg.edu.iss.team8.leaveApp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepo userRepository;


    @Override
    @Transactional
    public ArrayList<User> findAllUsers() {
        ArrayList<User> ul = new ArrayList<>();
        ArrayList<User> employees = (ArrayList<User>) userRepository.getAllEmployees();
//        ArrayList<User> managers = (ArrayList<User>) userRepository.getAllManagers();
//        ArrayList<User> admins = (ArrayList<User>) userRepository.getAlladmins();

        ul.addAll(employees);
//        ul.addAll(managers);
//        ul.addAll(admins);

        return ul;
    }

    @Override
    @Transactional
    public User findUser(String userId) {
        return userRepository.findById(Integer.valueOf(userId)).orElse(null);
    }


    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public User changeUser(User user) {
        return userRepository.saveAndFlush(user);
    }


    @Override
    @Transactional
    public void removeUser(User user) {
        userRepository.delete(user);
    }


}
