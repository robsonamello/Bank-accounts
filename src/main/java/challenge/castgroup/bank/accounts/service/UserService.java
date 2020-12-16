package challenge.castgroup.bank.accounts.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import challenge.castgroup.bank.accounts.entity.User;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public interface UserService {

	User saveUser(User user);

	User checkAvailability(User user);

	User findByUserId(BigInteger userId);

	User findByLogin(String login);

	User getAuthenticatedUser();

}
