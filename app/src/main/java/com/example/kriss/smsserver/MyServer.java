package com.example.kriss.smsserver;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class MyServer extends NanoHTTPD {
    private final static int PORT = 8080;

    public MyServer() throws IOException {
        super(PORT);
        start();
    }

    @Override
    public Response serve(IHTTPSession session) {

        String msg = "<html><body><h1>Sms server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("number") == null && parms.get("message") == null) {

            msg += "<form action='?' method='get'>\n  " +
                    "<p>Number: <input type='text' name='number'>" +
                    "<input type='text' name='message'></p>\n" +
                    "<input type='submit' value='Submit'>" +
                    "</form>\n";
        } else {
            String phoneNumber = parms.get("number");
            String message = parms.get("message");
            msg += "<p>Sent, " + message +" to, "+ phoneNumber+"!</p>";
            Log.e(phoneNumber,message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
        return newFixedLengthResponse(msg + "</body></html>\n");
    }


}
