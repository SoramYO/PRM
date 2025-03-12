package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(Context context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return congViecList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtTieuDe, txtNoiDung;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTieuDe = (TextView) convertView.findViewById(R.id.tvTenCongViec);
            holder.txtNoiDung = (TextView) convertView.findViewById(R.id.tvNoiDung);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivXoa);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.ivSua);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CongViec congViec = congViecList.get(position);
    holder.txtTieuDe.setText(congViec.getTieude());
    holder.txtNoiDung.setText(congViec.getNoidung());
    
    // Add click listener for delete button
    holder.imgDelete.setOnClickListener(v -> {
        ((MainActivity)context).deleteCongViec(congViec.getId(), congViec.getTieude());
    });
            holder.imgEdit.setOnClickListener(v -> {
            ((MainActivity)context).dialogSua(congViec);
        });
        return convertView;
    }
}