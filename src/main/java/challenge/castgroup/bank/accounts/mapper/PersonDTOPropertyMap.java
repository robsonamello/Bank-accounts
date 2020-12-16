package challenge.castgroup.bank.accounts.mapper;

import org.modelmapper.PropertyMap;

import challenge.castgroup.bank.accounts.dto.PersonDTO;
import challenge.castgroup.bank.accounts.entity.Person;

/***
 * 
 * @author robson.a.mello
 *
 */
public class PersonDTOPropertyMap extends PropertyMap<Person, PersonDTO> {

	@Override
	protected void configure() {}

}