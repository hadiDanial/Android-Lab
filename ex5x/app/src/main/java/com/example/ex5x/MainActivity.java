package com.example.ex5x;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements
		FragA.FragAListener, FragB.FragBListener,
		ExitAppDialogFragment.ExitDialogInterface, SettingsDialogFragment.SettingsDialogInterface
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragB fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		FragmentContainerView fragBContainer = findViewById(R.id.fragContainer);
		if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)){
			if (fragB != null) {
				getSupportFragmentManager().beginTransaction()
						.show(fragB)
						.commit();
			}
			else {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragContainer, FragB.class,null, "FRAGB")
						//	.addToBackStack(null)
						.commit();
			}
		}
		getSupportFragmentManager().executePendingTransactions();
	}


	public void OnCalculateResult(float op1, float op2, char operation) {
		FragB fragB;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.replace(R.id.fragContainer, FragB.class, null,"FRAGB")
					.addToBackStack("BBB")
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		fragB.setValues(op1,op2,operation);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		switch (item.getItemId())
		{
			case R.id.exitMenuItem:
				showExitDialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showExitDialog() {
		FragmentManager fm = getSupportFragmentManager();
		ExitAppDialogFragment exitDialog = ExitAppDialogFragment.newInstance("Closing the application", "Are you sure?");
		exitDialog.show(fm, "fragment_exit");
	}

	@Override
	public void ExitApplication() {
		this.finishAffinity();
	}

	@Override
	public void UpdateSeekbarProgress(int progress) {
		FragB fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		if(fragB != null)
			fragB.updateSeekbarProgress(progress);
	}

	@Override
	public void backToFragA() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			if(!getSupportFragmentManager().popBackStackImmediate())
			{
				getSupportFragmentManager().beginTransaction()
						.setReorderingAllowed(true)
						.replace(R.id.fragContainer, FragA.class, null,"FRAGA")
						.addToBackStack("AAA")
						.commit();
				getSupportFragmentManager().executePendingTransactions();
			}
		}
	}
}
