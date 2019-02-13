package com.example.doctorhealthapp2.Common;

import com.example.doctorhealthapp2.Remote.FCMClient;
import com.example.doctorhealthapp2.Remote.IFCMService;

public class Common {



    public static String currentToken = "";
    public static final String doctor_tb1 ="Doctors";
    public static final String user_doctor_tb1 ="DoctorInformation";
    public static final String user_patient_tb1 ="PatientsInformation";
    public static final String pickup_request_tb1 ="pickupRequest";
    public static final String token_tb1 ="Tokens";

    public static final String fcmURL = "https://fcm.googleapis.com/ ";
    public static final String  user_field="passusr";
    public static final String  pwd_field="passpwd";




    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }


}
