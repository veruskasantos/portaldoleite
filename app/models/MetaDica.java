package models;

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

@Entity
public class MetaDica implements Comparable<MetaDica>{
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String user;
	
	@Column
	private String comment;
	
	@OneToMany
	List<Dica> dicasAdicionadas;
	
	@OneToMany
	List<MetaDica> metaDicasAdicionadas;
	
	@ElementCollection
	private List<String> usuariosQueJaVotaram;
	
	@ElementCollection
	private List<String> usuarioqueQueJaDenunciaram;
	
	@ElementCollection
    @MapKeyColumn(name="user")
    @Column(name="commentary")
    @CollectionTable(name="META_COMM", joinColumns=@JoinColumn(name="dica_id"))
	private Map<String, String> usersCommentaries;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Disciplina disciplina;
	
	@Column
	private int concordancias;
	
	@Column
	private int discordancias;
	
	@Column
	private int flag;
	
	public MetaDica(){}
	
	public MetaDica(Disciplina disciplina, String user, String comment) {
		this.disciplina = disciplina;
		this.user = user;
		this.comment = comment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public Disciplina getDisciplina() {
		return this.disciplina;
	}
	
	public void addDica(Dica dica) {
		this.dicasAdicionadas.add(dica);
	}
	
	public void addMetaDica(MetaDica metaDica) {
		this.metaDicasAdicionadas.add(metaDica);
	}

	@Override
	public int compareTo(MetaDica otherMetaDica) {
		if (this.getConcordancias()>otherMetaDica.getConcordancias()) {
			return -1;
		} else if (this.getConcordancias()<otherMetaDica.getConcordancias()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void addUsuarioQueVotou(String user){
		usuariosQueJaVotaram.add(user);
	}
	
	public boolean wasVotedByUser(String user){
		return usuariosQueJaVotaram.contains(user); 
	}
	
	public void addUserCommentary(String login, String commentary) {
		usersCommentaries.put(login, commentary);
	}
	
	public void addUsuarioFlag(String user) {
		this.usuarioqueQueJaDenunciaram.add(user);
	}
	
	public boolean wasFlaggedByUser(String user) {
		return usuarioqueQueJaDenunciaram.contains(user);
	}
	
	public void incrementaFlag() {
		this.flag = flag + 1;
	}
}
