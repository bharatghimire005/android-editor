package com.example.bharatghimire.androideditor.displayhtml;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bharatghimire.androideditor.R;
import com.example.bharatghimire.androideditor.databinding.ActivityDisplayHtmlBinding;

public class DisplayHtmlActivity extends AppCompatActivity {

    public static final String EXTRA_HTML = "extraHtml";
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDisplayHtmlBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_display_html);
        if (getIntent() != null) {
            binding.tvDisplayHtml.setText(getIntent().getStringExtra(EXTRA_HTML));
        }

    }
}
