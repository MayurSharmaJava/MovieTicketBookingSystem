package com.booking.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentModel extends RepresentationModel<PaymentModel> {

    private long id;
    private String transactionNo;
    private String type;
    private BigDecimal amount;
    private String status;

}
