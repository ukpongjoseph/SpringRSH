package dev.joseph.playground.thymeleaf_demo.Mapper;
import org.springframework.stereotype.Component;
import dev.joseph.playground.thymeleaf_demo.Dtos.UserRequest;
import dev.joseph.playground.thymeleaf_demo.Dtos.UserResponse;
import dev.joseph.playground.thymeleaf_demo.Model.User;


@Component
public class UserMapper {
    public User userRequestToUserModel(UserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setDepartment(request.department());
        user.setPhoneNo(request.phoneNo());
        user.setPassword(request.password());
        return user;
    }

    public UserResponse userModelToResponse(User model) {
        UserResponse response = new UserResponse(model.getId(), model.getFirstName(), model.getLastName(),
                model.getEmail(), model.getPhoneNo(), model.getRole().toString(), model.getDepartment());
        return response;
    }
}
