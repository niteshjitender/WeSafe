package com.example.wesafe.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

public interface GetResult {
    public void onSuccess(JSONObject data) throws JSONException;
    public void onFailure(String err);
}
