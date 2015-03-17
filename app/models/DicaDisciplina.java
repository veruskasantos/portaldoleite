package models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DicaDisciplina extends Dica{
	@Column
	private String nomeDisciplina;
	
	@Column
	private String razao;
	
	public DicaDisciplina(String nomeDisciplina, String razao) {
		this.nomeDisciplina = nomeDisciplina;
		this.razao = razao;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}
}
