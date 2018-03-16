package Service;

import APIModel.Email;
import APIModel.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EmailService")
public class EmailServiceImp implements EmailService {

    @Autowired
    MailGunAPICaller mailgunCaller = new MailGunAPICaller();

    @Autowired
    SendGridAPICaller sendgridCaller = new SendGridAPICaller();

    @Override
    public ResponseMessage sendEmail(Email email) {
        return sendgridCaller.sendEmail(email);
    }
}
