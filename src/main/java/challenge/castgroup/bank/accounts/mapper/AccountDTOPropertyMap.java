package challenge.castgroup.bank.accounts.mapper;

import org.modelmapper.PropertyMap;

import challenge.castgroup.bank.accounts.dto.AccountDTO;
import challenge.castgroup.bank.accounts.entity.Account;

/***
 * 
 * @author robson.a.mello
 *
 */
public class AccountDTOPropertyMap extends PropertyMap<Account, AccountDTO> {

	@Override
	protected void configure() {}

}