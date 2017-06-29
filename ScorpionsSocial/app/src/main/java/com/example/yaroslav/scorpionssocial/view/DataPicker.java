package com.example.yaroslav.scorpionssocial.view;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.yaroslav.scorpionssocial.R;

import java.util.Date;
import java.util.Locale;

public class DataPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new DatePickerDialog(getActivity(),this,1970,0,1);
        dialog.setTitle("Вибір дати");
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText("Ok");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText dataText = (EditText) getActivity().findViewById(R.id.settings_date);
        String date = dayOfMonth+" "+month+" "+year;
        dataText.setText(date);

    }
}
