package com.example.festpal.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.festpal.R;

public class PaymentConfirmDialog extends Dialog implements android.view.View.OnClickListener{
    private Activity myActivity;
    private OnMyDialogResult myDialogResult;
    private Button mPaynow;
    private Button mPayLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pembayaran);
        mPayLater = findViewById(R.id.pay_later);
        mPaynow = findViewById(R.id.pay_now);
        mPayLater.setOnClickListener(this);
        mPaynow.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_now:
                if (myDialogResult != null) {
                    myDialogResult.finish(true);
                }
                dismiss();

                break;
            case R.id.pay_later:
                if (myDialogResult != null) {
                    myDialogResult.finish(false);
                }
                dismiss();

                break;
            default:
                break;
        }
        dismiss();
    }
    public PaymentConfirmDialog(Activity context){
        super(context);
        myActivity = context;
    }
    public void setDialogResult(PaymentConfirmDialog.OnMyDialogResult dialogResult) {
        myDialogResult = dialogResult;
    }
    public interface OnMyDialogResult{
        void finish(boolean result);
    }
}
