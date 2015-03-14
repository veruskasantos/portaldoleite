import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import base.AbstractTest;


public class UsuarioTest extends AbstractTest{

	GenericDAO dao = new GenericDAOImpl();
	
	@Test
	public void deveSalvarUsuarioNoBD() {
		User u = new User("Admin","admin@gmail.com","1234");
		dao.persist(u);
		
		assertThat(dao.findAllByClassName("Usuario").size()).isEqualTo(1);
	}
	
}
