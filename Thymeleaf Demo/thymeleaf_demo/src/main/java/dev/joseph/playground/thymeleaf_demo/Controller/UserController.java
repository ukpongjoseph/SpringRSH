package dev.joseph.playground.thymeleaf_demo.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;





@Controller
// @RequestMapping(value = "/myapp")
public class UserController {


    @GetMapping(value = "/")
    public String welcome(Model model) {
        model.addAttribute("Title", "Welcome to the AJ software Hub Staff Management system");
        return "welcome";
    }

    @GetMapping("/th/welcome")
    public String hello(Model model){
        model.addAttribute("greetings", "Welcome sorry for the stress but you needed to be authenticated");
        return "hello";
    }

    @GetMapping("/th/login")
    public String login() {
        return "login";
    }

    @GetMapping("/th/register")
    public String register() {
        return "register";
    }
    
}
