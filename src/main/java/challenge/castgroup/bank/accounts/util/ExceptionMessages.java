package challenge.castgroup.bank.accounts.util;

/***
 * 
 * @author robson.a.mello
 *
 */
public class ExceptionMessages {
	
	private ExceptionMessages(){}

	public static final String EXCEPTION_MSG_INFORMATION_PENDING = "Data pending completion";
	public static final String EXCEPTION_MSG_PERSON_NOT_EXIST = "This person does not exist";
	public static final String EXCEPTION_MSG_CPF_EXIST = "CPF already registered";
	public static final String EXCEPTION_MSG_PERSON_IDENTIFIER_NOT_EXIST = "The person's identifier needs to be informed";
	public static final String EXCEPTION_MSG_ACCOUNT_NOT_EXIST = "This account does not exist";
	public static final String EXCEPTION_MSG_OPERATION_CANCELED = "OPERATION CANCELED. Unexpected error in your transaction. Try again";
	public static final String EXCEPTION_MSG_NOT_ENOUGH_BALANCE = "Not enough balance";
	public static final String EXCEPTION_MSG_EMAIL_EXISTS = "Existing email.";
	public static final String EXCEPTION_MSG_LOGIN_EXISTS = "Existing login.";
	public static final String EXCEPTION_MSG_INVALID_LOGIN = "Invalid login or password.";
	public static final String EXCEPTION_MSG_INVALID_EMAIL = "Invalid Email.";
	public static final String EXCEPTION_MSG_INVALID_ROLE = "Invalid ROle.";
	public static final String EXCEPTION_MSG_MISSING_FIELDS = "Fields Not Filled.";
	public static final String EXCEPTION_MSG_UNAUTHORIZED = "Unauthorized.";
	public static final String EXCEPTION_MSG_INVALID_CREDENTIALS = "Invalid credentials.";
	public static final String EXCEPTION_MSG_USER_NOT_EXISTS = "User does not exist.";
	public static final String EXCEPTION_MSG_INVALID_TOKEN = "Unauthorized - Invalid Token";
	public static final String EXCEPTION_MSG_EXPIRED_TOKEN = "Unauthorized - Invalid Session";
	public static final String EXCEPTION_MSG_ACCOUNT_TYPE_NOT_EXIST = "Account type  does not exist";

}