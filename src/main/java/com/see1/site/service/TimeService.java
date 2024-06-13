package com.see1.site.service;

import com.see1.site.model.Time;
import com.see1.site.repository.EntryRepository;
import com.see1.site.repository.TimeRepository;
import com.see1.site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TimeService {

    @Autowired
    EntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TimeRepository timeRepository;
    public List<Time> getAllService() {
        return StreamSupport.stream(timeRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }



    public void saveTime(Time time) {
        timeRepository.save(time);
    }

    public void deleteTime(String date, String time) {
       List<Time> times  = timeRepository.findByTimeName(time);
       for(Time time_ : times ) {
           if(time_.getDate().equalsIgnoreCase(date)) {
               timeRepository.deleteById(time_.getId());
           }
       }
    }
    public Time getTime(int id) {
        if(timeRepository.existsById(id)) {

            return timeRepository.getOne(id);
        }
        return null;
    }

    public void addTime(String date, String time_, String user) {
        Time time = new Time();
        time.setTimeName(time_);
        time.setDate(date);
        time.setActive(false);
        time.setUser(userRepository.findById(Integer.parseInt(user)));
        saveTime(time);

    }


}