This is a Springboot application that would have a POST endpoint which delegates to the service layer to pick  a third party mail service either SendGrid or Mailgun
Currently uses a basic logic to determine which mailclient to pick and invokes the 3rd API via an API client (Using Sendgrid's internal http client for Sendgrid and OkHttp for Mailgun)

Technologies
Springboot
Gradle
Java8
Sendgrid client library
OkHttp Http client library

Please note you would require to register with Mailgun and SendGrid and retrive an API Key and place it in application.properties before invoking!


TODO or features to implement
1) Implement the controller and controller advice
2) Handling an exception handling service / controller advice to handle the exceptions thrown from the service
3) Validation of the incoming payload
4) Unit/component testing
