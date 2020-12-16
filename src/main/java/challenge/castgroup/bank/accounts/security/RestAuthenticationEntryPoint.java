package challenge.castgroup.bank.accounts.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import challenge.castgroup.bank.accounts.exception.CustomErrorResponse;

/***
 * 
 * @author robson.a.mello
 *
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		CustomErrorResponse error = new CustomErrorResponse();
		error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(authException.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON.toString());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(new Gson().toJson(error));
	}
}