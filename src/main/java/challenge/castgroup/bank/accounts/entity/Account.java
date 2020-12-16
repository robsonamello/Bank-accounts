package challenge.castgroup.bank.accounts.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 
 * @author robson.a.mello
 *
 */
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private BigInteger id;

	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private BigDecimal balance;
	
	public Account() {
		super();
	}
	
	public Account(BigInteger id, String type, BigDecimal balance) {
		super();
		this.id = id;
		this.type = type;
		this.balance = balance;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}