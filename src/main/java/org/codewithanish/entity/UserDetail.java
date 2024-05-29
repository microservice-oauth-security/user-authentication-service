package org.codewithanish.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.codewithanish.constant.Provider;

import java.util.Date;

@Entity
@Data
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String userId;
    private String userName;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String password;
    private Date createdTime;
    private String createdBy;
    private Date updatedTime;
    private String updatedBy;

}
