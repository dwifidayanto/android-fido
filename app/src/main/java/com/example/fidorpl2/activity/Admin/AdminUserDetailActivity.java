package com.example.fahmirpl022018.activity.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.fahmirpl022018.R;
import com.example.fahmirpl022018.RS;
import com.example.fahmirpl022018.helper.Config;
import com.example.fahmirpl022018.model.UserAdminModel;

import org.json.JSONObject;

import java.util.HashMap;

public class AdminUserDetailActivity extends AppCompatActivity {

    private EditText etNama, etAlamat, etKtp, etPhone, etEmail;
    private LinearLayout btn_simpan;

    private String U_ID;
    private String mLoginToken = "";
    private String mUserId = "";
    private UserAdminModel mOrderData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_detail);
        binding();
        mOrderData = getIntent().getExtras().getParcelable("extra_user");
        if(/*bundle*/ mOrderData != null) {
            U_ID = mOrderData.getU_ID();

            etNama.setText(mOrderData.getU_NAME());
            etPhone.setText(mOrderData.getU_PHONE());
            etEmail.setText(mOrderData.getU_EMAIL());
            etKtp.setText(mOrderData.getU_AUTHORITY_ID_1());
            etAlamat.setText(mOrderData.getU_ADDRESS());

        }

    }
    private void binding(){
        etNama = findViewById(R.id.et_Nama);
        etAlamat = findViewById(R.id.et_alamat);
        etEmail = findViewById(R.id.et_email);
        etKtp = findViewById(R.id.et_ktp);
        etPhone = findViewById(R.id.et_NoHp);
        btn_simpan = findViewById(R.id.btnSave);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> body = new HashMap<>();
                body.put("id", U_ID);
                body.put("username", etNama.getText().toString());
                body.put("alamat", etAlamat.getText().toString());
                body.put("email", etEmail.getText().toString());
                body.put("noktp", etKtp.getText().toString());
                body.put("nohp", etPhone.getText().toString());

                AndroidNetworking.post(Config.BASE_URL_API + "updateuser.php")
                        .addBodyParameter(body)
                        .setPriority(Priority.MEDIUM)
                        .setOkHttpClient(((RS) getApplication()).getOkHttpClient())
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String message = response.optString(Config.RESPONSE_MESSAGE_FIELD);
                                String status = response.optString(Config.RESPONSE_STATUS_FIELD);

                                Toast.makeText(AdminUserDetailActivity.this, message, Toast.LENGTH_LONG).show();

                                if (message.equalsIgnoreCase(Config.RESPONSE_STATUS_VALUE_SUCCESS)) {
                                    Toast.makeText(AdminUserDetailActivity.this,"Update berhasil",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(AdminUserDetailActivity.this,"Update gagal",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(AdminUserDetailActivity.this, Config.TOAST_AN_ERROR, Toast.LENGTH_SHORT).show();
                                Log.d("RBA", "onError: " + anError.getErrorBody());
                                Log.d("RBA", "onError: " + anError.getLocalizedMessage());
                                Log.d("RBA", "onError: " + anError.getErrorDetail());
                                Log.d("RBA", "onError: " + anError.getResponse());
                                Log.d("RBA", "onError: " + anError.getErrorCode());

                            }
                        });

            }
        });
    }
}
