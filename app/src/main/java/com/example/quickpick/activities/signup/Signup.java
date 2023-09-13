package com.example.quickpick.activities.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.activities.signup.commands.SignupButtonCommand;
import com.example.quickpick.activities.signup.commands.SignupLinkToLogin;
import com.example.quickpick.activities.signup.constants.SignupConstants;
import com.example.quickpick.constants.GlobalCommandID;
import com.example.quickpick.databinding.ActivitySignupBinding;
import com.example.quickpick.my_intefaces.ChainHandler;
import com.example.quickpick.my_intefaces.Command;
import com.example.quickpick.other_components.CommandHandler;

public class Signup extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private ChainHandler chainOfCommandHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCommandsHandler();
        binding.gotoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chainOfCommandHandler.handle(GlobalCommandID.LINK_TO_LOGIN);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupButtonCommand command=(SignupButtonCommand)chainOfCommandHandler.getCommand();
                command.setEmail(binding.emailInput.getText().toString())
                        .setPassword(binding.passwordInput.getText().toString())
                        .setPhone(binding.phoneInput.getText().toString())
                        .setUsername(binding.nameInput.getText().toString());

                chainOfCommandHandler.handle(GlobalCommandID.SIGNUP_COMMAND);
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
        CommandHandler linkToLoginHandler = new CommandHandler(GlobalCommandID.LINK_TO_LOGIN);
        Command linkToLoginCommand = new SignupLinkToLogin().setBaseContext(Signup.this);
        linkToLoginHandler.setCommand(linkToLoginCommand);

        CommandHandler signupHandler = new CommandHandler(GlobalCommandID.SIGNUP_COMMAND);
        Command signupCommand = new SignupButtonCommand(Signup.this)
//                .setEmail(binding.emailInput.getText().toString())
//                .setPassword(binding.passwordInput.getText().toString())
                .setSuccessfulReaction(successfulSignupReaction)
                .setFailureReaction(failureSignupReaction);
        signupHandler.setCommand(signupCommand);
        signupHandler.setNext(linkToLoginHandler);

        //assign ChainHandler to chainOfCommandHandler variable
        chainOfCommandHandler = signupHandler;
    }

    private Runnable successfulSignupReaction = new Runnable() {
        @Override
        public void run() {
            binding.nameInput.setText("");
            binding.emailInput.setText("");
            binding.passwordInput.setText("");
            binding.phoneInput.setText("");
            Toast.makeText(Signup.this, SignupConstants.SIGN_UP_SUCCESSFULLY, Toast.LENGTH_LONG).show();
        }
    };

    private Runnable failureSignupReaction = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(Signup.this, SignupConstants.FAIL_SIGN_UP, Toast.LENGTH_LONG).show();
        }
    };


}
