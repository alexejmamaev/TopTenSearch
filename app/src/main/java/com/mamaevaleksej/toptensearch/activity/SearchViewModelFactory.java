package com.mamaevaleksej.toptensearch.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String mQuery;
    private Context context;

    public SearchViewModelFactory(String mQuery, Context context) {
        this.mQuery = mQuery;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(context, mQuery);
    }
}
