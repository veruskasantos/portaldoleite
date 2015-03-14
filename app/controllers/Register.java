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
import views.html.register;

public class Register extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	static Form<User> registerForm = form(User.class).bindFromRequest();

	@Transactional
    public static Result show() {
        return ok(register.render(registerForm));
    }
    
	@Transactional
	public static Result register() {
		
		User u = registerForm.bindFromRequest().get();    	
		
		if (registerForm.hasErrors()) {
			flash("fail", "Campos inválidos");
            return badRequest(register.render(registerForm));
        } else if(checkDBForUser(u.getEmail())) {
        	flash("fail", "E-mail em uso");
        	return badRequest(register.render(registerForm));
        } else if(checkDBForName(u.getLogin())) {
        	flash("fail", "Login em uso");
        	return badRequest(register.render(registerForm));
        }
		else {
        	dao.persist(u);
            return redirect(
                routes.Login.show()
            );
        }
    }

	private static boolean checkDBForName(String login) {
		List<User> userInDB = dao.findByAttributeName("User", "login", login);
		
		if (userInDB == null || userInDB.size() == 0) {
			return false;
		}
		return true;
	}

	private static boolean checkDBForUser(String email) {
		List<User> userInDB = dao.findByAttributeName("User", "email", email);
		
		if (userInDB == null || userInDB.size()==0) {
			return false;
		}
		return true;
	}
}
