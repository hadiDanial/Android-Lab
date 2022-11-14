package com.example.ex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText operand1, operand2;
    private TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.INFO, "Ex2", "On Create");
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        operand1 = findViewById(R.id.OP1);
        operand2 = findViewById(R.id.OP2);
        resultText = findViewById(R.id.resultText);
    }

    public void onOperandClick(View view)
    {
        String op1 = operand1.getText().toString(), op2 = operand2.getText().toString();
        int num1, num2, result = 0;
        // Close the keyboard
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(op1.equals(""))
        {
            Toast.makeText(getApplicationContext(), (CharSequence) "Operand 1 is empty!", Toast.LENGTH_SHORT).show();
        }
        else if(op2.equals(""))
        {
            Toast.makeText(getApplicationContext(), (CharSequence) "Operand 2 is empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            num1 = Integer.parseInt(op1);
            num2 = Integer.parseInt(op2);
            boolean flag = true;
            switch (view.getId())
            {
                case R.id.addButton:
                    result = num1 + num2;
                    break;
                case R.id.subtractButton:
                    result = num1 - num2;
                    break;
                case R.id.multiplyButton:
                    result = num1 * num2;
                    break;
                case R.id.divideButton:
                    if(num2 == 0)
                    {
                        flag = false;
                        Toast.makeText(getApplicationContext(), (CharSequence) "Can't divide by zero!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        result = num1 / num2;
                    break;
            }
            Log.println(Log.INFO, "Ex2", String.valueOf(result));
            if(flag)
                resultText.setText(String.valueOf(result));
            else
                resultText.setText("");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.println(Log.INFO, "Ex2", "On Start");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.println(Log.INFO, "Ex2", "On Save Instance State");
        outState.putString("OP1", operand1.getText().toString());
        outState.putString("OP2", operand2.getText().toString());
        outState.putString("result", resultText.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.println(Log.INFO, "Ex2", "On Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.println(Log.INFO, "Ex2", "On Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.println(Log.INFO, "Ex2", "On Stop");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.println(Log.INFO, "Ex2", "On Restore Instance State");
        operand1.setText(savedInstanceState.getString("OP1", ""));
        operand2.setText(savedInstanceState.getString("OP2",""));
        resultText.setText(savedInstanceState.getString("result",""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.println(Log.INFO, "Ex2", "On Destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.println(Log.INFO, "Ex2", "On Restart");
    }
}