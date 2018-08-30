package com.mamaevaleksej.toptensearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamaevaleksej.toptensearch.R;
import com.mamaevaleksej.toptensearch.model.Item;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private static final String TAG = SearchResultsAdapter.class.getSimpleName();
    private List<Item> itemsList;
    private Context context;

    public SearchResultsAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTV;
        TextView mLinkTV;
        TextView mSippetTV;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.title_tv);
            mLinkTV = itemView.findViewById(R.id.link_tv);
            mSippetTV = itemView.findViewById(R.id.snippet_tv);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.search_result_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mTitleTV.setText(itemsList.get(position).getTitle());
        viewHolder.mLinkTV.setText(itemsList.get(position).getLink());
        viewHolder.mSippetTV.setText(itemsList.get(position).getSnippet());

    }

    @Override
    public int getItemCount() {
        if (itemsList != null)
        return itemsList.size();
        return 0;
    }

    public void setItemList(List<Item> itemList){
        itemsList = itemList;
        notifyDataSetChanged();
    }
}
