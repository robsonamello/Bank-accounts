package challenge.castgroup.bank.accounts.service.impl;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.entity.Account;
import challenge.castgroup.bank.accounts.repository.AccountRepository;
import challenge.castgroup.bank.accounts.service.AccountService;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	private AccountServiceImpl() {}
	
	@Override
	public Account filterById(BigInteger accountId){
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (!account.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_ACCOUNT_NOT_EXIST);
	    }
		return account.get(); 
	}

	private void validateMandatoryFields(Account account) {

		Boolean existingEmptyFields = false;

		if (account.getType() == null || StringUtils.isEmpty(account.getType().trim()))
			existingEmptyFields = true;
		
		if (account.getBalance() == null)
			existingEmptyFields = true;
		
		if (existingEmptyFields.equals(Boolean.TRUE))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INFORMATION_PENDING);

	}
	
	@Override
	public Account saveAccount(Account account) {
		validateMandatoryFields(account);
		return accountRepository.save(account);
	}
	
}