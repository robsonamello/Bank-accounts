package challenge.castgroup.bank.accounts.util;

/***
 * 
 * @author robson.a.mello
 *
 */
public class Constants {
	
	private Constants(){}

	// JWT Auth
	public static final long TOKEN_EXPIRATION_TIME = 86400000; // 1 day
	public static final String TOKEN_SECRET_KEY = "3D8F96F1AC8F84EECEA69BFBC2339-F744924D17E393DC29C992D6BDDEB-8AFC58D34B37618ED5E86C69A9549-268B462413D7ABD9BAD5959A9A63E";
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String ROLE_PREFIX = "ROLE_";
	public static final String TOKEN_HEADER_PARAM_NAME = "Authorization";

	// Model Mapper
	public static final String DATE_RESPONSE_FORMAT = "dd/MM/yyyy HH:mm:ss";

}