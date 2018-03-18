// provide abstraction for api caller

package Service;

import APIModel.Email;
import APIModel.ResponseMessage;

public interface APICaller {
    ResponseMessage sendEmail(Email email);
}
