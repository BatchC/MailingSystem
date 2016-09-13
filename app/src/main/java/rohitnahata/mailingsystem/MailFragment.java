package rohitnahata.mailingsystem;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailFragment extends Fragment implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private String timeSent;
    private List<Uri> filepath=null;
    private String message;
    private String subject;
    private ArrayList<String> strFilePath;
    private ArrayList<String> strFilePathString;
    private String[] temp;
    private String allEmails;
    private InternetAddress[] recipientAddress;
    private int emailFieldsUsed;
    private LinearLayout extraEmailsLayout;
    private LinearLayout extraAttachmentsLayoutRoot;
    private LinearLayout extraAttachmentsLayout;

    private Button addEmail;

    public MailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_mail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.attachment:
                add_attachment();
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void add_attachment() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, true);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                         ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filepath=new ArrayList<>();
        strFilePath=new ArrayList<>();
        strFilePathString=new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        setHasOptionsMenu(true);
        emailFieldsUsed=0;
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextMessage = (EditText)view.findViewById(R.id.editTextMessage);
        addEmail=(Button)view.findViewById(R.id.addEmail);
        extraEmailsLayout=(LinearLayout)view.findViewById(R.id.extraEmailsLayout);
        extraEmailsLayout.setVisibility(View.VISIBLE);
        extraAttachmentsLayoutRoot = (LinearLayout) view.findViewById(R.id.attachmentFragementMailRoot);
        extraEmailsLayout.setVisibility(View.VISIBLE);
        extraAttachmentsLayout = (LinearLayout) view.findViewById(R.id.attachmentFragementMail);

        Button buttonSend = (Button) view.findViewById(R.id.buttonSend);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        buttonSend.setOnClickListener(this);

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailFieldsUsed==20) {
                    Toast.makeText(getContext(), "No more email ids can be added", Toast.LENGTH_SHORT).show();
                    addEmail.setEnabled(false);
                }
                else {
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    emailFieldsUsed++;
                    EditText tv = new EditText(getContext());
                    tv.setLayoutParams(lparams);
//                  tv.setHint("Email");
                    extraEmailsLayout.addView(tv);
                    setEmailId(tv, emailFieldsUsed);
                    tv.requestFocus();
                }
            }

        });

        return view;
    }

    //Filepicker
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        extraAttachmentsLayoutRoot.setVisibility(View.VISIBLE);
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            filepath.add(uri);
                            System.out.println("else");
                            System.out.println(uri);
//                            filepath=uri;
                            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            TextView tv = new TextView(getContext());
                            String tp = String.valueOf(uri).substring(7);
                            tv.setText(tp.substring(tp.lastIndexOf("/")).substring(1));
                            tv.setPadding(0, 5, 0, 10);
                            tv.setLayoutParams(lparams);
//
//                            if(filepath.size()>0){
//                                for(Uri singleFilePath:filepath){
//                                    String tp = String.valueOf(singleFilePath);
//                                    tp = tp.substring(7);
//                                    strFilePath.add(tp);
//                                    System.out.println(tp.substring(tp.lastIndexOf("/")));
//                                    strFilePathString.add(tp.substring(tp.lastIndexOf("/")).substring(1));
//                                }
//                            }
                            extraAttachmentsLayout.addView(tv);
                        }
                    }
                    // For Ice Cream Sandwich
                } 
            } else {
                Uri uri = data.getData();
                System.out.println("else"+uri);

                // Do something with the URI
            }
        }
    }


    public   boolean isValidEmail(CharSequence target) {
        return /*!TextUtils.isEmpty(target) &&  */android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void sendEmail() {
        Log.i("mail","mailme");
        //Getting content for email
        SendMail sm;

        //Creating SendMail object
        int counter = 0;
        for (String recipient : temp) {
            if(counter==temp.length-1&&recipient==null){
                break;
            }
            try {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
            } catch (AddressException e) {
                e.printStackTrace();
            }
            counter++;
        }

        sm=new SendMail(getContext(),recipientAddress,subject,message,strFilePath,strFilePathString);

        //Executing sendmail to send email :p
        sm.execute();
        editTextEmail.setText("");
        editTextMessage.setText("");
        editTextSubject.setText("");
        filepath=new ArrayList<>();
        strFilePath=new ArrayList<>();
        strFilePathString=new ArrayList<>();
        extraEmailsLayout.setVisibility(View.GONE);
        for(int i=0;i<emailFieldsUsed;i++){
            clearFields(i);
        }
    }
    //Send Mail button
    @Override
    public void onClick(View v) {
        String email = editTextEmail.getText().toString().trim();
        subject = editTextSubject.getText().toString().trim();
        message = editTextMessage.getText().toString().trim();
        int recipientsPresent = 1;
//        email = email.replaceAll(",",";");
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getContext(),"Enter an email id",Toast.LENGTH_SHORT).show();
            return;
        }
        else  if(TextUtils.isEmpty(subject)){
            Toast.makeText(getContext(),"Enter a subject",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(message)){
            Toast.makeText(getContext(),"Enter a message",Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.indexOf(';')!=-1){
            Toast.makeText(getContext(),"Enter a vaid email id",Toast.LENGTH_SHORT).show();
            return;

        }
//        if(email.indexOf(';')!=-1)
//        {
//            recipientsPresent =2;
//            temp = email.split(";");
//            for (String aTemp : temp) {
//                if (!isValidEmail(aTemp)&& !TextUtils.isEmpty(aTemp)) {
//                    Toast.makeText(getContext(), "Enter valid email id", Toast.LENGTH_SHORT).show();
//                    recipientsPresent =0;
//                    break;
//                }
//            }
//            recipientAddress = new InternetAddress[temp.length];
//        }
        if(isValidEmail(email)&& email.indexOf(';')==-1)//basically recipients present is equal to 1
        {
            System.out.println(email);
            recipientAddress=new InternetAddress[emailFieldsUsed+1];
            temp=new String[emailFieldsUsed+1];
            allEmails=email;
            temp[0]= email;
        }

        if(emailFieldsUsed>0){
            String aTemp;
            int j=0;//used for actual temp array. a filed might be left blank
            for(int i=1;i<=emailFieldsUsed;i++){
                aTemp=storeAddressInArray(i);
                if(i==emailFieldsUsed){
                    if(!isValidEmail(aTemp)){
                        recipientAddress=new InternetAddress[emailFieldsUsed];
                        break;
                    }
                }
                if(!isValidEmail(aTemp)&&aTemp.indexOf(';')==-1){
                    Toast.makeText(getContext(),"Email Address "+i+" is not valid",Toast.LENGTH_SHORT).show();
                    recipientsPresent=0;
                    break;
                }
                else {
                    j++;
                    allEmails+=", "+aTemp;
                    temp[j]=aTemp;
                }

            }
//            emailFieldsUsed=j;

        }

        if(filepath.size()>0){
            for(Uri singleFilePath:filepath){
                String tp = String.valueOf(singleFilePath);
                tp = tp.substring(7);
                strFilePath.add(tp);
                System.out.println(tp.substring(tp.lastIndexOf("/")));
                strFilePathString.add(tp.substring(tp.lastIndexOf("/")).substring(1));
            }
        }

        if(recipientsPresent !=0) {
            Calendar c = Calendar.getInstance();
            int minute = c.get(Calendar.MINUTE);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            timeSent = hour + ":" + minute;
            save_to_database();
            sendEmail();

        }
    }




    private void save_to_database() {

        String recipient, subject, body;
        subject = String.valueOf(editTextSubject.getText());
        body = String.valueOf(editTextMessage.getText());
        recipient=allEmails;
        PreviousMailModel previousMailModel = new PreviousMailModel(recipient, subject, body, timeSent, strFilePathString);
        System.out.println("llllllllll" + previousMailModel);
        if (extraAttachmentsLayout.getChildCount() > 0)
            extraEmailsLayout.removeAllViews();


    }

    private void clearFields(int emailField){
        switch (emailField) {

            case 1:
                ((EditText)getView().findViewById(R.id.emailTextView1)).setText("");
                break;
            case 2:
                ((EditText)getView().findViewById(R.id.emailTextView2)).setText("");
                break;
            case 3:
                ((EditText)getView().findViewById(R.id.emailTextView3)).setText("");
                break;
            case 4:
                ((EditText)getView().findViewById(R.id.emailTextView4)).setText("");
                break;
            case 5:
                ((EditText)getView().findViewById(R.id.emailTextView5)).setText("");
                break;
            case 6:
                ((EditText)getView().findViewById(R.id.emailTextView6)).setText("");
                break;
            case 7:
                ((EditText)getView().findViewById(R.id.emailTextView7)).setText("");
                break;
            case 8:
                ((EditText)getView().findViewById(R.id.emailTextView8)).setText("");
                break;
            case 9:
                ((EditText)getView().findViewById(R.id.emailTextView9)).setText("");
                break;
            case 10:
                ((EditText)getView().findViewById(R.id.emailTextView10)).setText("");
                break;
            case 11:
                ((EditText)getView().findViewById(R.id.emailTextView11)).setText("");
                break;
            case 12:
                ((EditText)getView().findViewById(R.id.emailTextView12)).setText("");
                break;
            case 13:
                ((EditText)getView().findViewById(R.id.emailTextView13)).setText("");
                break;
            case 14:
                ((EditText)getView().findViewById(R.id.emailTextView14)).setText("");
                break;
            case 15:
                ((EditText)getView().findViewById(R.id.emailTextView15)).setText("");
                break;
            case 16:
                ((EditText)getView().findViewById(R.id.emailTextView16)).setText("");
                break;
            case 17:
                ((EditText)getView().findViewById(R.id.emailTextView17)).setText("");
                break;
            case 18:
                ((EditText)getView().findViewById(R.id.emailTextView18)).setText("");
                break;
            case 19:
                ((EditText)getView().findViewById(R.id.emailTextView19)).setText("");
                break;
            case 20:
                ((EditText)getView().findViewById(R.id.emailTextView20)).setText("");
                break;











        }

    }

    private String storeAddressInArray(int emailField) {
        String emailAddress;
        switch (emailField) {
            case 1:
               emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView1)).getText());
                break;
            case 2:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView2)).getText());
                break;
            case 3:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView3)).getText());
                break;
            case 4:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView4)).getText());
                break;
            case 5:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView5)).getText());
                break;
            case 6:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView6)).getText());
                break;
            case 7:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView7)).getText());
                break;
            case 8:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView8)).getText());
                break;
            case 9:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView9)).getText());
                break;
            case 10:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView10)).getText());
                break;
            case 11:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView11)).getText());
                break;
            case 12:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView12)).getText());
                break;
            case 13:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView13)).getText());
                break;
            case 14:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView14)).getText());
                break;
            case 15:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView15)).getText());
                break;
            case 16:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView16)).getText());
                break;
            case 17:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView17)).getText());
                break;
            case 18:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView18)).getText());
                break;
            case 19:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView19)).getText());
                break;
            case 20:
                emailAddress= String.valueOf(((EditText)getView().findViewById(R.id.emailTextView20)).getText());
                break;
            default:
                emailAddress="";
        }
        return emailAddress;
    }


    private void setEmailId(EditText tv,int  emailFieldsUsed) {
        switch (emailFieldsUsed) {
            case 1:
                tv.setId(R.id.emailTextView1);
                break;
            case 2:
                tv.setId(R.id.emailTextView2);
                break;
            case 3:
                tv.setId(R.id.emailTextView3);
                break;
            case 4:
                tv.setId(R.id.emailTextView4);
                break;
            case 5:
                tv.setId(R.id.emailTextView5);
                break;
            case 6:
                tv.setId(R.id.emailTextView6);
                break;
            case 7:
                tv.setId(R.id.emailTextView7);
                break;
            case 8:
                tv.setId(R.id.emailTextView8);
                break;
            case 9:
                tv.setId(R.id.emailTextView9);
                break;
            case 10:
                tv.setId(R.id.emailTextView10);
                break;
            case 11:
                tv.setId(R.id.emailTextView11);
                break;
            case 12:
                tv.setId(R.id.emailTextView12);
                break;
            case 13:
                tv.setId(R.id.emailTextView13);
                break;
            case 14:
                tv.setId(R.id.emailTextView14);
                break;
            case 15:
                tv.setId(R.id.emailTextView15);
                break;
            case 16:
                tv.setId(R.id.emailTextView16);
                break;
            case 17:
                tv.setId(R.id.emailTextView17);
                break;
            case 18:
                tv.setId(R.id.emailTextView18);
                break;
            case 19:
                tv.setId(R.id.emailTextView19);
                break;
            case 20:
                tv.setId(R.id.emailTextView20);
                break;

        }
    }


}
