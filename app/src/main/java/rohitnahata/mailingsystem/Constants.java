package rohitnahata.mailingsystem;

/**
 * Created by Rohit on 04/09/2016.
 */
public class Constants {


    public static final String DATABASE_NAME = "SQLiteExample.db";
    public static final int DATABASE_VERSION = 1;
    public static final String MAIL_TABLE_NAME = "previousMails";
    public static final String MAIL_ROW_ID = "_id";
    public static final String MAIL_SUBJECT = "subject";
    public static final String MAIL_BODY = "body";
    public static final String RECIPIENT_LIST = "recipients";
    public static final String ATTACHMENT_LIST ="attachments";

    public static final String CREATE_TABLE_MAILS="CREATE TABLE " + MAIL_TABLE_NAME + "(" +
            MAIL_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MAIL_SUBJECT + " TEXT, " +
            RECIPIENT_LIST+" TEXT, "+
            ATTACHMENT_LIST+" TEXT, "+
            MAIL_BODY + " TEXT)";
}
