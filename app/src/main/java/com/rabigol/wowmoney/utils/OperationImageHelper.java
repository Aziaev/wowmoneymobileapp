package com.rabigol.wowmoney.utils;

import android.util.Log;

import com.rabigol.wowmoney.R;

/**
 * Created by Artur.Ziaev on 11.12.2016.
 */

public class OperationImageHelper {
    public static int giveImage(String operationCategory){
        int result = 99;
//        Log.i("giveImage", operationCategory);
        switch(operationCategory) {
            case "Salary":
                result = R.drawable.op_salary;
                break;
            case "Other outcome":
                result = R.drawable.op_other_outcome;
                break;
            case "Other income":
                result = R.drawable.op_other_income;
                break;
            case "Transport":
                result = R.drawable.op_transport;
                break;
            case "Nutrition":
                result = R.drawable.op_nutrition;
                break;
            case "Home":
                result = R.drawable.op_home;
                break;
            case "Entertainment":
                result = R.drawable.op_entertainment;
                break;
            case "Health":
                result = R.drawable.op_health;
                break;
            case "Telecommunication":
                result = R.drawable.op_telecommunication;
                break;
        }
        return result;
    }
}
