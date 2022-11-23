package com.example.ex4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private EditText operand1, operand2;
    private TextView resultText, seekbarExampleText;
    private Button addBtn, subBtn, mulBtn, divBtn, clearBtn;
    private SeekBar seekbar;
    private String format = "%.0f";
    private float result = 0;

    private boolean dynamic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.INFO, "Ex2", "On Create");
        setContentView(R.layout.activity_main);
        findViews();
        if(dynamic)
            addSeekbarDynamically();
    }

    private void findViews() {
        operand1 = findViewById(R.id.OP1);
        operand2 = findViewById(R.id.OP2);
        resultText = findViewById(R.id.resultText);
        addBtn = findViewById((R.id.addButton));
        subBtn = findViewById((R.id.subtractButton));
        mulBtn = findViewById((R.id.multiplyButton));
        divBtn = findViewById((R.id.divideButton));
        HandleTextChange textChangeHandler = new HandleTextChange();
        operand1.addTextChangedListener(textChangeHandler);
        operand2.addTextChangedListener(textChangeHandler);
        clearBtn = findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operand1.setText("");
                operand2.setText("");
                resultText.setText("");
                result = 0;
            }
        });
        if(!dynamic)
        {
            seekbar = findViewById(R.id.seekBar);
            seekbar.setOnSeekBarChangeListener(this);
            seekbarExampleText = findViewById(R.id.seekbarExampleText);
        }
    }

    private void addSeekbarDynamically() {
        LinearLayout parentLayout = findViewById(R.id.calculatorLayout);
        View child = getLayoutInflater().inflate(R.layout.seekbar, parentLayout, false);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.spaceBeforeSeekbar);
//        rlp.setMargins(0,1400, 0 , 0);
        parentLayout.addView(child, rlp);
        seekbar = child.findViewById(R.id.seekBar);
//        seekbar.setProgress(4);
        seekbar.setOnSeekBarChangeListener(this);
        seekbarExampleText = findViewById(R.id.seekbarExampleText);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        format = "%." + seekbar.getProgress() + "f";
        String ex = dynamic ? "Dynamic Example: " : "Static Example: ";
        seekbarExampleText.setText(ex + String.format(format, 123.45678));
        if(!resultText.getText().toString().equals(""))
            resultText.setText(String.format(format, result));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    private class HandleTextChange implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            String op1 = operand1.getText().toString(), op2 = operand2.getText().toString();
            if(op1.equals("") || op2.equals(""))
            {
                toggleButtons(false);
                return;
            }
            toggleButtons(true);
            if(Integer.parseInt(op2) == 0)
            {
                divBtn.setEnabled(false);
            }
        }
    }

    private void toggleButtons(boolean enabled)
    {
        addBtn.setEnabled(enabled);
        subBtn.setEnabled(enabled);
        mulBtn.setEnabled(enabled);
        divBtn.setEnabled(enabled);
    }

    public void onOperandClick(View view)
    {
        String op1 = operand1.getText().toString(), op2 = operand2.getText().toString();
        int num1, num2;
        result = 0;
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
                        result = (float)num1 / num2;
                    break;
            }
            Log.println(Log.INFO, "Ex2", String.valueOf(result));
            if(flag)
//                resultText.setText(String.valueOf(result));
                resultText.setText(String.format(format, result));
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
        outState.putString("resultText", resultText.getText().toString());
        outState.putFloat("result", result);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.println(Log.INFO, "Ex2", "On Restore Instance State");
        operand1.setText(savedInstanceState.getString("OP1", ""));
        operand2.setText(savedInstanceState.getString("OP2",""));
        resultText.setText(savedInstanceState.getString("resultText",""));
        result = savedInstanceState.getFloat("result", 0);
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