package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	private String resText;

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
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.frag_b, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		resultText = view.findViewById(R.id.resultText);
		seekbar = view.findViewById(R.id.seekBar);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				format = "%." + seekbar.getProgress() + "f";
				seekbarExampleText.setText("Example: " + String.format(format, 123.45678));
				if(!resultText.getText().toString().equals(""))
					resultText.setText(resText + String.format(format, result));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		seekbarExampleText = view.findViewById(R.id.seekbarExampleText);
		super.onViewCreated(view, savedInstanceState);
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
		resText = num1 + " " + operation + " " + num2 + " =\n\n";
		resultText.setText(resText + String.format(format, result));
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.options, menu);
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			menu.findItem(R.id.backButton).setVisible(false);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId() == R.id.settingsMenu)
			showSettingsDialog();
		else
			listener.backToFragA();
		return super.onOptionsItemSelected(item);
	}

	private void showSettingsDialog() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		SettingsDialogFragment settingsDialog = SettingsDialogFragment.newInstance(seekbar.getProgress());
		settingsDialog.show(fm, "fragment_settings");

	}

	public void updateSeekbarProgress(int progress) {
		seekbar.setProgress(progress);
		format = "%." + progress + "f";
		resultText.setText(resText + String.format(format, result));
	}

	public interface FragBListener{
		//put here methods you want to utilize to communicate with the hosting activity
		public void backToFragA();
	}

}
