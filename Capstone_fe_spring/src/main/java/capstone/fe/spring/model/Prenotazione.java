package capstone.fe.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prenotazione {

	private Long id;
	private String data, dataFine;
	private Integer numeroGiorni, prezzo;
	private Long metaId, userId, alloggioId, trasportoId, ritornoId;
	private Integer numeroPosti;

}
