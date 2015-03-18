package controllers;

import java.util.List;
import java.util.Map;

import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Tema;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

public class Application extends Controller {
	private static GenericDAOImpl dao = new GenericDAOImpl();
	
	private static Form<Dica> dicaForm = Form.form(Dica.class);
	
	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(views.html.index.render("Home Page"));
    }
	
	@Transactional
	public static Result tema() {
		//passar tema aqui para ser repassado para view
		return ok(views.html.tema.render());
	}
	
	@Transactional
	public static Result cadastrarDica() {
		Form<Dica> filledForm = dicaForm.bindFromRequest();
		
		Map<String,String> formMap = filledForm.data();
		
		String nomeTema = formMap.get("tema");
		
		Tema tema = (Tema) dao.findByAttributeName("Tema", "name", nomeTema).get(0);
		
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render("Home Page")); //mudar
		} else {
			String tipoKey = formMap.get("tipo");
			switch (tipoKey) {
				case "assunto":
					String assunto = formMap.get("assunto");
					DicaAssunto dicaAssunto = new DicaAssunto(assunto);
					
					tema.addDica(dicaAssunto);
					dicaAssunto.setTema(tema);								
					dao.persist(dicaAssunto);				
					break;
				case "conselho":
					String conselho = formMap.get("conselho");
					DicaConselho dicaConselho = new DicaConselho(conselho);
					
					tema.addDica(dicaConselho);
					dicaConselho.setTema(tema);								
					dao.persist(dicaConselho);				
					break;
				case "disciplina":
					String disciplina = formMap.get("disciplina");
					String razao = formMap.get("razao");
					
					DicaDisciplina dicaDisciplina = new DicaDisciplina(disciplina, razao);
					
					tema.addDica(dicaDisciplina);
					dicaDisciplina.setTema(tema);								
					dao.persist(dicaDisciplina);
					break;
				case "material":
					String url = formMap.get("url");
					DicaMaterial dicaMaterial = new DicaMaterial(url);
									
					tema.addDica(dicaMaterial);
					dicaMaterial.setTema(tema);								
					dao.persist(dicaMaterial);				
					break;
				default:
					break;
			}
			
			dao.merge(tema);
			
			dao.flush();			
			
			return redirect(routes.Application.index());
		}
	}
}
