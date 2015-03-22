package unidade;

import static org.junit.Assert.fail;
import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;

import org.junit.Before;
import org.junit.Test;

public class DicaTest {
	Disciplina disciplina;
	Tema tema;
	Dica dica1, dica2, dica3, dica4;
	User user1, user2;
	
	@Before
	public void before() {
		disciplina = new Disciplina("Álgebra Linear");
		tema = new Tema("Transformações lineares");
		
		dica1 = new DicaAssunto("LaPlace");
		dica2 = new DicaConselho("Não subestime a última prova");
		dica3 = new DicaMaterial("https://www.youtube.com/watch?v=NyAp-3QXdC0");
		dica4 = new DicaDisciplina("Álgebra Vetorial", "Pré-requisito importante");
		
		user1 = new User("sir.leo@hotmail.com", "123", "leulz");
		user2 = new User("schopenh@uer.com", "lifesucks", "schops");
	}

	@Test
	public void devePoderReceberVotos() {
		tema.addDica(dica1);
		disciplina.addTema(tema);
		
		dica1.incrementaDiscordancias();
	}

}
