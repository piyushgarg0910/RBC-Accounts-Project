package com.rbc.accounts.util.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.rbc.rbcaccountlibrary.Transaction;

public class TransactionDiffUtilCallback extends DiffUtil.ItemCallback<Transaction> {

    @Override
    public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
        // Ideally only id should be compared here. However, since we do not have
        // an id, and there can be multiple transactions of same amount, I am assuming
        // that we can compare by dates. If dates are same to the millisecond, they
        // are the same transactions.
        return oldItem.getDate().equals(newItem.getDate());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
        return oldItem.getDate().equals(newItem.getDate())
                && oldItem.getAmount().equals(newItem.getAmount())
                && oldItem.getDescription().equals(newItem.getDescription()) ;
    }
}
