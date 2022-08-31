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
public class PreBookModel extends RepresentationModel<PreBookModel> {

    private Long bookingId;
    private String transactionId;
}
