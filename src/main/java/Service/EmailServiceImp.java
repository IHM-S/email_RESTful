// actual implementation class for email service

package Service;

import APIModel.Email;
import APIModel.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EmailService")
public class EmailServiceImp implements EmailService {

    @Autowired
    APICaller mailgunCaller = new MailGunAPICaller();

    @Autowired
    APICaller sendgridCaller = new SendGridAPICaller();

    /**
     * Service that try to use two mail service provider to deliver user's email, if one fail, try use another one to deliver the message
     * @param email, Java Bean which contain information about the email user want to send
     * @return a ResponseMessage which represent if the email is successfully sent
     */
    @Override
    public ResponseMessage sendEmail(Email email) {
        ResponseMessage mailgunResponse = mailgunCaller.sendEmail(email);
        if(mailgunResponse.getSucceed()){
            return mailgunResponse;
        } else {
            ResponseMessage sendgridResponse = sendgridCaller.sendEmail(email);
            return new ResponseMessage(mailgunResponse, sendgridResponse);
        }
    }
}
