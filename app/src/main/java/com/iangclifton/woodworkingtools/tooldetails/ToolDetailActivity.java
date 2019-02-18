package com.iangclifton.woodworkingtools.tooldetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.iangclifton.woodworkingtools.CaptionedImageView;
import com.iangclifton.woodworkingtools.R;
import com.iangclifton.woodworkingtools.Tool;

// TODO: 14.02.2019 ckeck Action Bar NPE
public class ToolDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TOOL = "com.iangclifton.woodworkingtools.TOOL";

    private static final String ERROR_MSG =
            "Tool not available as extra; use startActivity when creating an activity instance";

    public static void startActivity(Activity activity, Tool tool, Bundle activityOptions) {

        final Intent intent = new Intent(activity, ToolDetailActivity.class);
        intent.putExtra(EXTRA_TOOL, tool);
        ActivityCompat.startActivity(activity, intent, activityOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_detail);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final Tool tool = getIntent().getParcelableExtra(EXTRA_TOOL);

        if (tool == null) {
            throw new IllegalStateException(ERROR_MSG);
        }

        final CaptionedImageView captionedImageView = findViewById(R.id.hero_image);
        captionedImageView.getImageView().setSquare(false);
        captionedImageView.getTextView().setText(tool.getName());
        captionedImageView.setImageResource(tool.getImageResourceId());

        findAndSetTextView(R.id.price, tool.getPrice());
        findAndSetTextView(R.id.detail_0, tool.getDetails()[0]);
        findAndSetTextView(R.id.detail_1, tool.getDetails()[1]);
        findAndSetTextView(R.id.detail_2, tool.getDetails()[2]);
        findAndSetTextView(R.id.description, tool.getDescription());
    }

    private void findAndSetTextView(int id, String text) {
        final TextView textView = findViewById(id);
        textView.setText(text);
    }
}