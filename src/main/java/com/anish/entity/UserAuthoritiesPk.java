package com.anish.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserAuthoritiesPk implements Serializable {

    @Column(name="USER_ID")
    private Long userId;
    @Column(name="AUTHORITY_ID")
    private Long authorityId;

}
