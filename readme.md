# Spring Boot Authentication Service

This is an authentication service build using Spring Boot. Spring Boot is a JAVA Framework used for Rapid Application Development. It is an extension of Spring framework combined with embedded servers. It autoconfigures dependencies by itself let developers focus on the coding part only.

In this service, we have routes for user authentication that can be used in any backend application for authentication.

### Base URL
`https://auth-service-springboot.up.railway.app/auth`

### Routes

- `/signup`: (POST) To sign up a user. It takes a request body and the user gets a mail with otp for verification. 
- `/login`: (POST) To login a user. It takes a request body and the user gets permission only if it is valid.
- `/validate-otp`: (POST) It is used for otp validation.
- `/user`: (GET) It is used get the user information from email.
- `/forgot-password`: (POST) It is used for password retrieval. (I still have issues here)
- `/change-password`: (POST) It is used for password change.

## Usage

`/signup`

Request Body:
```json
{
  "email": "[YOUR EMAIL]",
  "firstname": "John",
  "lastname": "Doe",
  "password": "E2rk2FlYF",
  "isGoogleSignIn": false
}
```
You will get a mail in your email provided.

`/validate-otp`

Request Params:
```json
{
  "email": "[YOUR EMAIL]",
  "otp": "[OTP from your mail]"
}
```
Your otp will be verified.

`/login`

Request Body:
```json
{
  "email": "[YOUR EMAIL]",
  "password": "E2rk2FlYF",
  "isGoogleSignIn": false
}
```
You will be logged in