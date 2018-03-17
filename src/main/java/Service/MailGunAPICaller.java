package Service;

import APIModel.Email;
import APIModel.ResponseMessage;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.springframework.stereotype.Component;
import Constant.Constants;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component("APICaller")
public class MailGunAPICaller implements APICaller {

    /**
     * use mailgun web api to send message according to the information provided by user
     * @param email, email object contain all necessary information provided from user
     * @return ResponseMessage indicates if the message is successfully sent by mailgun
     */
    @Override
    public ResponseMessage sendEmail(Email email) {
        HttpRequestWithBody temp = Unirest.post("https://api.mailgun.net/v3/" + Constants.DOMIAN_NAME + "/messages")
                .basicAuth("api", Constants.MAILGUN_API_KEY)
                .queryString("from", Constants.FROM_EMAIL);
        for(String toEmail : email.getTo()){
            temp.queryString("to", toEmail);
        }
        for(String CCEmail : email.getCc()){
            temp.queryString("cc", CCEmail);
        }
        for(String BCCEmail : email.getBcc()){
            temp.queryString("bcc", BCCEmail);
        }
        temp.queryString("subject", email.getSubject());
        temp.queryString("text", email.getText());

        try {
            HttpResponse<JsonNode> request = temp.asJson();
            if(request.getBody().getObject().get("message").toString().contains("Queued. Thank you.")){
                return new ResponseMessage(true, "Your emails have been sent successfully through mailgun.");
            } else {
                return new ResponseMessage(false, "Your emails have not been sent successfully through mailgun.");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return new ResponseMessage(false, "While constructing HTTP request for mailgun something went wrong");
        }

    }
}