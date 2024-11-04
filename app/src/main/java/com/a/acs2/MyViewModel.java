package com.a.acs2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private final MutableLiveData<String> jsonData = new MutableLiveData<>();

    // Method to set the JSON data
    public void setJsonData(String data) {
        jsonData.setValue(data);
    }

    // Method to get the JSON data
    public LiveData<String> getJsonData() {
        return jsonData;
    }
}
