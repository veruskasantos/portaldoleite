package models;

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
public class Dica {
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
	
	public Dica(){}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
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
}
