package controllers;

import java.util.List;
import java.util.Map;

import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.dao.GenericDAOImpl;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class Application extends Controller {
	private static GenericDAOImpl dao = new GenericDAOImpl();
	
	@Transactional
	@Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(views.html.index.render("Home Page"));
    }
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result tema(long id) {
		//TODO Passar usuario logado (como pegar?)
		List<Disciplina> listaDisciplina = dao.findAllByClassName(Disciplina.class.getName());
		Tema tema = dao.findByEntityId(Tema.class, id);
		if(tema == null){
			return badRequest(views.html.index.render("Tema não existe"));
		}
		return ok(views.html.tema.render(listaDisciplina, tema));
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result cadastrarDica(long idTema) {
		
		DynamicForm filledForm = Form.form().bindFromRequest();
		
		Map<String,String> formMap = filledForm.data();
		
		//long idTema = Long.parseLong(formMap.get("idTema"));
		
		Tema tema = dao.findByEntityId(Tema.class, idTema);
		String userName = session("username");
		
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
					dicaAssunto.setUser(userName); //TODO pegar nome do usuario logado
					dao.persist(dicaAssunto);				
					break;
				case "conselho":
					String conselho = formMap.get("conselho");
					DicaConselho dicaConselho = new DicaConselho(conselho);
					
					tema.addDica(dicaConselho);
					dicaConselho.setTema(tema);
					dicaConselho.setUser(userName); //TODO pegar nome do usuario logado
					dao.persist(dicaConselho);				
					break;
				case "disciplina":
					String disciplinas = formMap.get("disciplinas");
					String razao = formMap.get("razao");
					
					DicaDisciplina dicaDisciplina = new DicaDisciplina(disciplinas, razao);
					
					tema.addDica(dicaDisciplina);
					dicaDisciplina.setTema(tema);
					dicaDisciplina.setUser(userName); //TODO pegar nome do usuario logado
					dao.persist(dicaDisciplina);
					break;
				case "material":
					String url = formMap.get("url");
					DicaMaterial dicaMaterial = new DicaMaterial(url);
									
					tema.addDica(dicaMaterial);
					dicaMaterial.setTema(tema);
					dicaMaterial.setUser(userName); //TODO pegar nome do usuario logado
					dao.persist(dicaMaterial);				
					break;
				default:
					break;
			}
			
			dao.merge(tema);
			
			dao.flush();			
			
			return redirect(routes.Application.tema(idTema));
		}
	}
	
	@Transactional
	public static Result avaliarDificuldadeTema(long idTema) {
		DynamicForm filledForm = Form.form().bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render("Home Page")); //mudar
		} else {
			Map<String, String> formMap = filledForm.data();
			int dificuldade = Integer.parseInt(formMap.get("dificuldade"));	
			String userLogin = session("login");
			Tema tema = dao.findByEntityId(Tema.class, idTema);
			
			//Tema tema = dao.findByEntityId(Tema.class, id)(Tema) dao.findByAttributeName("Tema", "name", nomeTema).get(0);
			tema.incrementarDificuldade(userLogin, dificuldade);
			dao.merge(tema);
			dao.flush();
			return tema(idTema);
		}
	}
	
	@Transactional
	public static Result addDiscordanciaEmDica(long idDica) {
		DynamicForm filledForm = Form.form().bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render("Home Page")); //mudar
		} else {
			Map<String, String> formMap = filledForm.data();
			String username = session("username");
			String login = session("login");
			String discordancia = formMap.get("discordancia");
			Logger.info("Discordancia adicionada: " + discordancia);
			Dica dica = dao.findByEntityId(Dica.class, idDica);
			
			dica.addUsuarioQueVotou(login);
			dica.addUserCommentary(username, discordancia);
			dica.incrementaDiscordancias();
			dao.merge(dica);
			dao.flush();
			
			return tema(dica.getTema().getId());
		}
	}
	
	@Transactional
	public static Result upVoteDica(long idDica) {
		Dica dica = dao.findByEntityId(Dica.class, idDica);
		String login = session("login");
		dica.addUsuarioQueVotou(login);
		dica.incrementaConcordancias();
		dao.merge(dica);
		dao.flush();
		
		return tema(dica.getTema().getId());
	}
}
