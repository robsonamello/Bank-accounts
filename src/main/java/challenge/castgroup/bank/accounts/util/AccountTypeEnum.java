package challenge.castgroup.bank.accounts.util;

/***
 * 
 * @author robson.a.mello
 *
 */
public enum AccountTypeEnum {
	
	SAVINGS_ACCOUNT("S"),
	CURRENT_ACCOUNT("C");

    private String description;

    AccountTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}