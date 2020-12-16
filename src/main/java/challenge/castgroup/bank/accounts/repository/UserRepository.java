package challenge.castgroup.bank.accounts.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;

import challenge.castgroup.bank.accounts.entity.User;

/***
 * 
 * @author robson.a.mello
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, BigInteger> {

	User findByUserId(BigInteger userId);

	User findByEmail(String email);

	User findByEmailAndUserIdNot(String email, BigInteger userId);

	User findByLogin(String login);
	
	User findByLoginAndUserIdNot(String login, BigInteger userId);

	User findByLoginAndPassword(String login, String password);

}
