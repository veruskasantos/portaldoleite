package models;

import java.util.HashMap;
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

@Entity(name="Dica")
//@DiscriminatorColumn(name="REF_TYPE")
public abstract class Dica implements Comparable<Dica>{
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Tema tema;
	
	@ElementCollection
    @MapKeyColumn(name="user")
    @Column(name="commentary")
    @CollectionTable(name="USERS_COMM", joinColumns=@JoinColumn(name="dica_id"))
	private Map<String, String> usersCommentaries;
	
	@Column
	private int concordancias;
	
	@Column
	private int discordancias;
	
	@Column
	private int flag;
	
	public Dica(){}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
		this.usersCommentaries = new HashMap<String,String>();
	}

	public long getId() {
		return id;
	}

	public Map<String, String> getUsersCommentaries() {
		return usersCommentaries;
	}
	
	public void addUserCommentary(String login, String commentary) {
		usersCommentaries.put(login, commentary);
	}
	
	public abstract String getTexto();
	
	public int getConcordancias() {
		return concordancias;
	}

	public void setConcordancias(int concordancias) {
		this.concordancias = concordancias;
	}

	public int getDiscordancias() {
		return discordancias;
	}

	public void setDiscordancias(int discordancias) {
		this.discordancias = discordancias;
	}
	
	public float getIndiceConcordancia() {
		return this.getConcordancias()/(this.getConcordancias()+this.getDiscordancias());
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public int compareTo(Dica otherDica) {
		if (this.getConcordancias()>otherDica.getConcordancias()) {
			return 1;
		} else if (this.getConcordancias()<otherDica.getConcordancias()) {
			return -1;
		} else {
			return 0;
		}
	}
}
