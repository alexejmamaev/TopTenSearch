package com.mamaevaleksej.toptensearch.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.mamaevaleksej.toptensearch.model.Item;
import com.mamaevaleksej.toptensearch.utilities.AppRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private LiveData<List<Item>> itemsList;

    public SearchViewModel(Context context, String query) {
        AppRepository repository = AppRepository.getInstance(context);
        itemsList = repository.getItems();
    }

    public LiveData<List<Item>> getItems(){
        return itemsList;
    }
}
