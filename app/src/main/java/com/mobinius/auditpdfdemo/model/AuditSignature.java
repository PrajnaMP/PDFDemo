package com.mobinius.auditpdfdemo.model;

import android.graphics.Bitmap;

/**
 * Created by prajna on 24/11/17.
 */

public class AuditSignature {
    private String signatureName;
    private String signatureImageName;

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getSignatureImageName() {
        return signatureImageName;
    }

    public void setSignatureImageName(String signatureImageName) {
        this.signatureImageName = signatureImageName;
    }
}
