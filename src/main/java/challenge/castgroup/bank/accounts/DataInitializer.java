package challenge.castgroup.bank.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.service.UserService;
import challenge.castgroup.bank.accounts.util.RoleEnum;

/***
 * 
 * @author robson.a.mello
 *
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserService users;
    
    @Value("${challenge.castgroup.bank.accounts.user.name}")
	private String name;
	    
	@Value("${challenge.castgroup.bank.accounts.user.login}")
	private String login;
	
	@Value("${challenge.castgroup.bank.accounts.user.pwd}")
	private String pwd;
	
	@Value("${challenge.castgroup.bank.accounts.user.email}")
	private String email;
	
	/***
	 * Launch the application with a standard user (Admin)
	 */
    @Override
    public void run(String... args) throws Exception {
    	User user = new User();
    	user.setName(name);
    	user.setLogin(login);
    	user.setEmail(email);
    	user.setPassword(pwd);
    	user.setRole(RoleEnum.ADMIN.getDescription());
    	users.saveUser(user);
    }
}
