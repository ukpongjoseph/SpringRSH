 package dev.joseph.playground.thymeleaf_demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.joseph.playground.thymeleaf_demo.Model.User;
import dev.joseph.playground.thymeleaf_demo.Model.UsersModel;
import dev.joseph.playground.thymeleaf_demo.Repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with such credentials"));
        return new UsersModel(user);
    }
    
}
