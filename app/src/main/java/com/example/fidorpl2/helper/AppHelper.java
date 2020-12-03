package com.example.fahmirpl022018.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.fahmirpl022018.activity.Admin.AdminSepedaDetailActivity;
import com.example.fahmirpl022018.activity.Admin.AdminUserDetailActivity;
import com.example.fahmirpl022018.model.SepedaModel;
import com.example.fahmirpl022018.model.UserAdminModel;

import org.json.JSONObject;

public final class AppHelper {
    public static UserAdminModel mapUserAdminModel(JSONObject rowData) {
        UserAdminModel item = new UserAdminModel();
        item.setU_ID(rowData.optString("id"));
        item.setU_NAME(rowData.optString("username"));
        item.setU_ADDRESS(rowData.optString("alamat"));
        item.setU_AUTHORITY_ID_1(rowData.optString("noktp"));
        item.setU_EMAIL(rowData.optString("email"));
        item.setU_PHONE(rowData.optString("nohp"));
        item.setU_GROUP_ROLE(rowData.optString("roleuser"));


        return item;
    }

    public static void goToUserAdminDetail(Context context, UserAdminModel rowData) {
        Bundle bundle = new Bundle();

        bundle.putString("id", String.valueOf(rowData.getU_ID()));
        bundle.putString("username", rowData.getU_NAME().toUpperCase());
        bundle.putString("alamat", rowData.getU_ADDRESS().toUpperCase());
        bundle.putString("noktp", rowData.getU_AUTHORITY_ID_1());
        bundle.putString("email", rowData.getU_EMAIL());
        bundle.putString("nohp", rowData.getU_PHONE().toUpperCase());
        bundle.putString("roleuser", rowData.getU_GROUP_ROLE().toUpperCase());

        Intent i = new Intent(context, AdminUserDetailActivity.class);
        i.putExtra("extra_user", rowData);
        context.startActivity(i);
    }

    public static SepedaModel mapSepedaAdminModel(JSONObject rowData) {
        SepedaModel item = new SepedaModel();
        item.setID(rowData.optString("ID"));
        item.setKODE(rowData.optString("KODE"));
        item.setJENIS(rowData.optString("JENIS"));
        item.setMERK(rowData.optString("MERK"));
        item.setWARNA(rowData.optString("WARNA"));
        item.setHARGA(rowData.optString("HARGA"));
        item.setGAMBAR(rowData.optString("GAMBAR"));


        return item;
    }

    public static void goToSepedaAdminDetail(Context context, SepedaModel rowData) {
        Bundle bundle = new Bundle();

        bundle.putString("ID", String.valueOf(rowData.getID()));
        bundle.putString("KODE", rowData.getKODE().toUpperCase());
        bundle.putString("JENIS", rowData.getJENIS().toUpperCase());
        bundle.putString("HARGA", rowData.getHARGA());
        bundle.putString("MERK", rowData.getMERK());
        bundle.putString("WARNA", rowData.getWARNA().toUpperCase());
        bundle.putString("GAMBAR", rowData.getGAMBAR().toUpperCase());

        Intent i = new Intent(context, AdminSepedaDetailActivity.class);
        i.putExtra("extra_sepeda", rowData);
        context.startActivity(i);
    }
}
