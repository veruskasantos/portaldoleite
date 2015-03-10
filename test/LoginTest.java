import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Http;
import play.mvc.Result;
import base.AbstractTest;


public class LoginTest extends AbstractTest{

	Result result;
	
	@Test
	public void callLogin() {
		result = callAction(controllers.routes.ref.Login.show(),
				fakeRequest());
		assertThat(status(result)).isEqualTo(Http.Status.OK);
		
	} 
	
}
