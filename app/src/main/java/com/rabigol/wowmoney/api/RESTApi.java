package com.rabigol.wowmoney.api;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.events.APIFeedLoadFailEvent;
import com.rabigol.wowmoney.events.APIFeedLoadSuccessEvent;
import com.rabigol.wowmoney.events.APILoginFailEvent;
import com.rabigol.wowmoney.events.APILoginSuccessEvent;
import com.rabigol.wowmoney.events.APIOperationsLoadSuccessEvent;
import com.rabigol.wowmoney.events.APIRegisterFailEvent;
import com.rabigol.wowmoney.events.APIRegisterSuccessEvent;
import com.rabigol.wowmoney.utils.FakeOperations;

import org.greenrobot.eventbus.EventBus;

public class RESTApi {
    //    private final static String API_URL = "http://10.16.16.89:9090/api/"; //office ip
    private final static String API_URL = "http://10.1.30.37:9090/api/"; //home ip
    //    private final static String API_URL = "http://localhost:9090/api/";
    private static RESTApi mInstance;
    private RequestQueue mRequestQueue;

    public static RESTApi getInstance() {
        if (mInstance == null) {
            mInstance = new RESTApi();
            mInstance.mRequestQueue =
                    Volley.newRequestQueue(App.getInstance());
        }
        return mInstance;
    }

    public void login(String login, String password) {
        try {
            String url = API_URL + "auth/login";
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", login);
            jsonObject.put("password", password);

            mRequestQueue.add(new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            EventBus.getDefault().post(new APILoginSuccessEvent(response));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Сделать обработку ошибок
                    EventBus.getDefault().post(new APILoginFailEvent(error));
                    Log.i("REST LOGIN ERROR", error.toString());
                    if (error instanceof TimeoutError) {
                        //TODO: make timeouterror in failevent or anywhere else
                    } else if (error instanceof ServerError) {
                        //TODO: make servererror in failevent or anywhere else
                    } else {
                        //TODO: error toasts
                    }
                }
            }
            ));

        } catch (JSONException j) {
            EventBus.getDefault().post(new APILoginFailEvent());
        }

    }

    public void register(String email, String password) {
        try {
            String url = API_URL + "auth/register";
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            mRequestQueue.add(new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            EventBus.getDefault().post(new APIRegisterSuccessEvent(response)); // response from server
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    EventBus.getDefault().post(new APIRegisterFailEvent(error));
                    Log.i("REST REGISTER ERROR", error.toString());
                }
            }
            ));

        } catch (JSONException j) {
            EventBus.getDefault().post(new APIRegisterFailEvent());
        }
    }

    public void loadItems(int userId) {
        String url = API_URL + "operations/" + userId;
        final JSONArray jsonArray = new JSONArray();

        mRequestQueue.add(new JsonArrayRequest(
                Request.Method.GET,
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        EventBus.getDefault().post(new APIFeedLoadSuccessEvent(response)); // response from server
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new APIFeedLoadFailEvent(error));
                Log.i("REST loadItems error", error.toString());
            }
        }
        ));
    }


    public static void restore(String login) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: Something
            }
        }, 1000);
    }

    public static void loadFeed() {
        // TODO:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new APIOperationsLoadSuccessEvent(FakeOperations.getInstance().getOperationItems()));
            }
        }, 1000);
    }
}
