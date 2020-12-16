package challenge.castgroup.bank.accounts.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 
 * @author robson.a.mello
 *
 */
@Entity
@Table(name = "person")
public class Person {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private BigInteger id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	private Date bornDate;

	@Column(nullable = false)
	private String phoneNumber;
	
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
    private Collection<Account> accounts;
	
	public Person() {
		super();
	}
	
	public Person(BigInteger id, String name, String cpf, Date bornDate, String phoneNumber,
			Collection<Account> accounts) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.bornDate = bornDate;
		this.phoneNumber = phoneNumber;
		this.accounts = accounts;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}

}