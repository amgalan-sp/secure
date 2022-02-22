package mvc.springBoot.service;

import mvc.springBoot.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username);
    public User findUserById(Integer userId);
    public List<User> allUsers();
    public boolean saveUser(User user);
    public boolean saveAdmin(User user);
    public boolean deleteUser(Integer userId);

}
