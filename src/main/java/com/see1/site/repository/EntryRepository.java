package com.see1.site.repository;

import com.see1.site.model.*;
import com.see1.site.model.Entry;
import com.see1.site.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    List<Entry> findByUser(User user);
}
