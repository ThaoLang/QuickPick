package com.example.quickpick.activities.login.commands;

import android.content.Context;
import android.widget.Toast;
import com.example.quickpick.GlobalResources;
import com.example.quickpick.activities.login.Login;
import com.example.quickpick.activities.login.constants.LoginConstants;
import com.example.quickpick.my_intefaces.Command;
import com.example.quickpick.repositories.FirebaseRepository;

public class LoginButtonCommand implements Command {

    private String account, password;
    private Runnable successfulReaction, failureReaction;
    private Context baseContext;


    public LoginButtonCommand()
    {
        account = null;
        password = null;
        successfulReaction = null;
        failureReaction = null;
        baseContext = null;
    }

    public LoginButtonCommand(Context context)
    {
        this.account = null;
        this.password = null;
        this.successfulReaction = null;
        this.failureReaction = null;
        this.baseContext = context;
    }

    public LoginButtonCommand setAccount(String account)
    {
        this.account = account;
        return this;
    }

    public LoginButtonCommand setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public LoginButtonCommand setSuccessfulReaction(Runnable successfulReaction) {
        this.successfulReaction = successfulReaction;
        return this;
    }

    public LoginButtonCommand setFailureReaction(Runnable failureReaction) {
        this.failureReaction = failureReaction;
        return this;
    }

    public LoginButtonCommand setBaseContext(Context context) {
        this.baseContext = context;
        return this;
    }

    @Override
    public void execute() {
        if(baseContext == null)
        {
            throw new NullPointerException("Provide context of the activity");
        }
        else if(account == null || account.isEmpty())
        {
            Toast.makeText(baseContext, LoginConstants.EMPTY_EMAIL, Toast.LENGTH_LONG).show();
            return;
        }
        else if(password == null || password.isEmpty())
        {
            Toast.makeText(baseContext, LoginConstants.EMPTY_PASSWORD, Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseRepository firebaseRepository = ((GlobalResources) ((Login) baseContext).getApplication()).getFirebaseRepository();
        firebaseRepository.loginByEmailAndPassword(account, password, successfulReaction, failureReaction);
    }
}
