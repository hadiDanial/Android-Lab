package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class FragA extends Fragment implements View.OnClickListener {
	FragAListener listener;
	private EditText operand1, operand2;
	private Button addBtn, subBtn, mulBtn, divBtn, clearBtn;


	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener = (FragAListener)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					context.getClass().getName() +
					" must implements the interface 'FragAListener'");
		}
		super.onAttach(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_a, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		operand1 = view.findViewById(R.id.OP1);
		operand2 = view.findViewById(R.id.OP2);
		addBtn = view.findViewById((R.id.addButton));
		subBtn = view.findViewById((R.id.subtractButton));
		mulBtn = view.findViewById((R.id.multiplyButton));
		divBtn = view.findViewById((R.id.divideButton));
		addBtn.setOnClickListener(this);
		subBtn.setOnClickListener(this);
		mulBtn.setOnClickListener(this);
		divBtn.setOnClickListener(this);
		HandleTextChange textChangeHandler = new HandleTextChange();
		operand1.addTextChangedListener(textChangeHandler);
		operand2.addTextChangedListener(textChangeHandler);
		clearBtn = view.findViewById(R.id.clearButton);
		clearBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				operand1.setText("");
				operand2.setText("");
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		String op1 = operand1.getText().toString(), op2 = operand2.getText().toString();
		char op = '+';
		int num1, num2;
		// Close the keyboard
		((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

			num1 = Integer.parseInt(op1);
			num2 = Integer.parseInt(op2);
			switch (view.getId())
			{
				case R.id.addButton:
					op = '+';
					break;
				case R.id.subtractButton:
					op = '-';
					break;
				case R.id.multiplyButton:
					op = '*';
					break;
				case R.id.divideButton:
					op = '/';
					break;
			}
			listener.OnCalculateResult(num1,num2,op);

	}

	public interface FragAListener{
		public void OnCalculateResult(float op1, float op2, char operation);
	}


	private class HandleTextChange implements TextWatcher {
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
}
