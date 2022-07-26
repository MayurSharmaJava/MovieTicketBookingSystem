package com.sharma.mayur.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Theater")
public class Theater{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "theater_sequence")
    private long id;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Address address;

    @Column(name = "name")
    private String name;

    @Column(name = "pinCode")
    private String pinCode;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    private List<Screen> screen;

}
