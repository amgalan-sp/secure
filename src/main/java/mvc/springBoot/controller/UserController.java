package mvc.springBoot.controller;
import mvc.springBoot.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import mvc.springBoot.entity.User;
//import mvc.springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/auth/success")
    public String getSuccessPage(){
        return "success";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/index")
    public String StartPage() {
        return "index";
    }
    @GetMapping("/admin/users")
    public String allUsers(Model model) {
        model.addAttribute("usersList", userRepository.findAll());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUserbyId(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user";

    }
    @GetMapping("/admin/signup")
    public String showSignUpForm(User user) {
        return "addPage";
    }

    @PostMapping("/admin/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addPage";
        }
        userRepository.saveAndFlush(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "editPage";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "editPage";
        }
        userRepository.save(user);
        return "redirect:/users";
    }
    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/admin/users";
    }
}