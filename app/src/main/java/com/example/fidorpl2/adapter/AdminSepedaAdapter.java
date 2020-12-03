package com.example.fahmirpl022018.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahmirpl022018.R;
import com.example.fahmirpl022018.activity.Admin.AdminSepedaActivity;
import com.example.fahmirpl022018.activity.Admin.AdminSepedaDetailActivity;
import com.example.fahmirpl022018.activity.Admin.AdminUserActivity;
import com.example.fahmirpl022018.helper.AppHelper;
import com.example.fahmirpl022018.model.SepedaModel;
import com.example.fahmirpl022018.model.UserAdminModel;

import java.util.List;

public class AdminSepedaAdapter extends RecyclerView.Adapter<AdminSepedaAdapter.ItemViewHolder> {
    private Context context;
    private List<SepedaModel> mList;
    private String mLoginToken = "";
    private boolean mBusy = false;
    private ProgressDialog mProgressDialog;
    private AdminSepedaActivity mAdminUserActivity;


    public AdminSepedaAdapter(Context context, List<SepedaModel> mList, Activity AdminUserActivity) {
        this.context = context;
        this.mList = mList;
        this.mAdminUserActivity = (AdminSepedaActivity) AdminUserActivity;

    }

    @NonNull
    @Override
    public AdminSepedaAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_admin_sepeda, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminSepedaAdapter.ItemViewHolder itemViewHolder, int i) {
        final SepedaModel Amodel = mList.get(i);
        itemViewHolder.bind(Amodel);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clearData() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamasepeda, tvJenissepeda, tvHargaSewa;
        private CardView card_sepeda;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamasepeda = itemView.findViewById(R.id.tvNamaSepeda);
            tvHargaSewa = itemView.findViewById(R.id.tvHargaSewa);
            card_sepeda = itemView.findViewById(R.id.div_sepeda);
        }
        private void bind(final SepedaModel Amodel) {
            tvNamasepeda.setText(Amodel.getMERK());
            tvHargaSewa.setText(Amodel.getHARGA());
            card_sepeda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppHelper.goToSepedaAdminDetail(context,Amodel);
                }
            });

        }
    }
}
