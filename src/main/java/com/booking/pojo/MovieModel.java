package com.booking.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieModel extends RepresentationModel<MovieModel> {
	
	private long id;
	private String name;
	private String imdbNumber;

}
