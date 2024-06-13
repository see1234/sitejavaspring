package com.see1.site.repository;

import com.see1.site.model.Service;
import com.see1.site.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    Service findByServiceName(String service_name);
    List<Service> findByUser(User user);
}
