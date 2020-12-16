package challenge.castgroup.bank.accounts.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import challenge.castgroup.bank.accounts.dto.UserDTO;
import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.mapper.UserDTOPropertyMap;
import challenge.castgroup.bank.accounts.service.UserService;

/***
 * 
 * @author robson.a.mello
 *
 */
@RestController
public class UsersApiController implements UsersApi {

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<UserDTO> createUser(UserDTO body) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new UserDTOPropertyMap());

		User user = modelMapper.map(body, User.class);

		userService.saveUser(user);

		UserDTO userToReponse = modelMapper.map(user, UserDTO.class);

		return new ResponseEntity<>(userToReponse, new HttpHeaders(), HttpStatus.CREATED);
	}

}