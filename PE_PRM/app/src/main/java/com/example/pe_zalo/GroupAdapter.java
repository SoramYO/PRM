package com.example.pe_zalo;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<Group> groupList;
        private OnGroupItemClickListener listener;
    private int contextMenuPosition = -1;
        public interface OnGroupItemClickListener {
        void onGroupItemClick(Group group, int position);
        void onGroupItemLongClick(Group group, int position);
    }


    public GroupAdapter() {
        this.groupList = new ArrayList<>();
    }
        public void setOnGroupItemClickListener(OnGroupItemClickListener listener) {
        this.listener = listener;
    }
        public void setGroups(List<Group> groups) {
        this.groupList = new ArrayList<>(groups);
        notifyDataSetChanged();
    }
        public void updateGroup(Group group, int position) {
        if (position >= 0 && position < groupList.size()) {
            groupList.set(position, group);
            notifyItemChanged(position);
        }
    }
    public void removeGroup(int position) {
        if (position >= 0 && position < groupList.size()) {
            groupList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public int getContextMenuPosition() {
        return contextMenuPosition;
    }
    
    public void setContextMenuPosition(int position) {
        this.contextMenuPosition = position;
    }
    
    public Group getGroupAt(int position) {
        if (position >= 0 && position < groupList.size()) {
            return groupList.get(position);
        }
        return null;
    }


    public void addGroup(Group group) {
        groupList.add(group);
        notifyItemInserted(groupList.size() - 1);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

@Override
public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
    Group group = groupList.get(position);
    holder.groupNameTextView.setText(group.getName());
    holder.memberCountTextView.setText(group.getMemberCount() + " thành viên");
    
    final int pos = position;
    holder.itemView.setOnClickListener(v -> {
        if (listener != null) {
            listener.onGroupItemClick(group, pos);
        }
    });
    
    // This is important - store position for context menu
    holder.itemView.setOnLongClickListener(v -> {
        contextMenuPosition = pos;
        // Return false to allow the context menu to show
        return false;
    });
}

    @Override
    public int getItemCount() {
        return groupList.size();
    }

static class GroupViewHolder extends RecyclerView.ViewHolder 
        implements View.OnCreateContextMenuListener {
    TextView groupNameTextView;
    TextView memberCountTextView;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
        memberCountTextView = itemView.findViewById(R.id.memberCountTextView);
        // Register this view for context menu
        itemView.setOnCreateContextMenuListener(this);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, 
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // Use the MainActivity constants directly
        menu.add(0, MainActivity.MENU_EDIT_GROUP, 0, "Chỉnh sửa nhóm");
        menu.add(0, MainActivity.MENU_DELETE_GROUP, 1, "Xóa nhóm");
    }
}
}