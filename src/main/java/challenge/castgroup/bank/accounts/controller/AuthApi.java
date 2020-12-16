package challenge.castgroup.bank.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import challenge.castgroup.bank.accounts.dto.UserAuthDTO;

/***
*
* Interface com contratos estabelecidos para consumo de serviços REST
*
* @author robson.a.mello
*
*/
public interface AuthApi {

	/**
	* Serviço de login e token
	*
	* @param body informações de autenticação
	* @return token da aplicação
	*/
	@CrossOrigin
	@PostMapping(value = "/signin", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<UserAuthDTO> signIn(@RequestBody UserAuthDTO body);

}