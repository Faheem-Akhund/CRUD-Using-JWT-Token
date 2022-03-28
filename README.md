# crud jwt 

I have used lombok for getter setter etc see what lombok is if you dont know

Clone the project 
### it has packages 
#### Prdouct

     its controller 
     its pojo /model
     its Repo 
     its service
     
#### User
	  its controller 
	  its MyUser Detail which implements UserDetails //for Jwt 
	  its pojo /model
	  its Repo 
	  its service which implements UserDetailsService //for jwt
     
#### JWT
	 it has JwtRequestFilter // for jwt 
	 it has Jwt utill Service which contains methods // for jwt
	 it has Refresh token pojo/model
	 refresh token repo 
	 refresh token service 
	  userToken class as bean !
     


## Installation


Dependency for Jwt change version which ever you want to use !
```XML
<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
</dependency>
```

## check Rest urls
### 1: localhost:8080/create
To create Customer or Admin (change roles according to Role user behaves as customer !
```JSON

{
    "userName": "faseeh",
    "password": "CUSTOMER",
    "roles": "ROLE_USER"
}
```
Response !

```JSON
{
    "code": 1,
    "message": "User Created",
    "object": "Sheeraz"
}
```
### 2: localhost:8080/authenticate

#### Request
```JSON
{
    "username":"faseeh",
    "password":"CUSTOMER"
}
```
#### Response
```JSON

{
    "code": 0,
    "message": "success",
    "object": {
        "username": "faseeh",
        "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYXNlZWgiLCJleHAiOjE2NDg0NTkwOTgsImlhdCI6MTY0ODQ1ODE5OH0.L0B1l8ObaNIAKdCwvwerrvz1QECircja4WC9ovypu30",
        "refreshToken": "87c0924f-a223-4982-b733-f0a38294c850"
    }
}
```


## DATABASE 

-> USER table
| id  | active | password  | roles | user_name |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| 1  | b'1'  | CUSTOMER  | USER  | faseeh  |

-> Refresh token table

| id  | expiry_date | token  | user_id |
| ------------- | ------------- | ------------- | ------------- | 
| 1  | 2022-04-23 11:04:25  | 87c0924f-a223-4982-b733-f0a38294c850  | 1  |


### Procedure

#### Refresh token 
 The refresh token is created by random key is stored  for that user (admin or Customer) in database 
it can be used to regenrate Jwt token again !
again jwt token is not stored in database it has a expiry time which we can set to minutes hours or days !

#### so whats the use of Refresh token ? and why dont we just increase the expiry time of jwt token
the jwt token allows the user to access its role based urls while the refresh token is just used to refresh the jwt without providing credentials 
so when user has jwt token he is allowed to see the the permitted urls and when the jwt token expired he re genrates the token by proving the refresh token 


### Security Config


```Java
@Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable().authorizeRequests()
            
                .antMatchers("/product").hasRole("ADMIN")
                .antMatchers("/cart").hasRole("USER")
                .antMatchers("/refreshtoken").permitAll()
                 
             
    }
```
#### In this we have shown 3 URLs
1. The Admin can create the products 
2. The User can see the chis cart 
3. Both can regenrate token by providing Refresh token to the /refresh url 


## Refresh the token 
### 1: localhost:8080/refresh 
```JSON

{
   "refreshToken": "87c0924f-a223-4982-b733-f0a38294c850"
}
```
Response !

```JSON
{
    "code": 0,
    "message": "success",
    "object": {
        "username": "faseeh",
        "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYXNlZWgiLCJleHAiOjE2NDg0NTkwOTgsImlhdCI6MTY0ODQ1ODE5OH0.L0B1l8ObaNIAKdCwvwerrvz1QECircja4WC9ovypu30",
        "refreshToken": "87c0924f-a223-4982-b733-f0a38294c850"
    }
}
```
