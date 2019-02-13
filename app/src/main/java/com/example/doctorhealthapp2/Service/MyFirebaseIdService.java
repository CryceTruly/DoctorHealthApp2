package com.example.doctorhealthapp2.Service;

import com.example.doctorhealthapp2.Common.Common;
import com.example.doctorhealthapp2.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Common.currentToken = refreshedToken;     ... no neeed
        updateTokenToserver(refreshedToken); //when have reffreshed token , we need to update to firebase
    }

    private void updateTokenToserver(String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens =db.getReference(Common.token_tb1);

        Token token = new Token(refreshedToken);
        if (FirebaseAuth.getInstance().getCurrentUser() !=null)  //if already login must update token
            tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(token);
    }
}
