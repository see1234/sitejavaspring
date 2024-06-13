package com.see1.site.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.see1.site.model.Service;
import com.see1.site.model.Time;
import com.see1.site.model.User;
import com.see1.site.service.EntryService;
import com.see1.site.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
@Log
public class WelcomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private EntryService entryService;
    @GetMapping("/prices")
    public String prices(){
        return "prices.html";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact.html";
    }
    @PostMapping("/add")
    public ModelAndView addEntryAction(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phone,
            @RequestParam String date,
            @RequestParam String specialist,
            @RequestParam String service,
            @RequestParam String time) {
            entryService.addEntry(name, surname, phone, service, time, date, specialist);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("success");
            return modelAndView;
        }

    @GetMapping("/")
    public String welcome(Model model){
        HashMap<User, List<Service>> usersAndServices = new HashMap<>();
        HashMap<User, List<Time>> usersAndTime = new HashMap<>();
        HashMap<Integer, List<Service>> usersIdAndServices = new HashMap<>();
        HashMap<Integer, List<Time>> usersAndTimes = new HashMap<>();
        List<User> specialistList = userService.findUsersByRoleId("ADMIN");

        for(User specialist : specialistList) {
            List<Service> services = userService.getServicesForUser(specialist);
            List<Time> times = userService.getTimesForUser(specialist);
            usersAndServices.put(specialist, services);
            usersAndTime.put(specialist, times);
            usersIdAndServices.put(specialist.getId(), services);
            usersAndTimes.put(specialist.getId(), times);
        }
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        String specialistsJson = objectMapper.writeValueAsString(usersIdAndServices);
            model.addAttribute("specialistsJson", specialistsJson);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String timesJson = objectMapper.writeValueAsString(usersAndTimes);
            model.addAttribute("timesJson", timesJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("specialistsTime", usersAndTime);
        model.addAttribute("specialists", usersAndServices);

        return "index";
    }

}
