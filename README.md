# Desafio CAST - Contas Bancárias (Bank Accounts)

Api REST que permitir realizar cadastro de pessoas, contas, movimentações bancárias e autenticação 
com permissões baseadas em papéis(ROLES)

   - Java 1.8
   - Autenticação Basic Utilizando Spring Security
   - Banco de dados em mémoria (H2);
   - Maven como gerenciador de dependências
   - Persistência com Hibernate e JPA
   - Aplicação REST com Spring boot. Framework (2.1.4.RELEASE)
   - Servlet Containner Tomcat embarcado na aplicação
   - JWT como Token
   - Testes unitários utilizando mockito. Versão 1.10.19
   - As senhas dos usuários cadastrados são persistidas criptografadas

### Build

A aplicação foi desenvolvida com Spring Boot.

Para realizar o processo de Build e instalar as dependências do projeto, deve-se executar o Maven:

```sh
$ cd bankaccounts
$ mvn clean bankaccounts
```

Execução da aplicação após Build.

```sh
$ cd bankaccounts
$ cd target
$ java -jar bankaccounts-0.0.1-SNAPSHOT.jar
```
A aplicação será iniciada na porta 443:

http://localhost:443

#### Rotas

   - As rotas estão listadas de acordo com suas permissões. A obtenção do TOKEN é uma etapa obrigatória para o consumo dos demais serviços listados;
   - Ao executar o serviço de obtenção do TOKEN, é preciso copiar o retorno contido no HEADER e colar no Authorization dos demais serviços
   - Exemplo válido: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg
   -  O login e a permissão são armazenados no TOKEN da aplicação onde é feita a verificação com o registo cadastrado no banco de dados;
   -  Sempre que a aplicação sobre em memória um usuário com permissão ADMIN é criado, com as seguintes credenciais:
   -  Login: admin, Senha: admin, Permissão: Administrador
   -  As siglas cadastradas são A, M e G (ADMIN, MANAGER E GUEST)
   -  Só é possível ter uma permissão por usuário. A implementação de realacionamento N:N não foi contemplada nessa versão
   -  Para fins didáticos, o perfil Administrador pode acessar todos os recursos, Gerente também, exceto controle de acesso e Visitante pode apenas consultar dados da pessoa

| Descrição                                   | URL     								                            |  Permissão                          | 
| ------------------------------------------- | ------------------------------------------------------------------- | ----------------------------------- |
| Autenticação do usuário (retorno header)    | POST Request to: http://localhost:443/signin                        | Pública                             |
| Cadastro de Pessoas e Contas Bancárias      | POST Request to: http://localhost:443/person                        | Administrador e Gerente             |
| Atualização de Pessoas e Contas Bancárias   | PUT Request to: http://localhost:443/person                         | Administrador e Gerente             |
| Listagem de Pessoas e Contas Bancárias      | GET Request to: http://localhost:443/person                         | Administrador, Gerente e Visitante  |
| Filtro de Pessoas e Contas Bancárias        | GET Request to: http://localhost:443/person/{personId}              | Administrador, Gerente e Visitante  |
| Operação de saque e depósito                | POST Request to: http://localhost:443/person/finantialtransaction}  | Administrador, Gerente              |
| Exclusão de Pessoas e Contas Bancárias      | DELETE Request to: http://localhost:443/person/{personId}           | Administrador, Gerente              |
| Cadastro de usuário da aplicação            | POST Request to: http://localhost:443/users                         | Administrador                       |

### Observações para o consumo dos serviços

Seguem algumas orientações para facilitar o entendimento da regra de negócio. O cliente de consumo dos serviços utilizado nesse desafio foi o Insomnia 2020.5.2, mas você pode usar o cliente de sua preferência:

O retorno do login é exibido conforme a imagem abaixo

![token](./token.png)

Ao recuperar o token contendo as informações do usuário e perfil, incluir no cabeçalho dos demais serviço a serem validados por autenticação

![example](./example.png)

Algumas siglas utilizadas como Enums foram padronizadas no formato inlgês americano, com apenas as inicais

```
	- SAVINGS_ACCOUNT("S"),
	- CURRENT_ACCOUNT("C");
	- CREDIT("C"),
	- DEBIT("D");
	- ADMIN("A"),
	- MANAGER("M"),
	- GUEST("G");
```

### CURLs usados na aplicado contendo massas válidas para testes iniciais

1. Autenticação do usuário (retorno header) 
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
2. Cadastro de Pessoas e Contas Bancárias
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
3. Atualização de Pessoas e Contas Bancárias
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
4. Listagem de Pessoas e Contas Bancárias
```
curl --request GET \
  --url http://localhost:443/person \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTM5MjYwfQ.X47JbjA2WLZKvhxuUmVxcUkcl6OMQ1VOmVgdqtwnU0eFE9HmTEmO0ErCkcQwLF6iAFK6EJEN9rp99m6TLuY8Gg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=3293BFB3D6D29E5843FA87787986EE3C
  ```
------------------------------------------------------------------------------------
5. Filtro de Pessoas e Contas Bancárias
```
curl --request GET \
  --url http://localhost:443/person/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc4NTQzfQ._012YqHaOH3imfTxiNk4HNM5rbl6auJbXJNw0vI-WNbaGilDM2QN9uOfI3hb-ysEbwW1yI69YWeOic88sJ-HKg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=1FD7178A60B11D73F549BF00DC6A0CC9
 ``` 
------------------------------------------------------------------------------------
6. Filtro de Pessoas e Contas Bancárias
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
7. Exclusão de Pessoas e Contas Bancárias
```
curl --request DELETE \
  --url http://localhost:443/person/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBIiwiZXhwIjoxNjA4MTc4NTQzfQ._012YqHaOH3imfTxiNk4HNM5rbl6auJbXJNw0vI-WNbaGilDM2QN9uOfI3hb-ysEbwW1yI69YWeOic88sJ-HKg' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=4C0B88BB1C4C01DD9E199A8A6CC9AEDC
```
------------------------------------------------------------------------------------
8. Cadastro de usuário da aplicação
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
### Guias, Ferramentas e Bibliotecas utilizados no desenvolvimento dessa aplicação

| Referências                                             |
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
