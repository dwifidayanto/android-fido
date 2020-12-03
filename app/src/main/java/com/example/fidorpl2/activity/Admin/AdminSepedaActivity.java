package com.example.fahmirpl022018.activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.fahmirpl022018.R;
import com.example.fahmirpl022018.RS;
import com.example.fahmirpl022018.adapter.AdminSepedaAdapter;
import com.example.fahmirpl022018.helper.AppHelper;
import com.example.fahmirpl022018.helper.Config;
import com.example.fahmirpl022018.model.SepedaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminSepedaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView ivAdd, ivBack;

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rv;
    private FloatingActionButton tambahdata;

    private ArrayList<SepedaModel> mList = new ArrayList<>();
    private AdminSepedaAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sepeda);
        binding();
    }

    @Override
    public void onRefresh() {
        getUserList();
    }

    private void binding() {
        tambahdata = findViewById(R.id.tambahdata);
        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSepedaActivity.this,AdminSepedaCreateActivity.class);
                startActivity(i);
            }
        });

        rv = findViewById(R.id.rvNamaSepeda);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        swipeRefresh.post(new Runnable() {
            private void doNothing() {

            }

            @Override
            public void run() {
                getUserList();
            }
        });

    }

    public void getUserList() {
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get(Config.BASE_URL_API + "getsepeda.php")
                .setPriority(Priority.LOW)
                .setOkHttpClient(((RS) getApplication()).getOkHttpClient())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        swipeRefresh.setRefreshing(false);
                        if (mAdapter != null) {
                            mAdapter.clearData();
                            mAdapter.notifyDataSetChanged();
                        }
                        if (mList != null) mList.clear();
                        Log.d("RBA", "res" + response);
                        try {
                            Log.i("AB", "respo: "+response);
                            //Loop the Array
                            for(int i=0;i < response.length();i++) {
                                JSONObject data = response.getJSONObject(i);
                                Log.e("ADF", "ponse: "+data );
                                SepedaModel item = AppHelper.mapSepedaAdminModel(data);
                                mList.add(item);
                            }
//                            mAdapter = new AdminUserAdapter (list_data_sepedaActivity.this, mList, list_data_sepedaActivity.this);
                            mAdapter = new AdminSepedaAdapter (AdminSepedaActivity.this, mList, AdminSepedaActivity.this);
                            rv.setAdapter(mAdapter);
                        } catch(JSONException e) {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(AdminSepedaActivity.this, Config.TOAST_AN_ERROR, Toast.LENGTH_SHORT).show();
                        Log.d("A", "onError1: " + anError.getErrorBody());
                        Log.d("A", "onError: " + anError.getLocalizedMessage());
                        Log.d("A", "onError: " + anError.getErrorDetail());
                        Log.d("A", "onError: " + anError.getResponse());
                        Log.d("A", "onError: " + anError.getErrorCode());
                    }

                });

    }
}