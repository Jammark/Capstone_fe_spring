package capstone.fe.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alloggio {

	private Long id;
	private String nome, descrizione, urlImmagine;
	private Long metaId;
	private Double prezzo;
	private Integer rate;

}
