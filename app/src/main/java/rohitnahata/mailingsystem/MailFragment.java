package rohitnahata.mailingsystem;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import rohitnahata.mailingsystem.Models.PreviousMailModel;
import rohitnahata.mailingsystem.Models.StudentDetailsModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailFragment extends Fragment {


    AutoCompleteTextView txtSearch;
    StudentSuggestionAdapter adapter;
    ArrayList<StudentDetailsModel> className;
    ArrayList<StudentDetailsModel> studentDetailsModelList;
    int flag = 0;
    private View view;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private String timeSent;
    private ArrayList<Uri> filepath = null;
    private String message;
    private String subject;
    private ArrayList<String> strFilePath;
    private ArrayList<String> strFilePathString;
    private String[] temp;
    private ArrayList<String> tempEmail;
    private String allEmails;
    private InternetAddress[] recipientAddress;
    private int emailFieldsUsed;
    private LinearLayout extraEmailsLayout;
    private LinearLayout extraAttachmentsLayoutRoot;
    private LinearLayout extraAttachmentsLayout;
    private Button addEmail;
    private ArrayList<PreviousMailModel> previousMailModelList;


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previousMailModelList = ((App) getContext().getApplicationContext()).getPreviousMailModelList();
        className = ((App) getContext().getApplicationContext()).getClassName();
        studentDetailsModelList = ((App) getContext().getApplicationContext()).getStudentDetailsModelList();
        filepath = new ArrayList<>();
        strFilePath = new ArrayList<>();
        strFilePathString = new ArrayList<>();
        emailFieldsUsed = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_mail, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    private void add_attachment() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, true);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(i, 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        tempEmail = new ArrayList<>();
        allEmails = "";
        ArrayList<StudentDetailsModel> studentDetailsModelListForSuggestions = new ArrayList<>(className);
        studentDetailsModelListForSuggestions.addAll(studentDetailsModelList);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);
        addEmail = (Button) view.findViewById(R.id.addEmail);
        extraEmailsLayout = (LinearLayout) view.findViewById(R.id.extraEmailsLayout);
        extraEmailsLayout.setVisibility(View.VISIBLE);
        extraAttachmentsLayoutRoot = (LinearLayout) view.findViewById(R.id.attachmentFragementMailRoot);
//        extraEmailsLayout.setVisibility(View.VISIBLE);
        extraAttachmentsLayout = (LinearLayout) view.findViewById(R.id.attachmentFragementMail);
        Button buttonSend = (Button) view.findViewById(R.id.buttonSend);
        txtSearch = (AutoCompleteTextView) view.findViewById(R.id.editTextEmail);
        adapter = new StudentSuggestionAdapter(getContext(), R.layout.activity_main, R.id.lbl_name, studentDetailsModelListForSuggestions);
        txtSearch.setAdapter(adapter);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMailButton();
            }
        });

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
                    AutoCompleteTextView tv = new AutoCompleteTextView(getContext());
                    tv.setLayoutParams(lparams);
                    tv.setBackground(null);
                    tv.setHint("To");
                    tv.setThreshold(1);
                    tv.setAdapter(adapter);
                    tv.setSingleLine();
                    extraEmailsLayout.addView(tv);
                    setEmailId(tv, emailFieldsUsed);
                    tv.requestFocus();
                }
            }

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Paused");
    }


    public   boolean isValidEmail(CharSequence target) {
        return /*!TextUtils.isEmpty(target) &&  */android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void sendEmail() {
        Log.i("mail", "mailMe");
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

        //Executing sendmail to send email....
        sm.execute();
        txtSearch.setText("");
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

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //Send Mail button
    public void sendMailButton() {

        String email = txtSearch.getText().toString().trim();
        subject = editTextSubject.getText().toString().trim();
        message = editTextMessage.getText().toString().trim();
        int recipientsPresent = 1;
//        email = email.replaceAll(",",";");

        if (!haveNetworkConnection()) {
            Snackbar snackbar = Snackbar
                    .make(view.findViewById(R.id.mailLinearLayout), "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendMailButton();
                        }
                    });

            snackbar.show();
            return;
        }


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
            tempEmail.add(email);
            allEmails=email;
        } else if (!isValidEmail(email) && email.indexOf(';') == -1) {
            flag = 0;
            for (StudentDetailsModel name : className) {
                if (Objects.equals(email, name.getClassroom())) {
                    flag = 1;
                    allEmails += email + "\n";
                    for (StudentDetailsModel temp : studentDetailsModelList) {

                        if (Objects.equals(temp.getClassroom(), email)) {
                            tempEmail.add(temp.getEmail_id());
//                            allEmails+="\n"+temp.getEmail_id();
                        }
                    }
                    break;
                }
                if (flag == 1)
                    break;
            }
            if (flag == 0) {
                Toast.makeText(getContext(), "Email Address is not valid", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(emailFieldsUsed>0){
            String aTemp;
            int j = 0;//used for actual temp array. a field might be left blank
            for(int i=1;i<=emailFieldsUsed;i++){
                aTemp=storeAddressInArray(i);
                if(i==emailFieldsUsed){
                    if(!isValidEmail(aTemp)){
                        flag = 0;
                        for (StudentDetailsModel name : className) {
                            if (Objects.equals(aTemp, name.getClassroom())) {
                                allEmails += aTemp + "\n";
                                flag = 1;
                                for (StudentDetailsModel temp : studentDetailsModelList) {
                                    if (Objects.equals(temp.getClassroom(), aTemp)) {
                                        tempEmail.add(temp.getEmail_id());
                                    }
                                }
                                break;
                            }
                            if (flag == 1)
                                break;
                        }
                        //recipientAddress=new InternetAddress[emailFieldsUsed];
                        break;
                    }
                }
                if (!isValidEmail(aTemp) && aTemp.indexOf(';') == -1) {
                    flag = 0;
                    for (StudentDetailsModel name : className) {
                        if (Objects.equals(aTemp, name.getClassroom())) {
                            flag = 1;
                            allEmails += email + "\n";
                            for (StudentDetailsModel temp : studentDetailsModelList) {
                                if (Objects.equals(temp.getClassroom(), aTemp)) {
                                    tempEmail.add(temp.getEmail_id());
                                }
                            }
                            break;
                        }
                        if (flag == 1)
                            break;
                    }
                    if (flag == 0) {
                        Toast.makeText(getContext(), "Email Address " + i + " is not valid", Toast.LENGTH_LONG).show();
                        recipientsPresent = 0;
                        break;
                    }
                }
                else {
                    j++;
                    allEmails += ",\n" + aTemp;
                    tempEmail.add(aTemp);
                }

            }

        }
        recipientAddress = new InternetAddress[tempEmail.size()];
        temp = tempEmail.toArray(new String[tempEmail.size()]);

        if(filepath.size()>0){
            for(Uri singleFilePath:filepath){
                String tp = String.valueOf(singleFilePath);
                tp = tp.substring(7);
                strFilePath.add(tp);
                strFilePathString.add(tp.substring(tp.lastIndexOf("/")).substring(1));
            }
        }

        if(recipientsPresent !=0) {
            Calendar c = Calendar.getInstance();
            int min;
            int hr;
            String minute;
            String hour;
            min = c.get(Calendar.MINUTE);
            hr = c.get(Calendar.HOUR_OF_DAY);
            if (min < 10)
                minute = "0" + min;
            else
                minute = String.valueOf(min);

            if (hr < 10)
                hour = "0" + hr;
            else
                hour = String.valueOf(hr);

            timeSent = hour + ":" + minute;
            emailFieldsUsed = 0;
            save_to_database();
            sendEmail();
        }
    }

    private void save_to_database() {
        String recipient, subject, body;
        subject = String.valueOf(editTextSubject.getText());
        body = String.valueOf(editTextMessage.getText());
        recipient=allEmails;
        if (strFilePathString.size() == 0)
            strFilePathString = null;
        PreviousMailModel previousMailModel = new PreviousMailModel(recipient, subject, body, timeSent, strFilePathString);
        previousMailModelList.add(previousMailModel);
        ((App) this.getActivity().getApplication()).setPreviousMailModelList(previousMailModelList);
        if (extraAttachmentsLayout.getChildCount() > 0)
            extraEmailsLayout.removeAllViews();
        extraAttachmentsLayoutRoot.setVisibility(View.INVISIBLE);
    }

    //FilePicker
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
//                            filepath=uri;
                            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            TextView tv = new TextView(getContext());
                            String tp = String.valueOf(uri).substring(7);
                            tv.setText(tp.substring(tp.lastIndexOf("/")).substring(1));
                            tv.setPadding(0, 5, 0, 10);
                            tv.setLayoutParams(lparams);
                            tv.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            extraAttachmentsLayout.addView(tv);
                        }
                    }
                    // For Ice Cream Sandwich
                }
            }
        }
    }


    private void clearFields(int emailField){
        switch (emailField) {

            case 1:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView1)).setText("");
                break;
            case 2:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView2)).setText("");
                break;
            case 3:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView3)).setText("");
                break;
            case 4:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView4)).setText("");
                break;
            case 5:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView5)).setText("");
                break;
            case 6:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView6)).setText("");
                break;
            case 7:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView7)).setText("");
                break;
            case 8:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView8)).setText("");
                break;
            case 9:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView9)).setText("");
                break;
            case 10:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView10)).setText("");
                break;
            case 11:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView11)).setText("");
                break;
            case 12:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView12)).setText("");
                break;
            case 13:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView13)).setText("");
                break;
            case 14:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView14)).setText("");
                break;
            case 15:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView15)).setText("");
                break;
            case 16:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView16)).setText("");
                break;
            case 17:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView17)).setText("");
                break;
            case 18:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView18)).setText("");
                break;
            case 19:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView19)).setText("");
                break;
            case 20:
                ((AutoCompleteTextView) getView().findViewById(R.id.emailTextView20)).setText("");
                break;
        }

    }

    private String storeAddressInArray(int emailField) {
        String emailAddress;
        switch (emailField) {
            case 1:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView1)).getText());
                break;
            case 2:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView2)).getText());
                break;
            case 3:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView3)).getText());
                break;
            case 4:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView4)).getText());
                break;
            case 5:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView5)).getText());
                break;
            case 6:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView6)).getText());
                break;
            case 7:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView7)).getText());
                break;
            case 8:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView8)).getText());
                break;
            case 9:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView9)).getText());
                break;
            case 10:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView10)).getText());
                break;
            case 11:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView11)).getText());
                break;
            case 12:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView12)).getText());
                break;
            case 13:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView13)).getText());
                break;
            case 14:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView14)).getText());
                break;
            case 15:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView15)).getText());
                break;
            case 16:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView16)).getText());
                break;
            case 17:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView17)).getText());
                break;
            case 18:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView18)).getText());
                break;
            case 19:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView19)).getText());
                break;
            case 20:
                emailAddress = String.valueOf(((AutoCompleteTextView) getView().findViewById(R.id.emailTextView20)).getText());
                break;
            default:
                emailAddress="";
        }
        return emailAddress;
    }


    private void setEmailId(AutoCompleteTextView tv, int emailFieldsUsed) {
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
