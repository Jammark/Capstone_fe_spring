package capstone.fe.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destinazione extends Meta {

	private String cotenutoSecondario, contenutoPrincipale;
	private Long[] cityIds;

}
