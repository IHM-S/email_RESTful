package APIModel;

import java.util.ArrayList;

public class Email {
    private ArrayList<String> to;
    private ArrayList<String> CC;
    private ArrayList<String> BCC;
    private String subject;
    private String text;

    public Email(ArrayList<String> to, ArrayList<String> CC, ArrayList<String> BCC, String subject, String text){
        this.to = to;
        this.CC = CC;
        this.BCC = BCC;
        this.subject = subject;
        this.text = text;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getCC() {
        return CC;
    }

    public void setCC(ArrayList<String> CC) {
        this.CC = CC;
    }

    public ArrayList<String> getBCC() {
        return BCC;
    }

    public void setBCC(ArrayList<String> BCC) {
        this.BCC = BCC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
