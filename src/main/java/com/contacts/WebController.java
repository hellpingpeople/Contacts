package com.contacts;

import com.contacts.aspect.LoginAspect;
import com.contacts.db.HibernateUtil;
import com.contacts.model.Contact;
import com.contacts.model.User;
import com.contacts.model.UserSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import static com.contacts.db.HibernateUtil.*;

@Controller
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class WebController {
    @Bean
    public LoginAspect loginAspect() {
        return new LoginAspect();
    }

    @RequestMapping("/")
    public String home(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        contacts(model, request, response);
        return "showContacts";
    }

    @RequestMapping("/logout")
    public String logout(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        request.getSession(true).invalidate();
        login(model, request, response);
        return "login";
    }

    @RequestMapping("/contacts")
    public String contacts(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = getLoggedUser(request);
        model.addAttribute("user", user);
        return "showContacts";
    }

    @RequestMapping("/contacts/remove/{id}")
    public String removeContact(
            @PathVariable("id") Integer id,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        HibernateUtil.removeContact(id);
        contacts(model, request, response);
        return "showContacts";
    }

    @RequestMapping(value = "/contacts/edit/{id}", method=RequestMethod.GET)
    public String editContact(
            @PathVariable Integer id,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Contact contact = HibernateUtil.getContact(id);
        model.addAttribute("contact", contact);
        return "editStart";
    }

    @RequestMapping(value = "/contacts/edit/{id}", method=RequestMethod.POST)
    public String editContact(
            @PathVariable Integer id,
            @ModelAttribute Contact contact,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        contact.setUser(HibernateUtil.getLoggedUser(request));
        HibernateUtil.editContact(contact);
        //model.addAttribute("contact", contact);
        contacts(model, request, response);
        return "showContacts";
    }

    @RequestMapping(value = "/contacts/new", method=RequestMethod.GET)
     public String newContact(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        model.addAttribute("contact", new Contact());
        return "newContact";
    }

    @RequestMapping(value = "/contacts/new", method=RequestMethod.POST)
    public String newContact(
            Model model,
            @ModelAttribute Contact contact,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User loggedUser = HibernateUtil.getLoggedUser(request);
        contact.setUser(loggedUser);
        save(contact);
        contacts(model, request, response);
        return "showContacts";
    }


    @RequestMapping(value = "/registration", method=RequestMethod.GET)
    public String registration(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method=RequestMethod.POST)
    public String registration(
            Model model,
            @ModelAttribute User user,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        save(user);
        login(model, user, request, response);
        return "showContacts";
    }

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String login(
            Model model,
            @ModelAttribute User user,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User userFromDB = HibernateUtil.checkUser(user);
        if (userFromDB != null) {
            model.addAttribute("loginStatus", true);
            model.addAttribute("user", userFromDB);
            UserSession userSession = new UserSession();
            userSession.setUser(userFromDB);
            HibernateUtil.save(userSession);
            request.getSession().setAttribute("loggedIn", userSession.getSessionId());
        } else {
            model.addAttribute("loginStatus", false);
        }
        return "loginEnd";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebController.class, args);

    }
}
