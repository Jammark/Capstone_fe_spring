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
public class Trasporto {

	private Long id;
	private String nome, descrizione, durata;

	private String dataPartenza, dataArrivo;
	private Integer postiDisponibili, postiOccupati;
}
