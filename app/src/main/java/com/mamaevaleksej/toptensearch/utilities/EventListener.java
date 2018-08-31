package com.mamaevaleksej.toptensearch.utilities;

import com.mamaevaleksej.toptensearch.model.Item;

import java.util.List;

public interface EventListener {
    void onSuccess(List<Item> itemList);
    void onFailure();
}
