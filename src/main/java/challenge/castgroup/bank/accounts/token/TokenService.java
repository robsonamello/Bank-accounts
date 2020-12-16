package challenge.castgroup.bank.accounts.token;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.util.Constants;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/***
 * 
 * @author robson.a.mello
 *
 */
public class TokenService {

	private TokenService() {
	}

	public static HttpHeaders setBearerAuthorization(HttpHeaders httpHeaders, String username, String role) {
		
		Date expirationDate = new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME);
		
		Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        String tokenJWT = Jwts.builder()
				.setClaims(claims)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET_KEY)
				.compact();

		httpHeaders.add("Access-Control-Expose-Headers", "Authorization");
		httpHeaders.add("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
		httpHeaders.setBearerAuth(tokenJWT);
		httpHeaders.setExpires(expirationDate.getTime());
		
		return httpHeaders;

	}

	public static Claims getJwtsClaimstByToken(String authorization) {

		if (StringUtils.isEmpty(authorization) || !StringUtils.startsWithIgnoreCase(authorization, Constants.TOKEN_PREFIX)) {
			return null;
		}

		try {
			return Jwts.parser()
					.setSigningKey(Constants.TOKEN_SECRET_KEY)
					.parseClaimsJws(authorization.replace(Constants.TOKEN_PREFIX, ""))
					.getBody();
		} catch (ExpiredJwtException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_EXPIRED_TOKEN);
		} catch (JwtException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_INVALID_TOKEN);
		}

	}

}