package com.example.quickpick.activities.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.activities.login.commands.LoginButtonCommand;
import com.example.quickpick.activities.login.commands.LoginLinkToSignUp;
import com.example.quickpick.activities.login.constants.LoginConstants;
import com.example.quickpick.constants.GlobalCommandID;
import com.example.quickpick.databinding.ActivityLoginBinding;
import com.example.quickpick.my_intefaces.ChainHandler;
import com.example.quickpick.my_intefaces.Command;
import com.example.quickpick.other_components.CommandHandler;

public class Login extends AppCompatActivity {
    //private CommandHandler commandHandler;
    private ChainHandler chainOfCommandHandler;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCommandsHandler();
        binding.gotoSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Button clicked");
                Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
                chainOfCommandHandler.handle(GlobalCommandID.LINK_TO_SIGNUP);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButtonCommand command=(LoginButtonCommand) chainOfCommandHandler.getCommand();
                command.setAccount(binding.emailInput.getText().toString())
                        .setPassword(binding.passwordInput.getText().toString());
                chainOfCommandHandler.handle(GlobalCommandID.LOGIN_COMMAND);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    private void initCommandsHandler()
    {
        CommandHandler linkToSignupHandler = new CommandHandler(GlobalCommandID.LINK_TO_SIGNUP);
        Command linkToSignUpCommand = new LoginLinkToSignUp().setBaseContext(Login.this);
        linkToSignupHandler.setCommand(linkToSignUpCommand);
        Log.d("LOGIN", "set command");
        CommandHandler loginHandler = new CommandHandler(GlobalCommandID.LOGIN_COMMAND);
        Command loginCommand = new LoginButtonCommand(Login.this)
                .setSuccessfulReaction(successfulLoginReaction)
                .setFailureReaction(failureLoginReaction);
        loginHandler.setCommand(loginCommand);
        loginHandler.setNext(linkToSignupHandler);

        //assign ChainHandler to chainOfCommandHandler variable
        chainOfCommandHandler = loginHandler;
    }

    private Runnable successfulLoginReaction = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_LONG).show();

//            Intent intent = new Intent(Login.this, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    };

    private Runnable failureLoginReaction = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(Login.this, LoginConstants.CANNOT_LOGIN, Toast.LENGTH_LONG).show();
        }
    };

}