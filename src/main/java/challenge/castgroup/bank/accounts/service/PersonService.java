package challenge.castgroup.bank.accounts.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import challenge.castgroup.bank.accounts.entity.Person;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public interface PersonService {

	Person savePerson(Person person);
	
	Person filterById(BigInteger personId);

	List<Person> listPerson();
	
	public void deletePerson(Person person);
	
}
