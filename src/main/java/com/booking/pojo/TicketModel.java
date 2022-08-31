package com.booking.pojo;

import com.booking.entity.Payment;
import com.booking.entity.Seat;
import com.booking.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
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
    private BigDecimal amount;
    private String theaterName;
    private String screen;
    private String qr_code_link;
    private Payment payment;

}
