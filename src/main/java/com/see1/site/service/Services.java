package com.see1.site.service;

import com.see1.site.model.User;
import com.see1.site.repository.ServiceRepository;
import com.see1.site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Services {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    UserRepository userRepository;
    public List<com.see1.site.model.Service> getAllService() {
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }



    public void saveService(com.see1.site.model.Service service) {
        serviceRepository.save(service);
    }

    public void deleteService(String service) {
        com.see1.site.model.Service service_ = findByServiceName(service);
        if (service_ != null)
            serviceRepository.delete(service_);
    }
    public List<com.see1.site.model.Service> findServiceByUser(User user) {
        return serviceRepository.findByUser(user);
    }
    public com.see1.site.model.Service findByServiceName(String service) {
        return serviceRepository.findByServiceName(service);
    }

    public com.see1.site.model.Service addServices(String name, String user) {
        com.see1.site.model.Service service = new com.see1.site.model.Service();
        service.setServiceName(name);

        service.setUser(userRepository.findById(Integer.parseInt(user)));
        saveService(service);
        return service;
    }


}