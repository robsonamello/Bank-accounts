package challenge.castgroup.bank.accounts.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;

import challenge.castgroup.bank.accounts.entity.Person;

/***
 * 
 * @author robson.a.mello
 *
 */
public interface PersonRepository extends PagingAndSortingRepository<Person, BigInteger> {

	Person findByCpf(String cpf);
	
	Person findByCpfAndIdNot(String cpf, BigInteger id);

}