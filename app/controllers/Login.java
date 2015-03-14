package controllers;

import static play.data.Form.form;

import java.util.List;

import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class Login extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<User> loginForm = form(User.class).bindFromRequest();	

	@Transactional
    public static Result show() {
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
        return ok(login.render(loginForm));
    }
	
	@Transactional
	public static Result logout() {
		session().clear();
		return show();
	}
    
	@Transactional
	public static Result authenticate() {		
		User u = loginForm.bindFromRequest().get();
		
		List<User> usuarioNoBD = dao.findByAttributeName("User", "email", u.getEmail());
        String pass = loginForm.bindFromRequest().data().get("pass");
		
        if (loginForm.hasErrors() || !validate(usuarioNoBD, pass)) {
        	flash("fail", "Email ou Senha Inválidos");
        	return unauthorized(login.render(loginForm));
        } else {
        	User user = usuarioNoBD.get(0);
            session().clear();
            session("user", user.getNome());
            
            return redirect(
                routes.Application.index()
            );
        }
    }
	
	private static boolean validate(List<User> listaUsuario, String pass) {
		try {
			User usuarioLogado = listaUsuario.get(0);
			if (listaUsuario.size()>1){
				Logger.debug("Mais de um usuário com o mesmo e-mail");
			}
			if (usuarioLogado.checkPass(pass)) {
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			Logger.debug("Usuário não cadastrado");			
			return false;
		}
	}
}