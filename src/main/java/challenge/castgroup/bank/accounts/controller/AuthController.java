package challenge.castgroup.bank.accounts.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import challenge.castgroup.bank.accounts.dto.UserAuthDTO;
import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.service.UserService;
import challenge.castgroup.bank.accounts.token.TokenService;

/***
 * 
 * @author robson.a.mello
 *
 */
@RestController
public class AuthController implements AuthApi {

	@Autowired
	private UserService userService;

	public ResponseEntity<UserAuthDTO> signIn(UserAuthDTO body) {

		ModelMapper modelMapper = new ModelMapper();

		User user = modelMapper.map(body, User.class);

		user = this.userService.checkAvailability(user);
		
		HttpHeaders responseHeaders = TokenService.setBearerAuthorization(new HttpHeaders(), user.getLogin(), user.getRole());

		return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
	}
	
}