package com.android.internal.telephony;

import android.os.Bundle;
    interface ITelephony{
        boolean endcall();
        void dial(String number);
        void answerRingingCall();
        }
