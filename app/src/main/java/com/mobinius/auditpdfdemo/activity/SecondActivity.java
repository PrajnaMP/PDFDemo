package com.mobinius.auditpdfdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobinius.auditpdfdemo.fragment.PdfRendererBasicFragment;
import com.mobinius.auditpdfdemo.R;

/**
 * Created by prajna on 23/11/17.
 */

public class SecondActivity extends AppCompatActivity {

    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfRendererBasicFragment(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }
}
