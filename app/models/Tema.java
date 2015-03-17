package models;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity(name="Tema")
public class Tema {
	@Id
	@GeneratedValue
	private long id;
		
	@Column
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="DICAS")
	private List<Dica> dicas;
	
	@ElementCollection
    @MapKeyColumn(name="user")
    @Column(name="dificuldade")
    @CollectionTable(name="USERS_DIFF", joinColumns=@JoinColumn(name="tema_id"))
	private Map<String, String> usersDifficulty;
	
	@Column(name = "TOTAL_DIFF")
	private int dificuldade;
	
	public Tema(){}
	
	public Tema(String name) {
		this.name = name;
	}
	
	public void incrementarDificuldade(String userLogin, int dificuldade) {
		if (usersDifficulty.containsKey(userLogin)) {
			String previousDiff = usersDifficulty.get(userLogin);
			
			this.dificuldade -= Integer.valueOf(previousDiff);
		}
		
		this.dificuldade += dificuldade;
		
		usersDifficulty.put(userLogin, String.valueOf(dificuldade));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public long getId() {
		return id;
	}

	public List<Dica> getDicas() {
		return dicas;
	}
	
	public void addDica(Dica dica) {
		this.dicas.add(dica);
	}
}
