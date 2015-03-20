package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import javax.persistence.Transient;

@Entity(name="Dica")
//@DiscriminatorColumn(name="REF_TYPE")
public abstract class Dica implements Comparable<Dica>{
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Tema tema;
	
	@Column
	private String user;
	
	@ElementCollection
    @MapKeyColumn(name="user")
    @Column(name="commentary")
    @CollectionTable(name="USERS_COMM", joinColumns=@JoinColumn(name="dica_id"))
	private Map<String, String> usersCommentaries;
	
	@ElementCollection
	private List<String> usuariosQueJaVotaram;
	
	@Column
	private int concordancias;
	
	@Column
	private int discordancias;
	
	@Column
	private int flag;
	
	@Transient
	private DicaDisciplina instanciaDisciplina;

	@Transient
	private DicaAssunto instanciaAssunto;
	
	@Transient
	private DicaMaterial instanciaMaterial;
	
	@Transient
	private DicaConselho instanciaConselho;
	
	
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
	
	public Entry<String,String> getDiscordanciasSet(){
		return (Entry<String, String>) usersCommentaries.entrySet();
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
	
	public void incrementaConcordancias(){
		this.concordancias++;
	}
	
	public void incrementaDiscordancias(){
		this.discordancias++;
	}

	public int getDiscordancias() {
		return discordancias;
	}

	public void setDiscordancias(int discordancias) {
		this.discordancias = discordancias;
	}
	
	public float getIndiceConcordancia() {
		int soma = concordancias + discordancias;
		if(soma == 0){
			return 0;
		}
		return this.getConcordancias()/(this.getConcordancias()+this.getDiscordancias());
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void addUsuarioQueVotou(String user){
		usuariosQueJaVotaram.add(user);
	}
	
	public boolean wasVotedByUser(String user){
		return usuariosQueJaVotaram.contains(user); 
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
	
	public void checaTipoDica() {
		switch (this.getTipo()) {
		case "DicaAssunto":
			this.instanciaAssunto = (DicaAssunto) this;			
			break;
		case "DicaMaterial":
			this.instanciaMaterial = (DicaMaterial) this;			
			break;
		case "DicaDisciplina":
			this.instanciaDisciplina = (DicaDisciplina) this;			
			break;
		case "DicaConselho":
			this.instanciaConselho = (DicaConselho) this;			
			break;
		}
	}
	
	public DicaDisciplina getInstanciaDisciplina() {
		return instanciaDisciplina;
	}

	public DicaAssunto getInstanciaAssunto() {
		return instanciaAssunto;
	}

	public DicaMaterial getInstanciaMaterial() {
		return instanciaMaterial;
	}

	public DicaConselho getInstanciaConselho() {
		return instanciaConselho;
	}

	public abstract String getTipo();
}
