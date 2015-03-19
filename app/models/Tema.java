package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import play.Logger;

@Entity(name="Tema")
public class Tema {
	@Id
	@GeneratedValue
	private long id;
		
	@Column
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Disciplina disciplina;
	
	@OneToMany(cascade=CascadeType.ALL)
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
		this.dicas = new ArrayList<Dica>();
		this.usersDifficulty = new HashMap<String, String>();
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
		Collections.sort(dicas);
		return dicas;
	}
	
	public void addDica(Dica dica) {
		this.dicas.add(dica);
	}
	
	public Map<String, String> getUsersDifficulty() {
		return usersDifficulty;
	}

	public double getMediana() {
		List<String> difficultyVotes = new ArrayList<String>(this.usersDifficulty.values());
		
		Collections.sort(difficultyVotes);
		int totalVotes = difficultyVotes.size();
		if (totalVotes == 0) {
			return 0;
		}
		else if (totalVotes % 2 == 1) {
			String mediana = difficultyVotes.get(totalVotes/2);
			
			return Integer.parseInt(mediana);
		}
		else {
			String primeiraMediana = difficultyVotes.get(totalVotes/2);
			String segundaMediana = difficultyVotes.get((totalVotes/2)-1);
			
			return (Integer.parseInt(primeiraMediana) + Integer.parseInt(segundaMediana))/2.0;
		}
	}
	
	public double getMedia() {
		List<String> difficultyVotes = new ArrayList<String>(this.usersDifficulty.values());
		
		int totalVotes = difficultyVotes.size();
		if(totalVotes == 0){
			return 0;
		}
		
		double votesCount = 0;
		
		for (String vote : difficultyVotes) {
			votesCount = votesCount + Double.parseDouble(vote);
		}
		
		return votesCount/totalVotes;
	}
}
