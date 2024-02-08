package com.anish.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "user_authorities")
@Data
public class UserAuthorities {

    @EmbeddedId
    private UserAuthoritiesPk id;
    private Date createdTime;
    private String createdBy;
    private Date updatedTime;
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserDetails userDetails;


}
