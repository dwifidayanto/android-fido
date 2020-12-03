package com.example.fahmirpl022018.activity.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.fahmirpl022018.R;
import com.example.fahmirpl022018.RS;
import com.example.fahmirpl022018.adapter.AdminUserAdapter;
import com.example.fahmirpl022018.helper.AppHelper;
import com.example.fahmirpl022018.helper.Config;
import com.example.fahmirpl022018.model.UserAdminModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminUserActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ImageView iv_more_admin, ivNew, icon_user;

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rv;

    private ArrayList<UserAdminModel> mList = new ArrayList<>();
    private AdminUserAdapter mAdapter;

    private String mLoginToken = "";
    private String mUserId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_admin_user);
        binding();
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            private void doNothing() {

            }

            @Override
            public void run() {
                getUserList();
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void binding() {
        icon_user = findViewById(R.id.icon_user);
        icon_user.setVisibility(View.VISIBLE);
        iv_more_admin = findViewById(R.id.iv_more_admin);
        iv_more_admin.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View view) {
                logout();
            }
        });

        rv = findViewById(R.id.rvUserManage);
        swipeRefresh = findViewById(R.id.swipeRefresh);

    }

    @Override
    public void onRefresh() {
        getUserList();
    }


    public void show(){
        mAdapter = new AdminUserAdapter(AdminUserActivity.this, mList, AdminUserActivity.this);

        rv.setAdapter(mAdapter);
    }


    public void getUserList() {
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get(Config.BASE_URL_API + "getdatauser.php")
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
                                UserAdminModel item = AppHelper.mapUserAdminModel(data);
                                mList.add(item);
                            }
                            show();
                        } catch(JSONException e) {
                            Log.e("log_tag", "Error parsing data "+e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(AdminUserActivity.this, Config.TOAST_AN_ERROR, Toast.LENGTH_SHORT).show();
                        Log.d("A", "onError1: " + anError.getErrorBody());
                        Log.d("A", "onError: " + anError.getLocalizedMessage());
                        Log.d("A", "onError: " + anError.getErrorDetail());
                        Log.d("A", "onError: " + anError.getResponse());
                        Log.d("A", "onError: " + anError.getErrorCode());
                    }
                });

    }
    private void logout() {
        new AlertDialog.Builder(AdminUserActivity.this)
                .setTitle("Logout")
                .setMessage("Anda yakin akan logout ?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    private void doNothing() {

                    }

                    public void onClick(DialogInterface arg0, int arg1) {
                        Config.forceLogout(AdminUserActivity.this);
                    }
                }).create().show();
    }

}