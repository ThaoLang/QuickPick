package com.example.quickpick.activities.signup.commands;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.quickpick.GlobalResources;
import com.example.quickpick.activities.signup.Signup;
import com.example.quickpick.activities.signup.constants.SignupConstants;
import com.example.quickpick.my_intefaces.Command;
import com.example.quickpick.repositories.FirebaseRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupButtonCommand implements Command {

    private String email, password, phone,username;
    private Runnable successfulReaction, failureReaction;
    private Context baseContext;


    public SignupButtonCommand()
    {
        username=null;
        email = null;
        password = null;
        phone = null;
        successfulReaction = null;
        failureReaction = null;
        baseContext = null;
    }

    public SignupButtonCommand(Context context)
    {
        this.username=null;
        this.email = null;
        this.password = null;
        this.phone = null;
        this.successfulReaction = null;
        this.failureReaction = null;
        this.baseContext = context;
    }

    public SignupButtonCommand setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public SignupButtonCommand setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public SignupButtonCommand setPhone(String phone)
    {
        this.phone = phone;
        return this;
    }

    public SignupButtonCommand setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public SignupButtonCommand setSuccessfulReaction(Runnable successfulReaction) {
        this.successfulReaction = successfulReaction;
        return this;
    }

    public SignupButtonCommand setFailureReaction(Runnable failureReaction) {
        this.failureReaction = failureReaction;
        return this;
    }

    public SignupButtonCommand setBaseContext(Context context)
    {
        this.baseContext = context;
        return this;
    }

    @Override
    public void execute() {
        Log.e("SIGN UP",baseContext.toString());

        if(baseContext == null)
        {
            throw new NullPointerException("Provide context of the activity");
        }
        else if(username == null || username.isEmpty())
        {
            Toast.makeText(baseContext, SignupConstants.EMPTY_USERNAME, Toast.LENGTH_LONG).show();
            return;
        }
        else if(email == null || email.isEmpty())
        {
            Toast.makeText(baseContext, SignupConstants.EMPTY_EMAIL, Toast.LENGTH_LONG).show();
            return;
        }
        else if(!isValidEmail(email))
        {
            Toast.makeText(baseContext, SignupConstants.INVALID_EMAIL_FORMAT, Toast.LENGTH_LONG).show();
            return;
        }
        else if(password == null || password.isEmpty())
        {
            Toast.makeText(baseContext, SignupConstants.EMPTY_PASSWORD, Toast.LENGTH_LONG).show();
            return;
        }
        else if(password.length() < 8)
        {
            Toast.makeText(baseContext, SignupConstants.INVALID_PASSWORD_LENGTH, Toast.LENGTH_LONG).show();
            return;
        }
        else if(phone == null || phone.isEmpty())
        {
            Toast.makeText(baseContext, SignupConstants.EMPTY_PHONE, Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseRepository firebaseRepository =  ((GlobalResources) ((Signup) baseContext).getApplication()).getFirebaseRepository();
        firebaseRepository.signupByEmailAndPassword(email, password, phone,username,successfulReaction, failureReaction);
    }

    private boolean isValidEmail(String email){
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }
}
