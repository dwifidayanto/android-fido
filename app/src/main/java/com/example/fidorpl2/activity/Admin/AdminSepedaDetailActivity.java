package com.example.fahmirpl022018.activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.fahmirpl022018.R;
import com.example.fahmirpl022018.RS;
import com.example.fahmirpl022018.adapter.AdminSepedaAdapter;
import com.example.fahmirpl022018.helper.Config;
import com.example.fahmirpl022018.model.SepedaModel;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

public class AdminSepedaDetailActivity extends AppCompatActivity implements IPickResult {

    private Button btneditsepeda;
    private ImageView ivSepeda;
    private EditText tvKodeSepeda,tvMerkSepeda,tvJenisSepeda,tvWarnaSepeda,tvHargaSewa;

    private ProgressDialog mProgress;
    private SepedaModel model;
    private String U_ID;
    private AdminSepedaAdapter mAdapter;
    private boolean mIsFormFilled = false;

    private Bitmap mSelectedImage;
    private String mSelectedImagePath;
    File mSelectedFileBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sepeda_detail);
        binding();
        model = getIntent().getExtras().getParcelable("extra_sepeda");
        if(/*bundle*/ model != null) {
            U_ID = model.getID();

            tvKodeSepeda.setText(model.getKODE());
            tvJenisSepeda.setText(model.getJENIS());
            tvMerkSepeda.setText(model.getMERK());
            tvWarnaSepeda.setText(model.getWARNA());
            tvHargaSewa.setText(model.getHARGA());
            Picasso.with(AdminSepedaDetailActivity.this)
                    .load(Config.BASE_URL_UPLOADS+model.getGAMBAR())
                    .into(ivSepeda);

        }
    }
    private void binding() {
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Mohon tunggu...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        tvKodeSepeda = findViewById(R.id.etKodeSepeda);
        tvJenisSepeda = findViewById(R.id.etJenisSepeda);
        tvMerkSepeda = findViewById(R.id.etMerkSepeda);
        tvWarnaSepeda = findViewById(R.id.etWarnaSepeda);
        tvHargaSewa = findViewById(R.id.etHargaSewa);
        ivSepeda = findViewById(R.id.ivSepeda);
        ivSepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(AdminSepedaDetailActivity.this);
                new PickSetup().setCameraButtonText("Gallery");
            }
        });
        btneditsepeda = findViewById(R.id.btneditsepeda);
        btneditsepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsFormFilled = true;
                final String kode = tvKodeSepeda.getText().toString();
                final String jenis = tvJenisSepeda.getText().toString();
                final String merk = tvMerkSepeda.getText().toString();
                final String warna = tvWarnaSepeda.getText().toString();
                final String harga = tvHargaSewa.getText().toString();

                if (kode.isEmpty() || jenis.isEmpty() || merk.isEmpty() || warna.isEmpty() || harga.isEmpty()) {
                    Toast.makeText(AdminSepedaDetailActivity.this, "Harap lengkapi isian yang tersedia", Toast.LENGTH_SHORT).show();
                    mIsFormFilled = false;
                }

                if (mIsFormFilled) {
                    HashMap<String, String> body = new HashMap<>();

                    mProgress.show();
                    body.put("id", U_ID);
                    body.put("kode", kode);
                    body.put("jenis", jenis);
                    body.put("merk", merk);
                    body.put("warna", warna);
                    body.put("hargasewa", harga);

                    AndroidNetworking.upload(Config.BASE_URL_API + "updatedata.php")
                            .addMultipartParameter(body)
                            .addMultipartFile("gambar",mSelectedFileBanner)
                            .setPriority(Priority.MEDIUM)
                            .setOkHttpClient(((RS) getApplication()).getOkHttpClient())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String message = response.getString(Config.RESPONSE_MESSAGE_FIELD);

                                        Toast.makeText(AdminSepedaDetailActivity.this, message, Toast.LENGTH_LONG).show();

                                        if (message.equalsIgnoreCase(Config.RESPONSE_STATUS_VALUE_SUCCESS)) {
                                            Intent intent = new Intent(AdminSepedaDetailActivity.this, AdminSepedaActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("HBB", "JSONException: " + e.getMessage());
                                    }

                                    mProgress.dismiss();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    mProgress.dismiss();
                                    Toast.makeText(AdminSepedaDetailActivity.this, Config.TOAST_AN_ERROR, Toast.LENGTH_SHORT).show();
                                    Log.d("HBB", "onError: " + anError.getErrorBody());
                                    Log.d("HBB", "onError: " + anError.getLocalizedMessage());
                                    Log.d("HBB", "onError: " + anError.getErrorDetail());
                                    Log.d("HBB", "onError: " + anError.getResponse());
                                    Log.d("HBB", "onError: " + anError.getErrorCode());
                                }
                            });
                }

            }
        });
    }

    @Override
    public void onPickResult(PickResult r) {
        if(r.getError() == null){
            try {
                File fileku = new Compressor(this)
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(new File(r.getPath()));
                mSelectedImagePath = fileku.getAbsolutePath();
                mSelectedFileBanner = new File(mSelectedImagePath);
                mSelectedImage=r.getBitmap();
                ivSepeda.setImageBitmap(mSelectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(AdminSepedaDetailActivity.this, r.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}