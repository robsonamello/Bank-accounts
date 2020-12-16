package challenge.castgroup.bank.accounts.service.impl;

import java.math.BigInteger;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.repository.UserRepository;
import challenge.castgroup.bank.accounts.service.UserService;
import challenge.castgroup.bank.accounts.util.EncryptUtil;
import challenge.castgroup.bank.accounts.util.ExceptionMessages;
import challenge.castgroup.bank.accounts.util.RoleEnum;

/***
 * 
 * @author robson.a.mello
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	private UserServiceImpl() {}

	@Override
	public User saveUser(User user) {
		this.validateMissingFields(user);
		this.validateInconsistentFields(user);
		this.validateEmailConflict(user);
		this.validateLoginConflict(user);
		validateInconsistentRole(user);
		this.encriptPassword(user);
		return userRepository.save(user);
	}

	@Override
	public User checkAvailability(User user) {

		if (StringUtils.isEmpty(user.getLogin()) || StringUtils.isEmpty(user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_MISSING_FIELDS);
		}

		this.encriptPassword(user);
		
		user = findByLoginAndPassword(user.getLogin(), user.getPassword());

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INVALID_EMAIL);
		}

		return user;
	}
	
	@Override
	public User findByUserId(BigInteger userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	private User findByLoginAndPassword(String login, String password) {
		return userRepository.findByLoginAndPassword(login, password);
	}

	private void encriptPassword(User user) {

		if(user.getUserId() == null) {
			user.setPassword(EncryptUtil.encryptPassword(user.getPassword()));
		}
	}

	private void validateLoginConflict(User user) {
		
		if (user.getUserId() == null && userRepository.findByLogin(user.getLogin().trim()) != null || 
				user.getUserId() != null && userRepository.findByLoginAndUserIdNot(user.getLogin().trim(), user.getUserId()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionMessages.EXCEPTION_MSG_LOGIN_EXISTS);
		}
	}

	private void validateEmailConflict(User user) {

		if (user.getUserId() == null && userRepository.findByEmail(user.getEmail().trim()) != null || 
				user.getUserId() != null && userRepository.findByEmailAndUserIdNot(user.getEmail().trim(), user.getUserId()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionMessages.EXCEPTION_MSG_EMAIL_EXISTS);
		}
	}

	private void validateInconsistentFields(User user) {

		if (!new EmailValidator().isValid(user.getEmail().trim(), null))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INVALID_EMAIL);

	}
	
	private void validateInconsistentRole(User user) {

		if (!user.getRole().equals(RoleEnum.ADMIN.getDescription()) &&
			!user.getRole().equals(RoleEnum.GUEST.getDescription()) &&
			!user.getRole().equals(RoleEnum.MANAGER.getDescription()))				
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_INVALID_ROLE);

	}

	private void validateMissingFields(User user) {

		Boolean existingEmptyFields = false;

		if (user.getName() == null || StringUtils.isEmpty(user.getName().trim()))
			existingEmptyFields = true;

		if (user.getLogin() == null || StringUtils.isEmpty(user.getLogin().trim()))
			existingEmptyFields = true;

		if (user.getEmail() == null || StringUtils.isEmpty(user.getEmail().trim()))
			existingEmptyFields = true;

		if (user.getPassword() == null || StringUtils.isEmpty(user.getPassword().trim()))
			existingEmptyFields = true;

		if (existingEmptyFields)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_MISSING_FIELDS);

	}

	@Override
	public User getAuthenticatedUser() {
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return this.findByLogin(userLogin);
	}

}
