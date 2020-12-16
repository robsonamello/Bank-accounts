# Desafio CAST - Contas Banc�rias (Bank Accounts)

Api REST que permitir realizar cadastro de pessoas, contas, movimenta��es banc�rias e autentica��o 
com permiss�es baseadas em pap�is(ROLES)

   - Java 1.8
   - Autentica��o Basic Utilizando Spring Security
   - Banco de dados em m�moria (H2);
   - Maven como gerenciador de depend�ncias
   - Persist�ncia com Hibernate e JPA
   - Aplica��o REST com Spring boot. Framework (2.1.4.RELEASE)
   - Servlet Containner Tomcat embarcado na aplica��o
   - JWT como Token
   - Testes unit�rios utilizando mockito. Vers�o 1.10.19
   - As senhas dos usu�rios cadastrados s�o persistidas criptografadas

### Build

A aplica��o foi desenvolvida com Spring Boot.

Para realizar o processo de Build e instalar as depend�ncias do projeto, deve-se executar o Maven:

```sh
$ git clone https://github.com/robsonamello/bank-accounts.git
$ cd bankaccounts
$ mvn clean bankaccounts
```

Execu��o da aplica��o ap�s Build.

```sh
$ cd bankaccounts
$ cd target
$ java -jar bankaccounts-0.0.1-SNAPSHOT.jar
```
A aplica��o ser� iniciada na porta 443:

http://localhost:443

#### Rotas

   - As rotas est�o listadas de acordo com suas permiss�es. A obten��o do TOKEN � uma etapa obrigat�ria para o consumo dos demais servi�os listados;
   - Ao executar o servi�o de obten��o do TOKEN, � preciso copiar o retorno contido no HEADER e colar no Authorization dos demais servi�os
   - Exemplo v�lido: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg
   -  O login e a permiss�o s�o armazenados no TOKEN da aplica��o onde � feita a verifica��o com o registo cadastrado no banco de dados;
   -  Sempre que a aplica��o sobre em mem�ria um usu�rio com permiss�o ADMIN � criado, com as seguintes credenciais:
   -  Login: admin, Senha: admin, Permiss�o: Administrador
   -  As siglas cadastradas s�o A, M e G (ADMIN, MANAGER E GUEST)
   -  S� � poss�vel ter uma permiss�o por usu�rio. A implementa��o de realacionamento N:N n�o foi contemplada nessa vers�o
   -  Para fins did�ticos, o perfil Administrador pode acessar todos os recursos, Gerente tamb�m, exceto controle de acesso e Visitante pode apenas consultar dados da pessoa

| Descri��o                                   | URL     								                            |  Permiss�o                          | 
| ------------------------------------------- | ------------------------------------------------------------------- | ----------------------------------- |
| Autentica��o do usu�rio (retorno header)    | POST Request to: http://localhost:443/signin                        | P�blica                             |
| Cadastro de Pessoas e Contas Banc�rias      | POST Request to: http://localhost:443/person                        | Administrador e Gerente             |
| Atualiza��o de Pessoas e Contas Banc�rias   | PUT Request to: http://localhost:443/person                         | Administrador e Gerente             |
| Listagem de Pessoas e Contas Banc�rias      | GET Request to: http://localhost:443/person                         | Administrador, Gerente e Visitante  |
| Filtro de Pessoas e Contas Banc�rias        | GET Request to: http://localhost:443/person/{personId}              | Administrador, Gerente e Visitante  |
| Opera��o de saque e dep�sito                | POST Request to: http://localhost:443/person/finantialtransaction}  | Administrador, Gerente              |
| Exclus�o de Pessoas e Contas Banc�rias      | DELETE Request to: http://localhost:443/person/{personId}           | Administrador, Gerente              |
| Cadastro de usu�rio da aplica��o            | POST Request to: http://localhost:443/users                         | Administrador                       |

### Observa��es para o consumo dos servi�os

Seguem algumas orienta��es para facilitar o entendimento da regra de neg�cio. O cliente de consumo dos servi�os utilizado nesse desafio foi o Insomnia 2020.5.2, mas voc� pode usar o cliente de sua prefer�ncia:

O retorno do login � exibido conforme a imagem abaixo

![token](./token.png)

Ao recuperar o token contendo as informa��es do usu�rio e perfil, incluir no cabe�alho dos demais servi�o a serem validados por autentica��o

![example](./example.png)

Algumas siglas utilizadas como Enums foram padronizadas no formato inlg�s americano, com apenas as inicais

```
	- SAVINGS_ACCOUNT("S"),
	- CURRENT_ACCOUNT("C");
	- CREDIT("C"),
	- DEBIT("D");
	- ADMIN("A"),
	- MANAGER("M"),
	- GUEST("G");
```

### CURLs usados na aplicado contendo massas v�lidas para testes iniciais

1. Autentica��o do usu�rio (retorno header) 
```
curl --request POST \
  --url http://localhost:443/signin \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=2AC72A7EB14C6BAC13EF8BBB2E55C10B \
  --data '{
	"login" : "admin",
	"password" : "admin"
}'
```
------------------------------------------------------------------------------------
2. Cadastro de Pessoas e Contas Banc�rias
```
curl --request POST \
  --url http://localhost:443/person \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc3MjE2fQ.IXn3L9uHeadetHRkeQVQEYsYFtGPj6xLgDaGWo9I9XD6fpwiAK3l96wNQ7qfmL5k2r_83xGL0_XSJgJG1z-Igw' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=36601C1684CCBFD41C33CC87A76FCEF9 \
  --data '{
  "name": "Robson Alexandre de Mello",
  "cpf": "31357425805",
  "bornDate": "1982-09-15",
  "phoneNumber": "(81)0000-0000",
  "accounts": [
    { "type":"S", "balance":"0" },
    { "type":"C", "balance":"0" }
  ]
}'
```
------------------------------------------------------------------------------------
3. Atualiza��o de Pessoas e Contas Banc�rias
```
curl --request PUT \
  --url http://localhost:443/person \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=36601C1684CCBFD41C33CC87A76FCEF9 \
  --data '{
	"id" : "1",
  "name": "Robson Alexandre de Mello",
  "cpf": "31357425805",
  "bornDate": "1982-09-15",
  "phoneNumber": "(81)0000-0000",
  "accounts": [
    { "type":"C", "balance":"0" }
  ]
}'
```
------------------------------------------------------------------------------------
4. Listagem de Pessoas e Contas Banc�rias
```
curl --request GET \
  --url http://localhost:443/person \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=3293BFB3D6D29E5843FA87787986EE3C
  ```
------------------------------------------------------------------------------------
5. Filtro de Pessoas e Contas Banc�rias
```
curl --request GET \
  --url http://localhost:443/person/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc4NTQzfQ._012YqHaOH3imfTxiNk4HNM5rbl6auJbXJNw0vI-WNbaGilDM2QN9uOfI3hb-ysEbwW1yI69YWeOic88sJ-HKg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=1FD7178A60B11D73F549BF00DC6A0CC9
 ``` 
------------------------------------------------------------------------------------
6. Filtro de Pessoas e Contas Banc�rias
```
curl --request POST \
  --url http://localhost:443/finantialtransaction \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc4NTQzfQ._012YqHaOH3imfTxiNk4HNM5rbl6auJbXJNw0vI-WNbaGilDM2QN9uOfI3hb-ysEbwW1yI69YWeOic88sJ-HKg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=1FD7178A60B11D73F549BF00DC6A0CC9 \
  --data '{
	"accountId":"4",
  "type": "C",
  "value": 399
}'
```
------------------------------------------------------------------------------------
7. Exclus�o de Pessoas e Contas Banc�rias
```
curl --request DELETE \
  --url http://localhost:443/person/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc4NTQzfQ._012YqHaOH3imfTxiNk4HNM5rbl6auJbXJNw0vI-WNbaGilDM2QN9uOfI3hb-ysEbwW1yI69YWeOic88sJ-HKg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=4C0B88BB1C4C01DD9E199A8A6CC9AEDC
```
------------------------------------------------------------------------------------
8. Cadastro de usu�rio da aplica��o
```
curl --request POST \
  --url http://localhost:443/users \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=4C0B88BB1C4C01DD9E199A8A6CC9AEDC \
  --data '{
	"name":"Teste Perfil Gerente",
	"login":"loginteste",
	"email":"testegerente@castgroup.com.br",
	"password":"123mudar",
	"role":"M"
}'
```
### Guias, Ferramentas e Bibliotecas utilizados no desenvolvimento dessa aplica��o

| Refer�ncias                                             |
| ------------------------------------------------------- |
| https://spring.io/guides/gs/serving-web-content/        |
| https://spring.io/guides/gs/accessing-data-rest/        | 
| https://spring.io/guides/gs/accessing-neo4j-data-rest/  |
| https://spring.io/guides/gs/spring-boot-docker/         |
| https://www.baeldung.com/spring-security-custom-filter  |
| http://wiremock.org/docs/getting-started/               |
| https://maven.apache.org/guides/index.html              |
| https://www.mockable.io/                                |
| https://spring.io/guides/gs/securing-web/               | 
| https://www.baeldung.com/spring-security-custom-filter  |
| http://modelmapper.org/getting-started/                 |
| https://spring.io/guides/gs/rest-service/               |
