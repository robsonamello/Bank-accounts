package challenge.castgroup.bank.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import challenge.castgroup.bank.accounts.dto.UserDTO;

/***
*
* Interface com contratos estabelecidos para consumo de serviços REST
*
* @author robson.a.mello
*
*/

public interface UsersApi {

	/**
	* Serviço de criação de usuário
	*
	* @param body informações cadastrais
	* @return usuário cadastrado
	*/
	@PostMapping(value = "/users", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<UserDTO> createUser(@RequestBody UserDTO body);

}