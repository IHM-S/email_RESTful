package APIController;

import org.springframework.web.bind.annotation.RestController;


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


    /*
    sample post input
    {
    ""

    }
     */
//    @PostMapping("/send")
//    public
}
