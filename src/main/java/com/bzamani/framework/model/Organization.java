package com.bzamani.framework.model;

import com.bzamani.framework.mycustomannotation.MyLengthValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "core_organization")
@Setter
@Getter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Organization parent;

    @MyLengthValidator(minLenght = 0, maxLength = 50, message = "Error. length of field 'title' is not valid ! ")
    @Column(name = "title")
    private String title;

    @MyLengthValidator(minLenght = 0, maxLength = 50, message = "Error. length of field 'description' is not valid ! ")
    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

    public Organization() {

    }

    public Organization(String title, String description, boolean active) {
        this.title = title;
        this.description = description;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Organization [id=" + id + ", title=" + title + ", desc=" + description + ", active=" + active + "]";
    }

}
