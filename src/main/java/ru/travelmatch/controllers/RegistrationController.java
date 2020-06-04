package ru.travelmatch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import ru.travelmatch.services.UserService;
import ru.travelmatch.utils.SystemUser;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

  @GetMapping
  public String showMyLoginPage(Model model) {
    model.addAttribute("systemUser", new SystemUser());
    return "ui/register";
  }

    @PostMapping("/process")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser,
                                          BindingResult bindingResult, Model model) {

        bindingResult.getAllErrors().forEach(System.out::println);

        String username = systemUser.getUsername();
        String email = systemUser.getEmail();
        if (bindingResult.hasErrors()) {
            return "ui/register";
        }

        if (userService.isUsernameExist(username)) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "Username already exists!");
            return "ui/register";
        }

        if (userService.isEmailExist(email)) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "Email already exists!");
            return "ui/register";
        }

        userService.save(systemUser);
        return "redirect:/login";
  }

//  @PostMapping("/forgot/process")
//  public String processForgotUser(@ModelAttribute("systemUser")
//                                        @Valid SystemUser systemUser,
//                                        BindingResult bindingResult,
//                                        Model model,
//                                        final RedirectAttributes redirectAttributes) {
//    String username = systemUser.getEmail();
//    if (bindingResult.hasErrors()) {
//      return "ui/forgot";
//    }
//    User existing = userService.findByUsername(username);
//    if (existing != null) {
//      model.addAttribute("systemUser", systemUser);
//      model.addAttribute("forgotMessage", "Email Post");
//      return "ui/forgot";
//    }
//    return "redirect:/login";
//  }
}
