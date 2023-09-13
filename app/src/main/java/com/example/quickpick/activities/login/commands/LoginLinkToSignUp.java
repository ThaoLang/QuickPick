package com.example.quickpick.activities.login.commands;

import android.content.Context;
import android.content.Intent;

import com.example.quickpick.activities.login.Login;
import com.example.quickpick.activities.signup.Signup;
import com.example.quickpick.my_intefaces.Command;
public class LoginLinkToSignUp implements Command {

    private Context baseContext;

    public LoginLinkToSignUp()
    {
        baseContext = null;
    }

    public LoginLinkToSignUp setBaseContext(Context context)
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
        Intent signupIntent = new Intent(baseContext, Signup.class);
        baseContext.startActivity(signupIntent);
        ((Login) baseContext).finish();

    }
}

