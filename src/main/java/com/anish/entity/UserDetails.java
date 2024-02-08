package com.anish.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "user_details")
@Data
public class UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean credentialsExpired;
    private Boolean enabled;
    private Date createdTime;
    private String createdBy;
    private Date updatedTime;
    private String updatedBy;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "authority", cascade = CascadeType.ALL)
    private List<UserAuthorities> userAuthorities;

}
