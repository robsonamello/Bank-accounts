package challenge.castgroup.bank.accounts.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.entity.Account;
import challenge.castgroup.bank.accounts.entity.Person;
import challenge.castgroup.bank.accounts.repository.PersonRepository;
import challenge.castgroup.bank.accounts.service.PersonService;
import challenge.castgroup.bank.accounts.util.AccountTypeEnum;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	private PersonServiceImpl() {}
	
	@Override
	public Person savePerson(Person person) {
		validateMandatoryFields(person);
		validateConflictCpf(person);
		validateAccountType(person);
		return personRepository.save(person);
	}

	@Override
	public Person filterById(BigInteger personId) {
		Optional<Person> person = personRepository.findById(personId);
		
		if (!person.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_PERSON_NOT_EXIST);
	    }
		return person.get(); 
	}

	private void validateMandatoryFields(Person person) {

		Boolean existingEmptyFields = false;

		if (person.getName() == null || StringUtils.isEmpty(person.getName().trim()))
			existingEmptyFields = true;
		
		if (person.getPhoneNumber() == null || StringUtils.isEmpty(person.getPhoneNumber().trim()))
			existingEmptyFields = true;
		
		if (person.getBornDate() == null)
			existingEmptyFields = true;
		
		if (person.getCpf() == null || StringUtils.isEmpty(person.getCpf().trim()))
			existingEmptyFields = true;

		if (person.getAccounts() != null && !person.getAccounts().isEmpty()) {
			for (Account conta : person.getAccounts()) {
				if (conta.getType() == null && StringUtils.isEmpty(conta.getType().trim()))
					existingEmptyFields = true;
				if (conta.getBalance() == null)
					existingEmptyFields = true;
			}
		}
		
		if (existingEmptyFields.equals(Boolean.TRUE))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INFORMATION_PENDING);

	}
	
	private void validateConflictCpf(Person person) {

		if (person.getId() == null && personRepository.findByCpf(person.getCpf().trim()) != null || 
				person.getId() != null && personRepository.findByCpfAndIdNot(person.getCpf().trim(), person.getId()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionMessages.EXCEPTION_MSG_CPF_EXIST);
		}
	}
	
	private void validateAccountType(Person person) {

		if (!person.getAccounts().isEmpty()) {
			for (Account account : person.getAccounts()) {
				if (!account.getType().equals(AccountTypeEnum.CURRENT_ACCOUNT.getDescription()) 						 
						&& 	!account.getType().equals(AccountTypeEnum.SAVINGS_ACCOUNT.getDescription()
						)) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionMessages.EXCEPTION_MSG_ACCOUNT_TYPE_NOT_EXIST);
				}
			}
		}
	}

	@Override
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	@Override
	public List<Person> listPerson() {
		return (List<Person>) personRepository.findAll();
	}

}