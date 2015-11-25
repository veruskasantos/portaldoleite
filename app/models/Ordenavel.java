package models;

import java.util.List;

public interface Ordenavel {

	public List<Dica> ordenaDicas(List<Dica> dicas);
	public String getNomeTipo();
	public Ordenavel getThis();
	
}
