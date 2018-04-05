package com.mobinius.auditpdfdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobinius.auditpdfdemo.R;
import com.mobinius.auditpdfdemo.model.AuditCategory;
import com.mobinius.auditpdfdemo.model.AuditModelClass;
import com.mobinius.auditpdfdemo.model.AuditQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;

/**
 * Created by prajna on 30/11/17.
 */

public class ThirdActivity extends Activity {
    private Button mRenderPdf, mViewPdf;

    List<AuditCategory> listCategorys = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRenderPdf = (Button) findViewById(R.id.render_pdf_text_view);
        mViewPdf = (Button) findViewById(R.id.view_pdf_text_view);
        mRenderPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        //load json
        String jsonStr = loadJSONFromAsset();
        //parsing json data
        parseJson(jsonStr);
        //create pdf
        createCategory__();

    }

    private void dialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ThirdActivity.this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(ThirdActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                        finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        dialog();


    }

    private void parseJson(String jsonStr) {
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray questionsArray = jsonObject.getJSONArray("questions");
                for (int i = 0; i < questionsArray.length(); i++) {
                    AuditCategory auditCategory = new AuditCategory();

                    JSONObject categoryObject = questionsArray.getJSONObject(i);
                    String categoryName = categoryObject.getString("categoryName");
                    auditCategory.setCategoryName(categoryName);

                    JSONArray questionArray = categoryObject.getJSONArray("questions");

                    for (int j = 0; j < questionArray.length(); j++) {
                        AuditQuestion auditQuestion = new AuditQuestion();
                        JSONObject questionArrayObject = questionArray.getJSONObject(j);
                        auditQuestion.setQuestionName(questionArrayObject.getString("question"));
                        auditCategory.getListQuestions().add(auditQuestion);
                    }
                    listCategorys.add(auditCategory);
                }
            } catch (JSONException e) {
                System.out.println("exception while parsing::"+e.getMessage());
            }
        }
    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("audits.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void createCategory__() {
        for (AuditCategory auditCategory : listCategorys) {
            System.out.println("createCategory__ CategoryName " + auditCategory.getCategoryName());
            for (AuditQuestion auditQuestion : auditCategory.getListQuestions()) {
                System.out.println("createCategory__ QuestionName" + auditQuestion.getQuestionName());

            }
        }

    }
}