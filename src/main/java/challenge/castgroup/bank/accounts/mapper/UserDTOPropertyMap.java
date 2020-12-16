package challenge.castgroup.bank.accounts.mapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import challenge.castgroup.bank.accounts.dto.UserDTO;
import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.util.Constants;

/***
 * 
 * @author robson.a.mello
 *
 */
public class UserDTOPropertyMap extends PropertyMap<User, UserDTO> {

	Converter<Calendar, String> formatCalendarToString = new AbstractConverter<Calendar, String>() {
		protected String convert(Calendar source) {
			SimpleDateFormat s = new SimpleDateFormat(Constants.DATE_RESPONSE_FORMAT);
			return source == null ? null : s.format(source.getTime());
		}
	};

	@Override
	protected void configure() {
		map().setPassword(null);
	}

}