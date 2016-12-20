package com.rabigol.wowmoney.models;

public class OperationItem {
    private static long uniqueOperationId = 0; // unique operation id and count of operations
    private long id;
    private long ownerId = 1; //Operation owner. Needed in server
    private int timestamp; //for date in operations
    private String operationType; // one of third types of operation: income, outcome, transfer
    private String operationCategory; // category of operation, for example: clothes, food, car etc
    private String operationPic; // pic for operation
    private String account; // name of account
    private long value;
    private String currency;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOperationCategory(String operationCategory) {
        this.operationCategory = operationCategory;
    }

    public void setOperationPic(String operationPic) {
        this.operationPic = operationPic;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static void setUniqueOperationId(long uniqueOperationId) {
        OperationItem.uniqueOperationId = uniqueOperationId;
    }

    public void setValue(long value) {
        this.value = value;
    }

    private String description;

    public OperationItem(int timestamp, String operationType, String operationCategory, String operationPic, String account, long value, String currency, String description) {

        uniqueOperationId++;
        this.ownerId = 1;
        this.id = uniqueOperationId;
        this.timestamp = timestamp;
        this.operationType = operationType;
        this.operationCategory = operationCategory;
        this.operationPic = operationPic;
        this.account = account;
        this.value = value;
        this.currency = currency;
        this.description = description;
    }

    public OperationItem(String operationType, String operationCategory, String account, long value, String currency, String description) {

        uniqueOperationId++;
        this.ownerId = 1;
        this.id = uniqueOperationId;
        this.timestamp = (int) (System.currentTimeMillis() / 1000);
        this.operationType = operationType;
        this.operationCategory = operationCategory;
        this.account = account;
        this.value = value;
        this.currency = currency;
        this.description = description;
    }

    //Constructor for loadFeed
    public OperationItem(int ownerId, int operationId, String operationType, String operationCategory, String account, long value, String currency, String description, int timestamp) {
        this.ownerId = ownerId;
        this.id = operationId;
        this.timestamp = timestamp;
        this.operationType = operationType;
        this.operationCategory = operationCategory;
        this.account = account;
        this.value = value;
        this.currency = currency;
        this.description = description;
    }

    public String getAccount() {
        return account;
    }

    public String getCurrency() {
        return currency;
    }

    public long getId() {
        return id;
    }

    public String getOperationCategory() {
        return operationCategory;
    }

    public String getOperationPic() {
        return operationPic;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getDescription() {
        return description;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public long getValue() {
        return value;
    }
}
