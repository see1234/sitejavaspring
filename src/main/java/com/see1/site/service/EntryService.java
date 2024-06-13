package com.see1.site.service;

import com.see1.site.model.Entry;
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
public class EntryService {

    @Autowired
    EntryRepository entryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TimeRepository timeRepository;
    public List<Entry> getAllService() {
        return StreamSupport.stream(entryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }



    public void saveService(Entry service) {
        entryRepository.save(service);
    }

    public void deleteEntry(int id) {

        if(entryRepository.existsById(id)) {

           entryRepository.deleteById(id);
        }
    }
    public Entry getEntry(int id) {
        if(entryRepository.existsById(id)) {

           return entryRepository.getOne(id);
        }
        return null;
    }
    public void setEntryTime(int id) {

        Entry entry = entryRepository.getOne(id);
        List<Time> times = timeRepository.findByTimeName(entry.getTime());
        for(Time time : times) {

            if(time.getDate().equalsIgnoreCase(entry.getDate())) {
                if(time.getActive()) {

                    time.setActive(false);
                    timeRepository.save(time);
                    break;
                }
                else {

                    time.setActive(true);
                    timeRepository.save(time);

                    break;
                }
            }
        }


    }
    public void addEntry(String first_name, String last_name,String telephone, String service_name, String time, String date, String user) {
        Entry entry = new Entry();
        entry.setServiceName(service_name);
        entry.setFirstName(first_name);
        entry.setLastName(last_name);
        entry.setTelephone(telephone);
        entry.setDate(date);
        entry.setTime(time);
        entry.setUser(userRepository.findById(Integer.parseInt(user)));
         saveService(entry);

    }


}