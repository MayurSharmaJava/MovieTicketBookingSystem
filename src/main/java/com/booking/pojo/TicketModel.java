package com.booking.pojo;

import com.booking.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketModel extends RepresentationModel<TicketModel> {

    private long id;
    private User user;
    private Date bookingDate;
    private String movieName;
    private Date showDate;
    private Date startTime;
    private Date endTime;
    private List<Seat> seats;
    private String status;
    private double amount;
    private String theaterName;
    private String screen;
    private String qr_code_link;
    private Payment payment;

}
