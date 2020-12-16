package challenge.castgroup.bank.accounts.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;

import challenge.castgroup.bank.accounts.entity.Account;

/***
 * 
 * @author robson.a.mello
 *
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, BigInteger> {
}