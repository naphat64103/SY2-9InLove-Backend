package sit.project.projectv1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.project.projectv1.entities.User;
import sit.project.projectv1.exceptions.ItemNotFoundException;
import sit.project.projectv1.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAllUser();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ItemNotFoundException("This user does not exits!!!"));
    }

    public User createUser(User user) {
        User saveUser = userRepository.saveAndFlush(user);
        userRepository.refresh(saveUser);
        return saveUser;
    }

    public User updateUser(Integer userId, User user) {
        User user1 = userRepository.findById(userId).orElseThrow(
                () -> new ItemNotFoundException("This user does not exits!!!"));
        user1.setUsername(user.getUsername());
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setRole(user.getRole());
        User saveUser = userRepository.saveAndFlush(user1);
        userRepository.refresh(saveUser);
        return saveUser;
    }

    public void deleteUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ItemNotFoundException("This user does not exits!!!");
        }
    }
}
