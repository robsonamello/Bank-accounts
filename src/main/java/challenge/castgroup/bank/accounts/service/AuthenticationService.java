package challenge.castgroup.bank.accounts.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public interface AuthenticationService {

	Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response);

}
