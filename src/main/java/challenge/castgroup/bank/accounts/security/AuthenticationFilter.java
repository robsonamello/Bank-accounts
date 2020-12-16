package challenge.castgroup.bank.accounts.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import challenge.castgroup.bank.accounts.exception.CustomErrorResponse;
import challenge.castgroup.bank.accounts.service.AuthenticationService;

/***
 * 
 * @author robson.a.mello
 *
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (ResponseStatusException e) {
			processResponseStatusException(e, response);
		} catch (RuntimeException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}

	private void processResponseStatusException(ResponseStatusException e, HttpServletResponse response) throws IOException {

		CustomErrorResponse error = new CustomErrorResponse();
		error.setStatusCode(e.getStatus().value());
		error.setMessage(e.getReason());

		response.setContentType(MediaType.APPLICATION_JSON.toString());
		response.setStatus(e.getStatus().value());
		response.getWriter().write(new Gson().toJson(error));
	}

}
