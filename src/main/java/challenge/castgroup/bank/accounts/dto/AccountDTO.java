package challenge.castgroup.bank.accounts.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 
 * @author robson.a.mello
 *
 */
@XStreamAlias("ACCOUNT")
public class AccountDTO {

	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("TYPE")
	private String type;
	
	@XStreamAlias("BALANCE")
	private String balance;
	
	public AccountDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

}