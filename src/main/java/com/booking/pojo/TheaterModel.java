package com.booking.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "theater")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterModel extends RepresentationModel<TheaterModel> {

    private long id;
    private String name;
    private String pinCode;
}
