// main controller class handles all API calls

package APIController;

import APIModel.Email;
import APIModel.ResponseMessage;
import Service.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    // logical layer handle all the email sending
    @Autowired
    EmailService emailService = new EmailServiceImp();

    @PostMapping("/send")
    @JsonIgnore
    public ResponseMessage Send(@RequestBody Email email) {
        return emailService.send(email);
    }
}
