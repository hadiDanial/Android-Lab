package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class FragB extends Fragment {
	FragBListener listener;
	private TextView resultText, seekbarExampleText;
	private SeekBar seekbar;
	private String format = "%.0f";
	private float result = 0;


	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener = (FragBListener)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					getActivity().getClass().getName() +
					" must implements the interface 'FragBListener'");
		}

		super.onAttach(context);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_b, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		resultText = view.findViewById(R.id.resultText);
		addSeekbarDynamically(view);
		super.onViewCreated(view, savedInstanceState);
	}
	private void addSeekbarDynamically(View view) {
		FrameLayout parentLayout = view.findViewById(R.id.calculatorLayout);
		View child = getLayoutInflater().inflate(R.layout.seekbar, parentLayout, false);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.BELOW, R.id.spaceBeforeSeekbar);
//        rlp.setMargins(0,1400, 0 , 0);
		parentLayout.addView(child, rlp);
		seekbar = child.findViewById(R.id.seekBar);
//        seekbar.setProgress(4);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				format = "%." + seekbar.getProgress() + "f";
				seekbarExampleText.setText("Example: " + String.format(format, 123.45678));
				if(!resultText.getText().toString().equals(""))
					resultText.setText(String.format(format, result));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		seekbarExampleText = view.findViewById(R.id.seekbarExampleText);
	}
	public void setValues(float num1, float num2, char operation)
	{
		switch (operation)
		{
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '*':
				result = num1 * num2;
				break;
			case '/':
				result = (float)num1 / num2;
				break;
		}
		resultText.setText(String.format(format, result));
	}


	public interface FragBListener{
		//put here methods you want to utilize to communicate with the hosting activity

	}

}
