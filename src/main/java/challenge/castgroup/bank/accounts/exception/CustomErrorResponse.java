package challenge.castgroup.bank.accounts.exception;

/***
 * 
 * @author robson.a.mello
 *
 */
public class CustomErrorResponse {

	private String message;
	
	private int statusCode;

	public CustomErrorResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
