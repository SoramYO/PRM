package com.example.modulesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private List<Module> moduleList;
    private Context context;
    private OnModuleClickListener listener;

    // Simplified interface for handling clicks
    public interface OnModuleClickListener {
        void onModuleEdit(Module module, int position);
        void onModuleDelete(int position);
    }

    public ModuleAdapter(Context context, List<Module> moduleList, OnModuleClickListener listener) {
        this.context = context;
        this.moduleList = moduleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.bind(module);

        // Long click to edit
        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                listener.onModuleEdit(module, pos);
            }
            return true;
        });

        // Single click to delete with confirmation
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && listener != null) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Xóa module")
                       .setMessage("Bạn có chắc muốn xóa module này?")
                       .setPositiveButton("Có", (dialog, which) -> listener.onModuleDelete(pos))
                       .setNegativeButton("Không", null)
                       .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle, tvDescription, tvPlatform;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPlatform = itemView.findViewById(R.id.tvPlatform);
        }

        public void bind(Module module) {
            imageView.setImageResource(module.getImageResId());
            tvTitle.setText(module.getTitle());
            tvDescription.setText(module.getDescription());
            tvPlatform.setText(module.getPlatform());
        }
    }
}
