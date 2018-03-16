package Service;

import APIModel.Email;
import APIModel.ResponseMessage;

public interface EmailService {
    public ResponseMessage sendEmail(Email email);
}
