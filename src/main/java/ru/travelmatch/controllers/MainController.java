package ru.travelmatch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "ui/login";
  }

  @GetMapping("/category")
  public String category() {
    return "ui/category";
  }

  @GetMapping("/contact")
  public String contact() {
    return "ui/contact";
  }

  @GetMapping("/forgot")
  public String forgot() {
    return "ui/forgot";
  }

  @GetMapping("/page")
  public String page() {
    return "ui/page";
  }

  @GetMapping("/reset")
  public String reset() {
    return "ui/reset";
  }

  @GetMapping("/search")
  public String search() {
    return "ui/search";
  }

  @GetMapping("/single")
  public String single() {
    return "ui/single";
  }

  @GetMapping("/starter")
  public String starter() {
    return "ui/starter";
  }

  @GetMapping("/403")
  public String permission() {
    return "ui/403";
  }

  @GetMapping("/404")
  public String notFound() {
    return "ui/404";
  }

  @GetMapping("/500")
  public String internalServerError() {
    return "ui/500";
  }

  @GetMapping("/503")
  public String serviceUnavailable() {
    return "ui/503";
  }

}