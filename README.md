# email RESTful

* [Guidelines](#guidelines)
* [Features & Limitations](#features--limitations)
* [Project Structure](#project-structure)
* [RESTful URL](#restful-url)
* [Responses](#responses)
* [Error Handling](#error-handling)
* [Request & Response Examples](#request--response-examples)

## Guidelines

The purpose of the project is a RESTful API which provide an abstraction between two different email service providers (mailgun and sendgrid).
if one of the services goes down, your service can quickly failover to a different provider without affecting your customers.

## Features & Limitations
* The API cater for multiple email recipients, CCs and BCCs.
* Only support plain text content.
* No authentication is required.
* No 3rd party client library are used to integrate with MailGun or SendGrid (email service providers).
* Because I am using mailgun free account, therefore I can only send email to those people are in my verified users list. But there is no limitation for sendgrid.
* By default the API uses mailgun, if mailgun fail, it will try to send email by sendgrid.

## Project Structure
* Project used Spring boot to help setup the project and it uses Spring REST structure.
* Controller class is used to handle API request and dispatch request to correspond service.
* Email and ResponseMessage classes are used to serve the purpose of data transfer object between different function calls and they are also used as return objects so Spring will receive the return object and return a corresponded JSON object to user.
* APICaller and EmailService are interface classes to reduce coupling.
* EmailServiceImp is the implementation of EmailService, it is the project's service layer, it gets the request from user and does the service it provides and also it does the error checking of user input.
* MailGunAPICaller and SendGridAPICaller are the implementation of APICaller, they are helper class for the EmailService, they actually connect with corresponded email service provider and try send email through them.
* Constant is the class stores all the constants used in the projects, such as API key, from email address, from user name, etc.

## RESTful URL
This API only provide one API call:
* Post http://\[IP address\]/send
```
{
    "to"      : [],
    "cc"      : [],
    "bcc"     : [],
    "subject" : "",
    "text"    : ""
}

```

## Responses
The Json structure of the response is:
```aild
{
    "succeed": ture/false,
    "message": "message response"
}
```

## Error Handling
* For different input succeed status could be true or false.
* For different input messages could be "Invalid input, because...", "your message has been successfully sent", etc.

|  succeed   |     message                                                                                              |
| ---------- | ---------------                                                                                          |
| true/false | Your emails have/have not been sent successfully through mailgun/sendgrid.                               | 
|   false    | While constructing HTTP request for mailgun/sendgrid something went wrong                                |
|   false    | Invalid input email, you must have at least one email in to list.                                        |
|   false    | Invalid input email, you must have a valid email text message defined.                                   |
|   false    | Invalid input email, you must have a valid email subject defined.                                        |
|   false    | emailAddress + already exist in to list, please make sure email address is unique across to, cc and bcc. |
|   false    | emailAddress + email address is not valid, please make sure all email addresses are valid.               |
|   false    | Invalid input email, for either to, cc and bcc you can't have number of recipients larger than 1000.     |

## Request & Response Examples

Request 1:
```
{
    "to" : ["yun553966858@gmail.com"],
    "subject" : "new day",
    "text" : "hello"
}
```
Response 1:
```
{
    "succeed": true,
    "message": "Your emails have been sent successfully through mailgun."
}
```

Request 2:
```
{
    "to" : ["yun553966858@gmail.com"],
    "cc" : [ "shiyun.zhang@qq.com"],
    "bcc": ["shiyun.zhangsyz@gmail.com"],
    "text" : " ",
    "subject" : " "
}
```
Response 2:
```
{
    "succeed": true,
    "message": "Your emails have been sent successfully through mailgun."
}
```

Request 3:
```
{
    "to" : ["yun553966858@gmail.com"],
    "cc" : [ "shiyun.zhang@qq.com"],
    "bcc": ["shiyun.zhangsyz@gmail.com"],
    "text" : " ",
    "subject" : " "
}
```
Response 3:
```
{
    "succeed": true,
    "message": "Your emails have been sent successfully through mailgun."
}
```

Request 4:
```
{
    "to" : ["yun553966858@gmail.com"],
    "cc" : [ "shiyun.zhang@qq.com"],
    "bcc": ["shiyun.zhangsyz@gmail.com"],
    "text" : "asdz"
}
```
Response 4:
```
{
    "succeed": false,
    "message": "Invalid input email, you must have a valid email subject defined."
}
```

Request 5:
```
{
    "to" : ["yun553966858@gmail.com"],
    "cc" : [ "asdasdasdasd"],
    "bcc": ["shiyun.zhangsyz@gmail.com"],
    "text" : "asdz",
    "subject" : "asdas"
}
```
Response 5:
```
{
    "succeed": false,
    "message": "asdasdasdasd email address is not valid, please make sure all email addresses are valid."
}
```

Request 5:
```
{
    "to" : ["yun553966858@gmail.com"],
    "cc" : [ "shiyun.zhang@qq.com", "shiyun.zhangsyz@gmail.com"],
    "bcc": ["shiyun.zhangsyz@gmail.com"],
    "text" : "asdz",
    "subject" : "asdas"
}
```
Response 5:
```
{
    "succeed": false,
    "message": "asdasdasdasd email address is not valid, please make sure all email addresses are valid."
}
```