package rohitnahata.mailingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {

    private final ColorGenerator generator = ColorGenerator.MATERIAL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");  // providing compatibility to all the versions


        Intent intent=getIntent();
        String recipientsStr=intent.getStringExtra("RECIPIENTS");
        String bodyStr=intent.getStringExtra("BODY");
        String subjectStr=intent.getStringExtra("SUBJECT");
        String time_sentStr=intent.getStringExtra("TIME");
        ArrayList<String> attachmentsStr=intent.getStringArrayListExtra("ATTACHMENTS");


        TextView recipients = (TextView) findViewById(R.id.recipientDetails);
        TextView body = (TextView) findViewById(R.id.bodyDetails);
        TextView subject = (TextView) findViewById(R.id.detailSubject);
        TextView time_sent = (TextView) findViewById(R.id.timeDetails);
        ImageView imageView = (ImageView) findViewById(R.id.nameImageDetail);
        TextView textViewAttachment = (TextView) findViewById(R.id.textViewAttachment);

        String letter = (String.valueOf(recipientsStr.charAt(0))).toUpperCase();
        int color = generator.getColor(letter);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, color /*generator.getRandomColor()*/);
        imageView.setImageDrawable(drawable);
        recipients.setText(recipientsStr);
        body.setText(bodyStr);
        subject.setText(subjectStr);
        time_sent.setText(time_sentStr);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        if (attachmentsStr != null) {
            findViewById(R.id.textViewAttachment).setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            for (String attachments : attachmentsStr) {
                TextView tv = new TextView(this);
                tv.setText(attachments);
                tv.setPadding(20, 5, 0, 10);
                tv.setLayoutParams(lparams);
                linearLayout.addView(tv);
            }
        }
    }

}
