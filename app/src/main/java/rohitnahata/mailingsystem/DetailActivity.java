package rohitnahata.mailingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {

    TextView recipients,body,subject,time_sent,textViewAttachment;
    ImageView imageView;
    String letter;
    ListView listView;
    int color;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");  // providing compatibility to all the versions


        Intent intent=getIntent();
        String recipientsStr=intent.getStringExtra("RECIPIENTS");
        String bodyStr=intent.getStringExtra("BODY");
        String subjectStr=intent.getStringExtra("SUBJECT");
        String time_sentStr=intent.getStringExtra("TIME");
        ArrayList<String> attachmentsStr=intent.getStringArrayListExtra("ATTACHMENTS");



        recipients=(TextView)findViewById(R.id.recipientDetails);
        body=(TextView)findViewById(R.id.bodyDetails);
        subject=(TextView)findViewById(R.id.detailSubject);
        time_sent=(TextView)findViewById(R.id.timeDetails);
        imageView=(ImageView)findViewById(R.id.nameImageDetail);
        textViewAttachment=(TextView)findViewById(R.id.textViewAttachment);
        listView=(ListView)findViewById(R.id.listViewAttachments);

        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                attachmentsStr);
        if(attachmentsStr!=null) {
            textViewAttachment.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);
        }
        letter = (String.valueOf(recipientsStr.charAt(0))).toUpperCase();
        color=generator.getColor(letter);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter,color /*generator.getRandomColor()*/);
        imageView.setImageDrawable(drawable);
        recipients.setText(recipientsStr);
        body.setText(bodyStr);
        subject.setText(subjectStr);
        time_sent.setText(time_sentStr);

    }

}
