package com.booking.repository.pojo;

import com.booking.entity.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieShowModel extends RepresentationModel<MovieShowModel> {

    private long id;
    private String name;
    private Date date;
    private Date startTime;
    private Date endTime;
    private Movie movie;
    private TheaterModel theater;
}
