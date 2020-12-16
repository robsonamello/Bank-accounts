package challenge.castgroup.bank.accounts.util;

/***
 * 
 * @author robson.a.mello
 *
 */
public enum RoleEnum {
	
	ADMIN("A"),
	MANAGER("M"),
	GUEST("G");

    private String description;

    RoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}