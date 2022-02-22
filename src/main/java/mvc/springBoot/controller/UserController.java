package mvc.springBoot.controller;

import mvc.springBoot.repository.UserRepository;
import mvc.springBoot.service.UserServiceImpl;
import org.springframework.ui.Model;
import mvc.springBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/index")
    public String StartPage() {
        return "index";
    }

    @GetMapping("/admin/users")
    public String allUsers(Model model) {
        model.addAttribute("usersList", userServiceImpl.allUsers());
        return "users";
    }

    @GetMapping(value = "user/lk")
    public String getUserPage2(Model model, Principal principal) {
        model.addAttribute("user", userServiceImpl.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user";
    }

//    @GetMapping("/user")
//    public String getUserById(Model model, Principal principal) {
//        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        model.addAttribute("userAuth" , userService.loadUserByUsername(auth.));
//        return "user";
    //    }
    @GetMapping("/admin/signup")
    public String showSignUpForm(User user) {
        return "addPage";
    }

    @PostMapping("/admin/add")
    public String addUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "addPage";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "editPage";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "editPage";
        }
        userRepository.save(user);
        return "redirect:/admin/users";
    }
    @PostMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/admin/users";
    }
}