package APIController;

import APIModel.Email;
import APIModel.ResponseMessage;
import Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
Purpose of the project is to provide an abstraction between two different email service providers
if one of the services goes down, your service can quickly failover to a different provider without affecting your custormers
 */

/*
RESTful API
- Solution should cater for multiple email recipients, CCs and BCCs but there is no need to suport HTML emial body type (plain text is OK)
- No authentication is required for the scope of this exercise
- No 3rd party client library should be used to integrate with MailGun or SendGrid, A simple HTTP client of choice can be used to handcraft HTTP requests to the email gateway services
 */

/*
Please upload your solution on GitHub and include a README.md file with info on how to build and deploy the backend
Please deply your solution somewhere(URL) for us to play with it
 */


@RestController
public class Controller {

    // logical layer handle all the email sending
    @Autowired
    EmailService emailService = new EmailServiceImp();

    /*
    sample post input
    {
    "to" : ["shiyun.zhangsyz@gmail.com", "yun553966858@gmail.com"],
    "CC" : ["yun553966858@gmail.com"],
    "BCC": ["shiyun.zhangsyz@gmail.com"],
    "subject" : "how are you",
    "text" : "long time no see, how are you"
    }
     */
    @PostMapping("/send")
    public ResponseMessage Send(@RequestBody Email email) {

//        System.out.println("To");
//        for(String e : email.getTo()){
//            System.out.println(e);
//        }
//        System.out.println();
//
//        System.out.println("CC");
//        for(String e : email.getCc()){
//            System.out.println(e);
//        }
//        System.out.println();
//
//        System.out.println("BCC");
//        for(String e : email.getBcc()){
//            System.out.println(e);
//        }
//        System.out.println();
//
//        System.out.println("Subject : " + email.getSubject());
//        System.out.println("Text : " + email.getText());


        return emailService.sendEmail(email);
    }
}
