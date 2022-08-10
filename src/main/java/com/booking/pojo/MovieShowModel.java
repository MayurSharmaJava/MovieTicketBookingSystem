package com.booking.pojo;

import com.booking.entity.Movie;
import com.booking.entity.Theater;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
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
