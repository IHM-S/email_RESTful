package Service;

import APIModel.Email;
import APIModel.ResponseMessage;
import Constant.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Component("SendGridAPICaller")
public class SendGridAPICaller implements  APICaller {

    /**
     * use sendgrid web api to send message according to the information provided by user
     * @param email, contain all necessary information about the email user want to send
     * @return ResponseMessage indicates if the message is successfully sent by sendgrid
     */
    @Override
    public ResponseMessage sendEmail(Email email) {
        // construct the json request
        JSONArray to = new JSONArray();
        for(String toEmail : email.getTo()) to.put(new JSONObject().put("email", toEmail));
        JSONArray personalization = new JSONArray();

        JSONObject mailingList = new JSONObject().put("to", to);
        if(email.getCc() != null){
            JSONArray cc = new JSONArray();
            for(String ccEmail : email.getCc()) cc.put(new JSONObject().put("email", ccEmail));
            mailingList.put("cc", cc);
        }

        if(email.getBcc() != null){
            JSONArray bcc = new JSONArray();
            for(String bccEmail : email.getBcc()) bcc.put(new JSONObject().put("email", bccEmail));
            mailingList.put("bcc", bcc);
        }
        personalization.put(mailingList);

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("personalizations", personalization);

        jsonRequest.put("from", new JSONObject().put("email", Constants.FROM_EMAIL).put("name", Constants.FROM_NAME));

        jsonRequest.put("subject", email.getSubject());

        JSONArray content = new JSONArray();
        content.put(new JSONObject().put("type", "text/plain").put("value", email.getText()));
        jsonRequest.put("content", content);

        try {
            HttpResponse<JsonNode> response = Unirest.post("https://api.sendgrid.com/v3/mail/send")
                    .header("authorization", "Bearer " + Constants.SENDGRID_API_KEY)
                    .header("content-type", "application/json")
                    .body(jsonRequest.toString())
                    .asJson();
            if(response.getStatus() == 202){ // indicate the server accepted the request
                return new ResponseMessage(true, "Your emails have been sent successfully through sendgrid.");
            } else {
                return new ResponseMessage(false, "Your emails have not been sent successfully through sendgrid.");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return new ResponseMessage(false, "While constructing HTTP request for sendgrid something went wrong");
        }

    }
}