package com.rbc.accounts.util.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.rbc.accounts.model.BaseAccountDetailsSummaryModel;
import com.rbc.rbcaccountlibrary.Transaction;

public class TransactionDiffUtilCallback extends DiffUtil.ItemCallback<BaseAccountDetailsSummaryModel> {

    @Override
    public boolean areItemsTheSame(
            @NonNull BaseAccountDetailsSummaryModel oldItem,
            @NonNull BaseAccountDetailsSummaryModel newItem
    ) {
        // Ideally only id should be compared here. However, since we do not have
        // an id, and there can be multiple transactions of same amount, I am assuming
        // that we can compare by dates. If dates are same to the millisecond, they
        // are the same transactions.
        return oldItem.getDate().equals(newItem.getDate()) &&
                oldItem.getViewType().equals(newItem.getViewType());
    }

    @Override
    public boolean areContentsTheSame(
            @NonNull BaseAccountDetailsSummaryModel oldItem,
            @NonNull BaseAccountDetailsSummaryModel newItem) {
        return oldItem.getDate().equals(newItem.getDate()) &&
                oldItem.getViewType().equals(newItem.getViewType());
    }
}
