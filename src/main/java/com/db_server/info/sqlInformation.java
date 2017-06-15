package com.db_server.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NAVY on 2017/6/3.
 */
public class sqlInformation {
    @SerializedName("ID")
    private String ID;

    @SerializedName("CUSTOMER_SERVICE")
    private String CUSTOMER_SERVICE;

    @SerializedName("PLATER_NUMBER")
    private String PLATER_NUMBER;

    @SerializedName("VIN_NO")
    private String VIN_NO;

    @SerializedName("CUSTUMER_NAME")
    private String CUSTUMER_NAME;

    @SerializedName("BRAND")
    private String BRAND;

    @SerializedName("ENGINE")
    private String ENGINE;

    @SerializedName("PHONE")
    private String PHONE;

    @SerializedName("IDCARD")
    private String IDCARD;

    @SerializedName("COMMERCIAL")
    private long COMMERCIAL;

    @SerializedName("COMPULSORY")
    private long COMPULSORY;

    @SerializedName("REGISTER")
    private String REGISTER;

    @SerializedName("ADDRESS")
    private String ADDRESS;

    @SerializedName("STATE")
    private String STATE;

    @SerializedName("REMARKS")
    private String REMARKS;

    public String getID() {
        return ID;
    }

    public String getCUSTOMER_SERVICE() {
        return CUSTOMER_SERVICE;
    }

    public String getPLATER_NUMBER() {
        return PLATER_NUMBER;
    }

    public String getVIN_NO() {
        return VIN_NO;
    }

    public String getCUSTUMER_NAME() {
        return CUSTUMER_NAME;
    }

    public String getBRAND() {
        return BRAND;
    }

    public String getENGINE() {
        return ENGINE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getIDCARD() {
        return IDCARD;
    }

    public long getCOMMERCIAL() {
        return COMMERCIAL;
    }

    public long getCOMPULSORY() {
        return COMPULSORY;
    }

    public String getREGISTER() {
        return REGISTER;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getSTATE() {
        return STATE;
    }

    public String getREMARKS() {
        return REMARKS;
    }
}
