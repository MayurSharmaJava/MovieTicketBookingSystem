package com.booking.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "MovieShow")
public class MovieShow{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "movieshow_sequence")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "Theater_id")
    private Theater theater;

    @OneToOne
    private Screen screen;//-- TODO: re-evaluate

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
        @JoinColumn(name = "movie_show_id")
    private List<Seat> seats;
    //TODO: Convert to Set of Seat , Seat can not be duplicate

}
