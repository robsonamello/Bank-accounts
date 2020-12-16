package challenge.castgroup.bank.accounts.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 
 * @author robson.a.mello
 *
 */
@XStreamAlias("USERAUTH")
public class UserAuthDTO {

	@XStreamAlias("LOGIN")
	private String login;

	@XStreamAlias("PASSWORD")
	private String password;
	
	@XStreamAlias("PASSWORD")
	private String role;
		public UserAuthDTO() {
		super();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
