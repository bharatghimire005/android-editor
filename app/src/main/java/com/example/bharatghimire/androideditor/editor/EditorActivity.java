package com.example.bharatghimire.androideditor.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bharatghimire.androideditor.R;
import com.example.bharatghimire.androideditor.customComponent.BGEditor;
import com.example.bharatghimire.androideditor.databinding.ActivityEditor2Binding;
import com.example.bharatghimire.androideditor.displayhtml.DisplayHtmlActivity;
import com.example.bharatghimire.androideditor.repository.local.DatabaseLoader;
import com.example.bharatghimire.androideditor.repository.local.DatabaseQueries;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {
    private static final int LOADER_SCHEDULAR = 1;
    public static final String SaveData = "saveData";
    public static final String MyPREFERENCES = "MyPrefs";
    private Activity activity = this;
    private ActivityEditor2Binding binding;
    private static final String TAG = "EditorActivity";
    private EditorPresenter editorPresenter;
    private int id = Integer.MAX_VALUE;
    private Snackbar snackbar;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_editor2);
        DatabaseLoader databaseLoader = new DatabaseLoader(activity);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        getLoaderManager().initLoader(LOADER_SCHEDULAR, null, databaseLoader);
        editorPresenter = new EditorPresenter(databaseLoader, new DatabaseQueries(activity), this);
        binding.editor.setEditorFontSize(22);
        binding.editor.setEditorFontColor(Color.BLACK);
        binding.editor.setPadding(10, 10, 10, 10);
        binding.editor.setPlaceholder(getString(R.string.label_insert_text));
        binding.layoutEditorToolbar.actionBold.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionItalic.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionUnderline.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionLeft.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionRight.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionCenter.setOnClickListener(onClickListener);
        binding.layoutEditorToolbar.actionBullet.setOnClickListener(onClickListener);
        binding.btnSubmit.setOnClickListener(onClickListener);
        binding.btnSave.setOnClickListener(onClickListener);
        binding.editor.setOnTextChangeListener(new BGEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                String htmlText = android.text.Html.fromHtml(text).toString();
                int wordCount = htmlText.trim().split("\\s+").length;
                binding.tvWordCounter.setText(wordCountWithText(wordCount));
            }
        });
        setData();
    }

    private String wordCountWithText(int wordCount) {
        StringBuilder stringBuilder = new StringBuilder();
        if (wordCount == 1) {
            return stringBuilder.append(wordCount).append(" ").append(getString(R.string.label_word)).toString();
        } else {
            return stringBuilder.append(wordCount).append(" ").append(getString(R.string.label_words)).toString();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.action_bold:
                    binding.editor.setBold();
                    break;
                case R.id.action_italic:
                    binding.editor.setItalic();
                    break;
                case R.id.action_underline:
                    binding.editor.setUnderline();
                    break;
                case R.id.action_left:
                    binding.editor.setAlignLeft();
                    break;
                case R.id.action_right:
                    binding.editor.setAlignRight();
                    break;
                case R.id.action_center:
                    binding.editor.setAlignCenter();
                    break;
                case R.id.action_bullet:
                    binding.editor.setBullets();
                    break;
                case R.id.btn_submit:
                    if (binding.editor != null && !binding.editor.getHtml().isEmpty()) {
                        Intent intent = new Intent(activity, DisplayHtmlActivity.class);
                        intent.putExtra(DisplayHtmlActivity.EXTRA_HTML, binding.editor.getHtml());
                        startActivity(intent);
                    } else {
                        Snackbar.make(binding.clParent, R.string.msg_nothing_to_show, Snackbar.LENGTH_LONG).show();
                    }

                    break;
                case R.id.btn_save:
                    if (binding.editor != null && !binding.editor.getHtml().isEmpty()) {
                        editorPresenter.saveData(id, binding.editor.getHtml(),EditorPresenter.FROM_SAVE_BUTTON);
                    } else {
                        Snackbar.make(binding.clParent, R.string.msg_nothing_save, Snackbar.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };


    @Override
    public void displayMessage() {
        binding.editor.setHtml("");
        saveData("");
        binding.tvWordCounter.setText(wordCountWithText(0));
        snackbar = Snackbar
                .make(binding.clParent, getString(R.string.msg_saved), Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });

        snackbar.show();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    private void setData() {
        String saveddata = sharedpreferences.getString(SaveData, "");
        binding.editor.setHtml(saveddata);
    }

    private void saveData(String data) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SaveData, data);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        if (!binding.editor.getHtml().isEmpty()) {
            saveData(binding.editor.getHtml());
        }
        super.onDestroy();
    }
}
