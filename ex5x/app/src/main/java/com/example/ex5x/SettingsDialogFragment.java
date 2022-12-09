package com.example.ex5x;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsDialogFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener {

    private SettingsDialogInterface listener;
    private SeekBar seekBar;
    private int progress = 0;
    private String format;
    private TextView exampleText;

    public SettingsDialogFragment() {
        // Required empty public constructor
    }

    public static SettingsDialogFragment newInstance(int progress) {
        SettingsDialogFragment fragment = new SettingsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("progress", progress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.listener = (SettingsDialogFragment.SettingsDialogInterface)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'SettingsDialogInterface'");
        }
        super.onAttach(context);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.seekbar, null);
        exampleText = view.findViewById(R.id.seekbarExampleText);
        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        progress = getArguments().getInt("progress", 0);
        seekBar.setProgress(progress);
        alertDialogBuilder.setTitle("Settings");
        alertDialogBuilder.setMessage("Choose precision level");
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
//                listener.ExitApplication();
                Log.println(Log.INFO, "EX6", "SUCCESS, " + progress);
                listener.UpdateSeekbarProgress(progress);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        progress = seekBar.getProgress();
        format = "%." + progress + "f";
        exampleText.setText("Example: " + String.format(format, 123.45678));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface SettingsDialogInterface {
        public void UpdateSeekbarProgress(int progress);
    }
}