package challenge.castgroup.bank.accounts.util;

/***
 * 
 * @author robson.a.mello
 *
 */
public enum BankTransactionEnum {
	
	CREDIT("C"),
	DEBIT("D");

    private String description;

    BankTransactionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    
}