package com.see1.site.repository;

import com.see1.site.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRolesRole(String role);
    User findByUserName(String userName);
    User findById(int id);
}