package APIModel;

public class ResponseMessage {
    private Boolean succeed;
    private String message;

    public ResponseMessage(){
        super();
    }

    public ResponseMessage(Boolean succeed, String message){
        this.succeed = succeed;
        this.message = message;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
