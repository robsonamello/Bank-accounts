package challenge.castgroup.bank.accounts.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import challenge.castgroup.bank.accounts.dto.AccountDTO;
import challenge.castgroup.bank.accounts.dto.FinancialTransactionDTO;
import challenge.castgroup.bank.accounts.dto.PersonDTO;

/***
*
* Interface com contratos estabelecidos para consumo de serviços REST
*
* @author robson.a.mello
*
*/
public interface BankAccountsApi {
	
	/**
	* Serviço de criação de pessoas e contas
	*
	* @param body informações cadastrais
	* @return pessoa cadastrada
	*/
	@PostMapping(value = "/person", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO body);

	/**
	* Serviço de atualização de pessoas e contas
	*
	* @param body informações cadastrais
	* @return pessoa atualizada
	*/
	@PutMapping(value = "/person", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO body);
	
	/**
	* Serviço para listar todos as pessoas cadastradas
	* @return lista das pessoas e contas
	*/
	@GetMapping(value = "/person", 
			produces = { "application/json" })
	ResponseEntity<List<PersonDTO>> listPerson();
	
	/**
	* Serviço para listar todos as pessoas cadastradas
	* @return Informações da pessoa, especificada pelo seu identificador
	*/
	@GetMapping(value = "/person/{personId}", 
			produces = { "application/json" })
	ResponseEntity<PersonDTO> getPerson(@PathVariable(value ="personId", required = true) BigInteger personId);
	
	
	/**
	* Serviço para excluir uma pessoa cadastrada
	* @return Pessoa excluída
	*/
	@DeleteMapping(value = "/person/{personId}", 
			produces = { "application/json" })
	ResponseEntity<PersonDTO> deletePerson(@PathVariable(value ="personId") BigInteger personId);
	
	/**
	* Serviço para movimentação bancária de saque ou depósito
	* @return Informações da movimentação
	*/
	@PostMapping(value = "/finantialtransaction", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<AccountDTO> finantialTransaction(@RequestBody FinancialTransactionDTO body);

}