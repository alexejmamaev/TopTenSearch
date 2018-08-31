package com.mamaevaleksej.toptensearch.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mamaevaleksej.toptensearch.R;
import com.mamaevaleksej.toptensearch.adapter.SearchResultsAdapter;
import com.mamaevaleksej.toptensearch.model.Item;
import com.mamaevaleksej.toptensearch.utilities.AppRepository;
import com.mamaevaleksej.toptensearch.utilities.EventListener;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements EventListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private SearchResultsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mQueryEditText;
    private Button mSearchBtn;
    private ProgressBar mLoadingIndicator;
    private String query;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();

        repository = AppRepository.getInstance(this);

        setUpViewModel();
    }

    private void initViews(){
        mQueryEditText = findViewById(R.id.search_et);
        mLoadingIndicator = findViewById(R.id.progressBar);
        mSearchBtn = findViewById(R.id.search_btn);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuery();
            }
        });

        mRecyclerView = findViewById(R.id.customRecyclerView);
        mAdapter = new SearchResultsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setQuery(){
        hideKeyboard();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        query = mQueryEditText.getText().toString();
        repository.setQuery(query, this, this);
    }

    @Override
    public void onSuccess(List<Item> itemList) {
        mLoadingIndicator.setVisibility(View.GONE);

        // Запись результатов поиска в базу данных
        if (itemList != null){

            // Очистка базы данных
            repository.deleteAllItems();

            Item[] itemsArray = new Item[itemList.size()];
            itemsArray = itemList.toArray(itemsArray);
            repository.InsertItems(itemsArray);

            setUpViewModel();

        } else {
            Toast.makeText(this, getString(R.string.nothing_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    private void setUpViewModel(){
       SearchViewModelFactory factory = new SearchViewModelFactory(query, this);
       SearchViewModel viewModel = ViewModelProviders.of(this, factory)
               .get(SearchViewModel.class);
       viewModel.getItems().observe(this, new Observer<List<Item>>() {
           @Override
           public void onChanged(@Nullable List<Item> itemList) {
               Log.d(TAG, "Updating list of Items from LiveData in ViewModel +++++++++++++");
               mAdapter.setItemList(itemList);
           }
       });
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
