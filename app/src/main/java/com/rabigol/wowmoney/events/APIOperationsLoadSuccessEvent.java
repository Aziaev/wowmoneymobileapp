package com.rabigol.wowmoney.events;

import com.rabigol.wowmoney.models.OperationItem;

import java.util.ArrayList;

/**
 * Created by Artur.Ziaev on 02.11.2016.
 */

public class APIOperationsLoadSuccessEvent {
    private ArrayList<OperationItem> operations;

    public APIOperationsLoadSuccessEvent(ArrayList<OperationItem> operations){
        this.operations = operations;
    }

    public ArrayList<OperationItem> getOperations(){
        return operations;
    }
}
