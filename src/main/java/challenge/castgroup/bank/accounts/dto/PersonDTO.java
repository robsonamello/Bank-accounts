package challenge.castgroup.bank.accounts.dto;

import java.util.Collection;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 
 * @author robson.a.mello
 *
 */
@XStreamAlias("PERSON")
public class PersonDTO {
	
	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("NAME")
	private String name;

	@XStreamAlias("CPF")
	private String cpf;
	
	@XStreamAlias("BORNDATE")
	private Date bornDate;

	@XStreamAlias("PHONENUMBER")
	private String phoneNumber;
	
	private Collection<AccountDTO> accounts;

	public PersonDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Collection<AccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<AccountDTO> accounts) {
		this.accounts = accounts;
	}
	
}