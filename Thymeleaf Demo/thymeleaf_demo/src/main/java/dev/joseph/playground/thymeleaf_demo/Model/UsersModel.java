package dev.joseph.playground.thymeleaf_demo.Model;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class UsersModel implements UserDetails{

    private User user;

    public UsersModel(User user){
        this.user = user;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
   }

   @Override
   public String getUsername(){
    return user.getEmail();
   }

   @Override
   public String getPassword(){
    return user.getPassword();
   }

   // Do not expose, expose only when needed
   public User getUser(){
      return user;
   }
}
