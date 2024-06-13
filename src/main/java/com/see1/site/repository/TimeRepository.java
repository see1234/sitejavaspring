package com.see1.site.repository;

import com.see1.site.model.Time;
import com.see1.site.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {
    List<Time> findByUser(User user);
    List<Time> findByTimeName(String time);
}
