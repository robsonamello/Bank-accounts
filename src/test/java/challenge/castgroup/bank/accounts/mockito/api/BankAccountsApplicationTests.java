package challenge.castgroup.bank.accounts.mockito.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import challenge.castgroup.bank.accounts.dto.AccountDTO;
import challenge.castgroup.bank.accounts.dto.FinancialTransactionDTO;
import challenge.castgroup.bank.accounts.dto.PersonDTO;
import challenge.castgroup.bank.accounts.dto.UserDTO;
import challenge.castgroup.bank.accounts.entity.Account;
import challenge.castgroup.bank.accounts.entity.Person;
import challenge.castgroup.bank.accounts.entity.User;
import challenge.castgroup.bank.accounts.repository.AccountRepository;
import challenge.castgroup.bank.accounts.repository.PersonRepository;
import challenge.castgroup.bank.accounts.repository.UserRepository;
import challenge.castgroup.bank.accounts.service.AccountService;
import challenge.castgroup.bank.accounts.service.PersonService;
import challenge.castgroup.bank.accounts.service.UserService;
import challenge.castgroup.bank.accounts.util.AccountTypeEnum;
import challenge.castgroup.bank.accounts.util.BankTransactionEnum;
import challenge.castgroup.bank.accounts.util.RoleEnum;

/***
 * 
 * @author robson.a.mello
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BankAccountsApplicationTests extends AbstractTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private PersonService personService;

	@MockBean
	private PersonRepository personRepository;
	
	@Autowired
	private AccountService accountService;

	@MockBean
	private AccountRepository accountRepository;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private String personUri = "/person";	
	
	private String finantialTransactionUri = "/finantialtransaction";
	
	private String personByIdUri = "/person/1";
	
	private String usersUri = "/users";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void createUser_withMissingFields() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("");
		userDTO.setEmail("");
		userDTO.setLogin("");
		userDTO.setPassword("");

		String inputJson = super.mapToJson(userDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(usersUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void createUser_withInvalidFieldsErros() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Name");
		userDTO.setEmail("2.2.com");
		userDTO.setLogin("login_to_invalid_email");
		userDTO.setPassword("password");

		String inputJson = super.mapToJson(userDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(usersUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void savePersonUnsuccessfully_MandatoryFields() {
		Person person = new Person(null, null, null, new Date(), "(00)0000-0000", null);
		
		Account savingsAccount = new Account();
		savingsAccount.setBalance(new BigDecimal(100));
		savingsAccount.setType(AccountTypeEnum.SAVINGS_ACCOUNT.getDescription());
		
		Account currentAccount = new Account();
		currentAccount.setBalance(new BigDecimal(200));
		currentAccount.setType(AccountTypeEnum.CURRENT_ACCOUNT.getDescription());
		
		List<Account> listAccount = new ArrayList<Account>();
		listAccount.add(savingsAccount);
		listAccount.add(currentAccount);
		
		person.setAccounts(listAccount);
		
		when(personRepository.save(person)).thenReturn(person);
		assertNotNull(person);
	}
	
	@Test
	public void checkAvailability() {
		User user = new User(null,"Branca Mieiro Brum", "BMB", "bmb@castgroup.com.br", "mudar123", RoleEnum.GUEST.getDescription());
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
		
		when(userRepository.findByLoginAndPassword("123", "123")).thenReturn(user);
		assertNotNull(user);
	}
	
	@Test
	public void findByCpfAndIdNot() {
		Person person = new Person(null, "Virgílio Granjeiro Vergueiro", "90742741000130", new Date(), "(00)0000-0000", null);
		when(personRepository.findByCpfAndIdNot("21909243000117",BigInteger.ONE)).thenReturn(person);
		assertNotNull(person);
	}
	
	@Test
	public void findByCpf() {
		Person person = new Person(null, "Angelo Noronha Rijo", "21909243000117", new Date(), "(00)0000-0000", null);
		when(personRepository.findByCpf("21909243000117")).thenReturn(person);
		assertNotNull(person);
	}
	
	@Test
	public void listPerson() {
		when(personRepository.findAll()).thenReturn(Stream
				.of(new Person(null, "Angelo Noronha Rijo", "21909243000117", new Date(), "(00)0000-0000", null), 
					new Person(null, "Taísa Sampaio", "45284950000123", new Date(), "(00)0000-0000", null)).collect(Collectors.toList()));
		assertEquals(2, personService.listPerson().size());
	}
	
	@Test
	public void savePersonSuccessfully() {
		Person person = new Person(null, "Nayma Dantas Belo", "32126518302", new Date(), "(00)0000-0000", null);
		
		Account savingsAccount = new Account();
		savingsAccount.setBalance(new BigDecimal(100));
		savingsAccount.setType(AccountTypeEnum.SAVINGS_ACCOUNT.getDescription());
		
		Account currentAccount = new Account();
		currentAccount.setBalance(new BigDecimal(200));
		currentAccount.setType(AccountTypeEnum.CURRENT_ACCOUNT.getDescription());
		
		List<Account> listAccount = new ArrayList<Account>();
		listAccount.add(savingsAccount);
		listAccount.add(currentAccount);
		
		person.setAccounts(listAccount);
		
		when(personRepository.save(person)).thenReturn(person);
		assertEquals(person, personService.savePerson(person));
	}
	
	@Test
	public void finantialTransactionSuccessfully() {
		Account account = new Account();
		
		account.setId(BigInteger.ONE);
		account.setType(BankTransactionEnum.CREDIT.getDescription());
		account.setBalance(BigDecimal.TEN);
		
		when(accountRepository.save(account)).thenReturn(account);
		assertEquals(account, accountService.saveAccount(account));
	}
	
	@Test
	public void deletePersonSuccessfully() {
		Person person = new Person();
		person.setId(BigInteger.ONE);
		personService.deletePerson(person);
		verify(personRepository, times(1)).delete(person);
	}
	
	@Test
	public void saveUserTestSuccessfully() {
		User user = new User(null,"Branca Mieiro Brum", "BMB", "bmb@castgroup.com.br", "mudar123", RoleEnum.GUEST.getDescription());
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
	}
	
	@Test
	public void createPerson_withMissingFields() throws Exception {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setName("Milana Andrade Rangel");
		personDTO.setCpf("");
		personDTO.setPhoneNumber("");
		String inputJson = super.mapToJson(personDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(personUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void updatePerson_withByPut() throws Exception {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId("1");
		personDTO.setName("Armaan Lagoa Cerveira");
		personDTO.setBornDate(new Date());
		personDTO.setCpf("26412144072");
		personDTO.setPhoneNumber("(81)2222-2222");
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setBalance("100");
		accountDTO.setType("P");
		
		AccountDTO accountDTO2 = new AccountDTO();
		accountDTO2.setBalance("200");
		accountDTO2.setType("P");
		
		List<AccountDTO> listaAccountDTO = new ArrayList<AccountDTO>();
		listaAccountDTO.add(accountDTO);
		listaAccountDTO.add(accountDTO2);
		
		personDTO.setAccounts(listaAccountDTO);
		String inputJson = super.mapToJson(personDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(personUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void credit_withSuccess() throws Exception {
		FinancialTransactionDTO financialTransactionDTO = new FinancialTransactionDTO();
		financialTransactionDTO.setAccountId("1");
		financialTransactionDTO.setType("C");
		financialTransactionDTO.setValue(new BigDecimal(1000));
		String inputJson = super.mapToJson(financialTransactionDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(finantialTransactionUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertNotEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void debit_withSuccess() throws Exception {
		FinancialTransactionDTO financialTransactionDTO = new FinancialTransactionDTO();
		financialTransactionDTO.setAccountId("1");
		financialTransactionDTO.setType("D");
		financialTransactionDTO.setValue(new BigDecimal(500));
		String inputJson = super.mapToJson(financialTransactionDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(finantialTransactionUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertNotEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void createPerson_withGet() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(personUri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void createPerson_withFilter() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(personByIdUri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assert.assertNotEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void createPerson_withDelete() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(personByIdUri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

}