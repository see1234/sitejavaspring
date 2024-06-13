package com.see1.site.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "times")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "time_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "time_name")
    private String timeName;
    @Column(name = "date")
    private String date;
    public String getTimeName() {
        return timeName;
    }
    public String getDate() {
        return date;
    }

    public int getId() {
        return user.getId();
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

}