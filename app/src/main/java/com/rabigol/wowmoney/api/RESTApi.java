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
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.FakeOperations;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class RESTApi {
        private final static String API_URL = "http://10.16.16.89:9090/api/"; //office ip
//    private final static String API_URL = "http://10.1.30.37:9090/api/"; //home ip
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
        final String url = API_URL + "operations/" + userId;
        final JSONArray jsonArray = new JSONArray();

        mRequestQueue.add(new JsonArrayRequest(
                Request.Method.POST, //Check! Was get!
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FakeOperations.clearArray();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject1 = null;
                            //TODO: parse to array
                            try {
                                jsonObject1 = response.getJSONObject(i);
                                OperationItem operationItem = new OperationItem(
                                        App.getInstance().getAppLoggedUserId()
                                        , jsonObject1.getInt("id")
                                        , jsonObject1.getString("operation_type")
                                        , jsonObject1.getString("operation_category")
                                        , jsonObject1.getString("account")
                                        , jsonObject1.getLong("value")
                                        , jsonObject1.getString("currency")
                                        , jsonObject1.getString("description")
                                        , jsonObject1.getInt("timestamp")
                                );
                                FakeOperations.addOperation(operationItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        EventBus.getDefault().post(new APIFeedLoadSuccessEvent(FakeOperations.getInstance().getOperationItems())); // response from server
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new APIFeedLoadFailEvent(error));
                Log.i("REST loadItems error", error.toString());
            }
        }
        ));
        getBalance(userId);
    }

    public int getBalance(int appLoggedUserId) {
        //TODO: change hardcoded exchange rates!!!
        int rubToUsdExchangeRate = 62;
        int rubToEurExchangeRate = 66;
        RESTApi.getInstance().getBalance(appLoggedUserId, "RUB");
        RESTApi.getInstance().getBalance(appLoggedUserId, "USD");
        RESTApi.getInstance().getBalance(appLoggedUserId, "EUR");

        int result = App.getInstance().getBalance("RUB") + App.getInstance().getBalance("USD") * rubToUsdExchangeRate + App.getInstance().getBalance("EUR") * rubToEurExchangeRate;
        Log.i("Totalbalance = ", "" + result);
        return result;
    }

    private void getBalance(int appLoggedUserId, String currency) {
        final String currency1 = currency;
        try {
            String url = API_URL + "operations/balance/total";
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", appLoggedUserId);
            jsonObject.put("currency", currency);


            mRequestQueue.add(new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int sum = response.getInt("sum");
                                App.getInstance().setBalance(currency1, sum);
                                Log.i("App " + currency1 + " = ", "" + sum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                Log.i("RESTApi load Feed()", " is running");
                RESTApi.getInstance().loadItems(App.getInstance().getAppLoggedUserId());
                RESTApi.getInstance().getBalance(App.getInstance().getAppLoggedUserId());
//                EventBus.getDefault().post(new APIFeedLoadSuccessEvent(FakeOperations.getInstance().getOperationItems()));
            }
        }, 1000);
    }

    public void deleteItems(long operationId) {
        final String url = API_URL + "operations/delete/";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("operationId", operationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("deleteItems ", jsonObject.toString());
        mRequestQueue.add(new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RESTApi.getInstance().loadItems(App.getInstance().getAppLoggedUserId());
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

    public void updateItem(int ownerId, long operationId, String operationType, String operationCategory, String account, long value, String currency, String description, int timestamp) {
        Log.i("updateItem ", "started");
        final String url = API_URL + "operations/edit/";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ownerId", ownerId);
            jsonObject.put("operationId", operationId);
            jsonObject.put("operation_Type", operationType);
            jsonObject.put("operation_Category", operationCategory);
            jsonObject.put("account", account);
            jsonObject.put("value", value);
            jsonObject.put("currency", currency);
            jsonObject.put("description", description);
            jsonObject.put("timestamp", timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueue.add(new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RESTApi.getInstance().loadItems(App.getInstance().getAppLoggedUserId());
//                        EventBus.getDefault().post(new APIFeedLoadSuccessEvent(FakeOperations.getInstance().getOperationItems())); // response from server
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new APIFeedLoadFailEvent(error));
                Log.i("REST edit item error", error.toString());
            }
        }
        ));
    }

    public void newItem(String operationType, String operationCategory, String account, long value, String currency, String description, int timestamp) {
        final String url = API_URL + "operations/new/";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ownerId", App.getInstance().getAppLoggedUserId());
            jsonObject.put("operation_Type", operationType);
            jsonObject.put("operation_Category", operationCategory);
            jsonObject.put("account", account);
            jsonObject.put("value", value);
            jsonObject.put("currency", currency);
            jsonObject.put("description", description);
            jsonObject.put("timestamp", timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueue.add(new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RESTApi.getInstance().loadItems(App.getInstance().getAppLoggedUserId());
//                        EventBus.getDefault().post(new APIFeedLoadSuccessEvent(FakeOperations.getInstance().getOperationItems())); // response from server
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new APIFeedLoadFailEvent(error));
                Log.i("REST edit item error", error.toString());
            }
        }
        ));
    }
}
