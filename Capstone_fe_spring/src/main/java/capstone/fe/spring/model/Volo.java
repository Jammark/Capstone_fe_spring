package capstone.fe.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Volo extends Trasporto {

	private String compagnia;
	private Long partenzaId, arrivoId, stopId;
	private Aereoporto partenza, arrivo;
	private Integer posti;

}
