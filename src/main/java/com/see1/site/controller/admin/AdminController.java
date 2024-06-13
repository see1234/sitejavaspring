package com.see1.site.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.see1.site.model.Entry;
import com.see1.site.model.Service;
import com.see1.site.model.Time;
import com.see1.site.model.User;
import com.see1.site.repository.TimeRepository;
import com.see1.site.service.EntryService;
import com.see1.site.service.Services;
import com.see1.site.service.TimeService;
import com.see1.site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class AdminController {
    @Autowired
    EntryService es;
    @Autowired
    TimeRepository tr;
    @Autowired
    UserService userService;
    @Autowired
    TimeService timeService;
    @Autowired
    Services services;
    @GetMapping("/admin/panel")
    public ModelAndView checker(Model model) {

        List<Entry> entries;

        entries = es.getAllService();
        List<Entry> reverseEntries = new ArrayList<Entry>();
        for(int i = entries.size() - 1; i >= 0;i--) {
            List<Time> times = tr.findByTimeName(entries.get(i).getTime());
            for(Time time : times) {
                if(time.getDate().equalsIgnoreCase(entries.get(i).getDate())) {
                    if(time.getActive()) {
                        entries.get(i).setActive("Забронировано");
                        reverseEntries.add(entries.get(i));

                    }
                    else {
                        entries.get(i).setActive("Забронировать");
                        reverseEntries.add(entries.get(i));

                    }

                }
            }


        }

        model.addAttribute("entries", reverseEntries);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/admin/adminpanel");
        return modelAndView;
    }
    @PostMapping("/admin/addtime")
    public ModelAndView addTime(Model model,
            @RequestParam String date,
            @RequestParam String times,
            @RequestParam String specialist
    ) {
        timeService.addTime(date,times,specialist);

        return adminpanel2(model);
    }
    @PostMapping("/admin/addservice")
    public ModelAndView addTime(Model model,
            @RequestParam String service,

            @RequestParam String specialist
    ) {
        services.addServices(service,specialist);

        return adminpanel2(model);
    }
    @GetMapping("/admin/panel2")
    public ModelAndView adminpanel2(Model model){
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/adminpanel2");
        return modelAndView;
    }
    @GetMapping("/admin/delete/{id}")
    public ModelAndView delete(@PathVariable int id) {

        es.deleteEntry(id);
        return new ModelAndView("redirect:/admin/panel");
    }
    @GetMapping("/admin/set/{id}")
    public ModelAndView set(@PathVariable int id) {

        es.setEntryTime(id);


        for(Entry entry : es.getAllService()) {
            if(entry.getId() != id) {
                if(entry.getTime().equalsIgnoreCase(es.getEntry(id).getTime()) && entry.getDate().equalsIgnoreCase(es.getEntry(id).getDate())) {
                    es.deleteEntry(entry.getId());
                }
            }
        }
        return new ModelAndView("redirect:/admin/panel");
    }
}