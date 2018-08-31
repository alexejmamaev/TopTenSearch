package com.mamaevaleksej.toptensearch.utilities;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mamaevaleksej.toptensearch.database.AppDataBase;
import com.mamaevaleksej.toptensearch.model.Example;
import com.mamaevaleksej.toptensearch.model.Item;
import com.mamaevaleksej.toptensearch.networking.GetDataService;
import com.mamaevaleksej.toptensearch.networking.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static AppRepository sInstance;
    private AppDataBase mDb;
    private List<Item> mItemList;

    private AppRepository(Context context) {
        mDb = AppDataBase.getsInstance(context);
    }

    // Синглтон ApoRepository
    public synchronized static AppRepository getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new AppRepository(context);
            }
        }
        return sInstance;
    }

    public LiveData<List<Item>> getItems(){
        Log.d(TAG, "Getting all Items via repository *********");
        //  Запросы LiveData выполняются асинхронно
        return mDb.Dao().loadAllItems();
    }

    public LiveData<Item> getItem(int id){
        Log.d(TAG, "Getting Item " + id + "via repository *********");
        //  Запросы LiveData выполняются асинхронно
        return mDb.Dao().loadItemById(id);
    }

    public void deleteAllItems(){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Clearing all tables from DB via repository ***********");
                mDb.clearAllTables();
            }
        });
    }

    public void InsertItems(final Item... items){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Inserting " + items.length + " items to DB via repository ***********");
                mDb.Dao().insertItems(items);
            }
        });
    }

    // Пользователь ввел запрос, производится асинхронный сетевой запрос (Retrofit 2)
    public void setQuery(String query, final Context context, final EventListener listener) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance()
                    .create(GetDataService.class);
            Call<Example> call = service.getResultObject(query);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    mItemList = response.body() != null ? response.body().getItems() : null;
                    listener.onSuccess(mItemList);
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(context, "Unable to set connection",
                        Toast.LENGTH_SHORT).show();
                listener.onFailure();
                }
            });

            // Возвращает полный URL запроса (для информации)
            String finalURL = service.getResultObject(query).request().url().toString();
            Log.d(TAG, "URL ====>>> " + finalURL);

    }
}
