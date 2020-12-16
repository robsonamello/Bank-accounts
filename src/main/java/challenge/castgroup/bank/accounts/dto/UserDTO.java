package challenge.castgroup.bank.accounts.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 
 * @author robson.a.mello
 *
 */
@XStreamAlias("USER")
public class UserDTO {

	@XStreamAlias("USERID")
	private  String userId;

	@XStreamAlias("NAME")
	private  String name;

	@XStreamAlias("LOGIN")
	private  String login;

	@XStreamAlias("EMAIL")
	private  String email;

	@XStreamAlias("PASSWORD")
	private  String password;
	
	@XStreamAlias("ROLE")
	private  String role;

	public UserDTO() {
		super();
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}