package com.example.UberReviewService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)  //to track the date and time related to object lifecycle events i.e creation and deletion
@MappedSuperclass //specifies inheritence type that is we dont need a seperate table for parent class
@Getter
@Setter
public abstract class BaseModel {

    @Id  //this annotation makes the id property a primary key of our table
    @GeneratedValue(strategy = GenerationType.IDENTITY) //identity means auto increment of Id column
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)//this annotation tells spring about the formats of Date object to be stored i.e Date/time ? timestamp
    @CreatedDate //this annotation tells spring that only handle it for object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate //this annotation tells spring that only handle it for object update
    protected Date updatedAt;
}
