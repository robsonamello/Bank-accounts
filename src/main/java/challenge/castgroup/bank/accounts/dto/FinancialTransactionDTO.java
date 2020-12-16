package challenge.castgroup.bank.accounts.dto;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 
 * @author robson.a.mello
 *
 */
@XStreamAlias("FINANCIALTRANSACTION")
public class FinancialTransactionDTO {

	@XStreamAlias("ACCOUNTID")
	private String accountId;

	@XStreamAlias("TYPE")
	private String type;
	
	@XStreamAlias("VALUE")
	private BigDecimal value;

	public FinancialTransactionDTO() {
		super();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}