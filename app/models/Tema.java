package models;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity(name="Tema")
public class Tema {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="DICAS")
	private List<Dica> dicas;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@MapKey
	@Column(name = "USERS_DIFF")
	private Map<String, String> usersDifficulty;
	
	@Column(name = "TOTAL_DIFF")
	private int dificuldade;
	
	public Tema(){}
	
	public void incrementarDificuldade(String userEmail, int dificuldade) {
		if (usersDifficulty.containsKey(userEmail)) {
			String previousDiff = usersDifficulty.get(usersDifficulty);
			
			this.dificuldade -= Integer.valueOf(previousDiff);
		}
		
		this.dificuldade += dificuldade;
		
		usersDifficulty.put(userEmail, String.valueOf(dificuldade));
	}
}
