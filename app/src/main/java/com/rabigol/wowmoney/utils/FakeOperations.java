package com.rabigol.wowmoney.utils;

import android.graphics.Path;
import android.util.Log;

import com.rabigol.wowmoney.models.OperationItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeOperations {
    private static ArrayList<OperationItem> operationItems = new ArrayList<>();

    // Заглушки из с названиями согласно базе данных.
    public static List<String> operationTypes = new ArrayList<>();
    public static List<String> operationCategories = new ArrayList<>();
    public static List<String> accounts = new ArrayList<>();
    public static List<String> currencies = new ArrayList<>();

    private static FakeOperations instance;

    // constructor(long id, int timestamp, String operationType, String operationCategory, String operationPic, String account, long value, String currency, String description)

    private FakeOperations() {
        operationTypes.add(0, "Income");
        operationTypes.add(1, "Outcome");
        operationTypes.add(2, "Transfer");

        currencies.add(0, "RUB");
        currencies.add(1, "USD");
        currencies.add(2, "EUR");

        accounts.add(0, "Wallet");
        accounts.add(1, "Bank card");
        accounts.add(2, "Bank account");

        operationCategories.add(0, "Salary");
        operationCategories.add(1, "Other income");
        operationCategories.add(2, "Transport");
        operationCategories.add(3, "Nutrition");
        operationCategories.add(4, "Home");
        operationCategories.add(5, "Entertainment");
        operationCategories.add(6, "Health");
        operationCategories.add(7, "Telecommunication");
        operationCategories.add(8, "Other outcome");


        for (int i = 0; i < 20; i++) {
            Log.i("TAG", "operation number " + i + " created");
            if (i % 3 == 0) {
                operationItems.add(new OperationItem((int) ((System.currentTimeMillis() / 1000) - i * 85000), operationTypes.get(2), operationCategories.get(0), "somepic" + i, accounts.get(2), 100 + i * 1100, currencies.get(0), "blablalbalbalbals"));
            } else if (i % 2 == 0) {
                operationItems.add(new OperationItem((int) ((System.currentTimeMillis() / 1000) - i * 50000), operationTypes.get(1), operationCategories.get(3), "somepic" + i, accounts.get(1), -1 * i * 550, currencies.get(1), "y0y0y0y0y0y0yxyu"));
            } else {
                operationItems.add(new OperationItem((int) ((System.currentTimeMillis() / 1000) - i * 70000), operationTypes.get(0), operationCategories.get(0), "somepic" + i, accounts.get(0), 1 * i * 2010, currencies.get(2), "ыыыы хочу ывудывьавыу"));
            }
        }
    }

    public ArrayList<OperationItem> getOperationItems() {
        return operationItems;
    }

    public void addOperationItemById() {

    }

    public static void addOperation(OperationItem operationItem) {
        operationItems.add(operationItem);
//        notifyDataSetChanged();
    }

    public static FakeOperations getInstance() {
        if (instance == null) {
            instance = new FakeOperations();
        }
        return instance;
    }

    public static OperationItem getOperationItemById(long id) {
        OperationItem operationItem = null;
        for (int i = 0; i < operationItems.size(); i++) {
            if (operationItems.get(i).getId() == id) {
                operationItem = operationItems.get(i);
            }
        }
        return operationItem;
    }

    public static void deleteOperationById(long idForDelete) {
        Iterator it = operationItems.iterator();
        while (it.hasNext()){
            OperationItem operationItem =(OperationItem) it.next();
            if (idForDelete == operationItem.getId()){
                it.remove();
            }
        }
    }

    public static List<String> getOperationCategories(){
        return operationCategories;
    }

    public static List<String> getOperationTypes(){
        return operationTypes;
    }

    public static List<String> getOperationAccounts(){
        return accounts;
    }

    public static List<String> getOperationCurrencies(){
        return currencies;
    }
}
