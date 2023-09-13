package com.example.quickpick.activities.signup.commands;

import android.content.Context;
import android.content.Intent;

import com.example.quickpick.activities.login.Login;
import com.example.quickpick.activities.signup.Signup;
import com.example.quickpick.my_intefaces.Command;

public class SignupLinkToLogin implements Command {
    private Context baseContext;

    public SignupLinkToLogin()
    {
        baseContext = null;
    }

    public SignupLinkToLogin setBaseContext(Context context)
    {
        baseContext = context;
        return this;
    }

    @Override
    public void execute() {
        if(baseContext == null)
        {
            return;
        }
        Intent loginIntent = new Intent(baseContext, Login.class);
        baseContext.startActivity(loginIntent);
        ((Signup) baseContext).finish();

    }
}

