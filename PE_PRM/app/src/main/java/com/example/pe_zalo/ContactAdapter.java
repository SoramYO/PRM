package com.example.pe_zalo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactNameTextView.setText(contact.getName());
        holder.contactPhoneTextView.setText(contact.getPhoneNumber());
        holder.contactCheckBox.setChecked(contact.isSelected());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = !contact.isSelected();
                contact.setSelected(isSelected);
                holder.contactCheckBox.setChecked(isSelected);
            }
        });

        holder.contactCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = holder.contactCheckBox.isChecked();
                contact.setSelected(isSelected);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public List<Contact> getSelectedContacts() {
        List<Contact> selectedContacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.isSelected()) {
                selectedContacts.add(contact);
            }
        }
        return selectedContacts;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactNameTextView;
        TextView contactPhoneTextView;
        CheckBox contactCheckBox;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView);
            contactPhoneTextView = itemView.findViewById(R.id.contactPhoneTextView);
            contactCheckBox = itemView.findViewById(R.id.contactCheckBox);
        }
    }
}