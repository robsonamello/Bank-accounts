package challenge.castgroup.bank.accounts.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.dto.AccountDTO;
import challenge.castgroup.bank.accounts.dto.FinancialTransactionDTO;
import challenge.castgroup.bank.accounts.dto.PersonDTO;
import challenge.castgroup.bank.accounts.entity.Account;
import challenge.castgroup.bank.accounts.entity.Person;
import challenge.castgroup.bank.accounts.mapper.AccountDTOPropertyMap;
import challenge.castgroup.bank.accounts.mapper.PersonDTOPropertyMap;
import challenge.castgroup.bank.accounts.service.AccountService;
import challenge.castgroup.bank.accounts.service.PersonService;
import challenge.castgroup.bank.accounts.util.BankTransactionEnum;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;

/***
 * 
 * @author robson.a.mello
 *
 */
@RestController
public class BankAccountsApiController implements BankAccountsApi {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private AccountService accountService;

	@Override
	public ResponseEntity<PersonDTO> createPerson(PersonDTO body) {

		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PersonDTOPropertyMap());

		Person person = modelMapper.map(body, Person.class);

		personService.savePerson(person);

		PersonDTO personToReponse = modelMapper.map(person, PersonDTO.class);
		
		return new ResponseEntity<>(personToReponse, new HttpHeaders(), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<PersonDTO> updatePerson(PersonDTO body) {
		
		if(body.getId() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_PERSON_IDENTIFIER_NOT_EXIST);

		Person person = personService.filterById(new BigInteger(body.getId()));
		
		if(person == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_PERSON_NOT_EXIST);

		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PersonDTOPropertyMap());

		person = modelMapper.map(body, Person.class);

		personService.savePerson(person);
		
		PersonDTO personToReponse = modelMapper.map(person, PersonDTO.class);

		return new ResponseEntity<>(personToReponse, new HttpHeaders(), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<PersonDTO> getPerson(BigInteger personId) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PersonDTOPropertyMap());

		Person person = personService.filterById(personId);

		PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);

		return new ResponseEntity<>(personDTO, HttpStatus.OK);	
		
	}

	@Override
	public ResponseEntity<PersonDTO> deletePerson(BigInteger personId) {
		
		if(personId == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_PERSON_IDENTIFIER_NOT_EXIST);

		Person person = personService.filterById(personId);
		
		if(person == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_PERSON_NOT_EXIST);
		
		personService.deletePerson(person);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PersonDTOPropertyMap());
		PersonDTO personToReponse = modelMapper.map(person, PersonDTO.class);

		return new ResponseEntity<>(personToReponse, new HttpHeaders(), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<List<PersonDTO>> listPerson() {
		
		ModelMapper modelMapper = new ModelMapper();
		List<Person> listaPessoas = personService.listPerson();
    	List<PersonDTO> listaPessoasDTO = new ArrayList<PersonDTO>();
    	for (Person person : listaPessoas) {
    		PersonDTO  personDTO = new PersonDTO();
    		personDTO =  modelMapper.map(person, PersonDTO.class);
    		listaPessoasDTO.add(personDTO);
		}
    	return new ResponseEntity<>(listaPessoasDTO, HttpStatus.OK);
    	
	}

	@Override
	public ResponseEntity<AccountDTO> finantialTransaction(FinancialTransactionDTO body) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new AccountDTOPropertyMap());
		
		AccountDTO accountToReponse = null;
		
		if (body != null && body.getAccountId() != null && !StringUtils.isEmpty(body.getAccountId().trim()) 
				&& body.getType() != null && !StringUtils.isEmpty(body.getType().trim())	
				&& body.getValue() != null) {
			
			Account account = accountService.filterById(new BigInteger(body.getAccountId()));
			
			if (account == null)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_ACCOUNT_NOT_EXIST);
			
			BigDecimal newBalance = null; 
			
			if (body.getType().equals(BankTransactionEnum.CREDIT.getDescription())) {
				newBalance = account.getBalance().add(body.getValue());
			} else if (body.getType().equals(BankTransactionEnum.DEBIT.getDescription())) {
				
				if ( body.getValue().compareTo(account.getBalance()) > 0 ) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_NOT_ENOUGH_BALANCE);
				}
					
				newBalance = account.getBalance().subtract(body.getValue());
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_OPERATION_CANCELED);
			}
			
			account.setBalance(newBalance);		
					
			accountService.saveAccount(account);
			
			accountToReponse = modelMapper.map(account, AccountDTO.class);
			
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INFORMATION_PENDING);
		}
		return new ResponseEntity<>(accountToReponse, new HttpHeaders(), HttpStatus.OK);
	}

}