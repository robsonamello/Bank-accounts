package challenge.castgroup.bank.accounts.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.dto.UserAuthDTO;
import challenge.castgroup.bank.accounts.service.AuthenticationService;
import challenge.castgroup.bank.accounts.service.UserService;
import challenge.castgroup.bank.accounts.token.TokenService;
import challenge.castgroup.bank.accounts.util.Constants;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;
import io.jsonwebtoken.Claims;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserService userService;

	private AuthenticationServiceImpl() {}

	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		String authorizationToken = request.getHeader(Constants.TOKEN_HEADER_PARAM_NAME);

		if (authorizationToken != null) {

			try {

				Claims claims = TokenService.getJwtsClaimstByToken(authorizationToken); 

				UserAuthDTO responseToValidate = new UserAuthDTO();

				responseToValidate.setLogin(claims.getSubject());
				responseToValidate.setRole(Constants.ROLE_PREFIX + claims.get("role").toString());
				
				if (responseToValidate != null && userService.findByLogin(responseToValidate.getLogin()) != null ) {
					
					List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			        
					authorities.add(new SimpleGrantedAuthority(responseToValidate.getRole()));
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
							new UsernamePasswordAuthenticationToken(responseToValidate.getLogin(), null, authorities);
							
					return usernamePasswordAuthenticationToken;
				} else {
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_INVALID_CREDENTIALS);
				}

			} catch (HttpClientErrorException e) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_UNAUTHORIZED);
			}

		}
		return null;
	}

}