package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Email;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
public class EmailController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    private Boolean sendEmail(Email email) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setText(email.getBody());
            mailMessage.setSubject(email.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping
    public String processContactUserForm(@ModelAttribute Email email, String sendingUser, String receivingUser,
                                         Errors errors, Model model, RedirectAttributes redirectAttributes) {

        User sendingUserObj = userRepository.findByUsername(sendingUser);
        User receivingUserObj = userRepository.findByUsername(receivingUser);

        model.addAttribute("isCurrentUser", false);
        model.addAttribute("currentUser", sendingUserObj);
        model.addAttribute("displayedUser", receivingUserObj);

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Could not send");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:user/" + receivingUser;
        }

        email.setSubject("A message from " + sendingUser + ": " + email.getSubject());
        email.setRecipient(receivingUserObj.getEmail());
        Boolean emailSuccessful = sendEmail(email);


        if (!emailSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Could not send");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:user/" + receivingUser;
        }

        redirectAttributes.addFlashAttribute("message", "Message sent");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:user/" + receivingUser;

    }

}
