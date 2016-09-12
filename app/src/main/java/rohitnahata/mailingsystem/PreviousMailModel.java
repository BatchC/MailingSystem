package rohitnahata.mailingsystem;

import java.util.ArrayList;

/**
 * Created by Rohit on 06/09/2016.
 */
public class PreviousMailModel {
    String strRecipients;
    String strSubject;
    String strBody;
    String strTime_sent;
    ArrayList<String> strAttachments;
    int intId;

    public PreviousMailModel() {
    }

    public PreviousMailModel(String strRecipients, String strSubject, String strBody, String strTime_sent,ArrayList<String> strAttachments) {
        this.strRecipients = strRecipients;
        this.strSubject = strSubject;
        this.strBody = strBody;
        this.strTime_sent = strTime_sent;
        this.strAttachments = strAttachments;
//        this.intId = intId;
    }

    public String getStrSubject() {
        return strSubject;
    }

    public void setStrSubject(String strSubject) {
        this.strSubject = strSubject;
    }

    public String getStrRecipients() {
        return strRecipients;
    }

    public void setStrRecipients(String strRecipients) {
        this.strRecipients = strRecipients;
    }

    public String getStrBody() {
        return strBody;
    }

    public void setStrBody(String strBody) {
        this.strBody = strBody;
    }

    public String getStrTime_sent() {
        return strTime_sent;
    }

    public void setStrTime_sent(String strTime_sent) {
        this.strTime_sent = strTime_sent;
    }

    public ArrayList<String> getStrAttachments() {
        return strAttachments;
    }

    public void setStrAttachments(ArrayList<String> strAttachments) {
        this.strAttachments = strAttachments;
    }

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }
}
