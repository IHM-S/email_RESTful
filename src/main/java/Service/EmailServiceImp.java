// actual implementation class for email service

package Service;

import APIModel.Email;
import APIModel.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

@Service("EmailService")
public class EmailServiceImp implements EmailService {

    @Autowired @Qualifier("MailGunAPICaller")
    APICaller mailgunCaller = new MailGunAPICaller();

    @Autowired @Qualifier("SendGridAPICaller")
    APICaller sendgridCaller = new SendGridAPICaller();

    /**
     * Service that try to use two mail service provider to deliver user's email, if one fail, try use another one to deliver the message
     * @param email, Java Bean which contain information about the email user want to send
     * @return a ResponseMessage which represent if the email is successfully sent
     */
    @Override
    public ResponseMessage send(Email email) {
        ResponseMessage msg = checkEmail(email);
        if(!msg.getSucceed()){
            return msg;
        }

        ResponseMessage mailgunResponse = mailgunCaller.sendEmail(email);
        if(mailgunResponse.getSucceed()){
            return mailgunResponse;
        } else {
            ResponseMessage sendgridResponse = sendgridCaller.sendEmail(email);
            return new ResponseMessage(mailgunResponse, sendgridResponse);
        }
    }

    /**
     * helper function which check if the given email object contain valid information
     * @param email, Java Bean which contain information about the email user want to send
     * @return ResponseMessage which contain boolean information about whether if the email object
     *         contains valid information, message contain the reasons
     */
    private ResponseMessage checkEmail(Email email){
        // to at least contains one email address
        if(email.getTo() == null || email.getTo().size() <= 0){
            return new ResponseMessage(false, "Invalid input email, you must have at least one email in to list.");
        }

        // to, cc, bcc length no bigger than 1000 (sendgrid requires)
        if(email.getTo().size() > 1000 || (email.getCc() != null && email.getCc().size() > 1000) || (email.getBcc() != null && email.getBcc().size() > 1000)){
            return new ResponseMessage(false, "Invalid input email, for either to, cc and bcc you can't have number of recipients larger than 1000.");
        }

        // don't have to have subject but has to have text for mailgun, but have to have both subject and text for sendgrid
        if(email.getText() == null || email.getText().equals("")){
            return new ResponseMessage(false, "Invalid input email, you must have a valid email text message defined.");
        } else if (email.getSubject() == null || email.getSubject().equals("")) {
            return new ResponseMessage(false, "Invalid input email, you must have a valid email subject defined.");
        }

        // check if all emails in to, cc and bcc are unique (sendgrid requires)
        // check all email addresses are valid
        HashMap<String, String> emailChecker = new HashMap<>();
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        ArrayList<String> emailList = new ArrayList<>(email.getTo());
        if(email.getCc() != null) emailList.addAll(email.getCc());
        if(email.getBcc() != null) emailList.addAll(email.getBcc());

        for(String emailAddress : emailList){
            if(emailChecker.containsKey(emailAddress)){
                return new ResponseMessage(false, emailAddress + " already exist in to list, please make sure email address is unique across to, cc and bcc.");
            } else {
                emailChecker.put(emailAddress, "");
                if(!emailAddress.matches(emailPattern)){
                    return new ResponseMessage(false, emailAddress + " email address is not valid, please make sure all email addresses are valid.");
                }
            }
        }

        return new ResponseMessage(true, "Valid input email");
    }
}
