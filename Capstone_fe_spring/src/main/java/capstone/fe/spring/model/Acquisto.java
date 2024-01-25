package capstone.fe.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acquisto {

	private Long id;
	private String data;
	private Long userId;
	private Long prenotazioneId;
	private Double prezzo;
	private Prenotazione prenotazione;

}
