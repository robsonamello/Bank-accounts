package challenge.castgroup.bank.accounts.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import challenge.castgroup.bank.accounts.entity.Account;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public interface AccountService {

	Account filterById(BigInteger contaId);
	
	Account saveAccount(Account conta);

}