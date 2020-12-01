package com.example.wesafe.UtilService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wesafe.HomeActivity;
import com.example.wesafe.MainActivity;
import com.example.wesafe.RegisterActivity;
import com.example.wesafe.RegisterActivity2;

public class ApiCall extends Activity{
    private Context CallingClassContext;
    SharedPreferenceClass sharedPreferenceClass;
    public ApiCall(Context context){
        CallingClassContext=context;
        sharedPreferenceClass=new SharedPreferenceClass(CallingClassContext);
    }
    public void PostCall(HashMap<String,String> params, String Endpoint,final GetResult callback)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Endpoint,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success"))
                    {
                        String token=response.getString("token");
                        sharedPreferenceClass.setValue_string("token",token);
                        Toast.makeText(CallingClassContext,response.getString("status"),Toast.LENGTH_SHORT).show();
                        // This is Called if Api responds successfully
                        callback.onSuccess(response.getJSONObject("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response=error.networkResponse;
                if(error instanceof ServerError && response!=null)
                {
                    try {
                        String res=new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(CallingClassContext,obj.getJSONObject("err").getString("message"),Toast.LENGTH_SHORT).show();
                        callback.onFailure(obj.getJSONObject("err").getString("message"));
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }
                else
                {
                    Log.wtf("Error",error.toString());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String > headers=new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        // Set the retry policy
        int socketTime=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        //request added
        RequestQueue requestQueue= Volley.newRequestQueue(CallingClassContext);
        requestQueue.add(jsonObjectRequest);
    }

    public void CallWithoutToken(HashMap<String,String> params, String Endpoint,final GetResult callback)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Endpoint,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success"))
                    {
                        Toast.makeText(CallingClassContext,response.getString("status"),Toast.LENGTH_SHORT).show();
                        // This is Called if Api responds successfully
                        callback.onSuccess(response.getJSONObject("data"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response=error.networkResponse;
                if(error instanceof ServerError && response!=null)
                {
                    try {
                        String res=new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(CallingClassContext,obj.getJSONObject("err").getString("message"),Toast.LENGTH_SHORT).show();
                        callback.onFailure(obj.getJSONObject("err").getString("message"));
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }
                else
                {
                    Log.wtf("Error",error.toString());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String > headers=new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        // Set the retry policy
        int socketTime=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        //request added
        RequestQueue requestQueue= Volley.newRequestQueue(CallingClassContext);
        requestQueue.add(jsonObjectRequest);
    }

    // This is when we pass token in header
    public void VerifiedPostCall(HashMap<String,String> params, String Endpoint, final GetResult callback)
    {
        final String Auth_Token=sharedPreferenceClass.getValue_string("token");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Endpoint,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success"))
                    {
                        Toast.makeText(CallingClassContext,response.getString("status"),Toast.LENGTH_SHORT).show();
                        Log.wtf("response",response.getString("status"));
                        callback.onSuccess(response.getJSONObject("data"));
                    }
                    else
                    {
                        Log.wtf("response",response.getString("status"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response=error.networkResponse;
                if(error instanceof ServerError && response!=null)
                {
                    try {
                        String res=new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj=new JSONObject(res);
                        Toast.makeText(CallingClassContext,obj.getJSONObject("err").getString("message"),Toast.LENGTH_SHORT).show();
                        callback.onFailure(obj.getJSONObject("err").getString("message"));
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String > headers=new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization", "Bearer " + Auth_Token);
                return headers;
            }
        };
        // Set the retry policy
        int socketTime=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        //request added
        RequestQueue requestQueue= Volley.newRequestQueue(CallingClassContext);
        requestQueue.add(jsonObjectRequest);
    }
}