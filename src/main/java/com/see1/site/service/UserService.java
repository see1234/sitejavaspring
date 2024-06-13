package com.see1.site.service;

import com.see1.site.model.Entry;
import com.see1.site.model.Role;
import com.see1.site.model.Time;
import com.see1.site.model.User;

import com.see1.site.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ServiceRepository serviceRepository;
    private TimeRepository timeRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EntryRepository entryRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository, ServiceRepository serviceRepository,
                       TimeRepository timeRepository, EntryRepository entryRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.timeRepository = timeRepository;
        this.serviceRepository = serviceRepository;
        this.entryRepository = entryRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> findUsersByRoleId(String role) {
        return userRepository.findByRolesRole(role);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    public List<com.see1.site.model.Service> getServicesForUser(User user) {
        return serviceRepository.findByUser(user);
    }
    public List<Entry> getEntryForUser(User user) {
        return entryRepository.findByUser(user);
    }
    public List<Time> getTimesForUser(User user) {
        return timeRepository.findByUser(user);
    }
}