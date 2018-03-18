// service layer abstraction

package Service;

import APIModel.Email;
import APIModel.ResponseMessage;

public interface EmailService {
    ResponseMessage send(Email email);
}
