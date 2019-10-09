package com.ez08.trade.ui.query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ez08.trade.R;
import com.ez08.trade.tools.DatePickerCallback;

public class DatePickerFragment extends DialogFragment {

    int type;
    int year;
    int month;
    int day;
    public static DatePickerFragment newInstance(int type, int year, int month, int day) {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        args.putInt("type",type);
        args.putInt("year",year);
        args.putInt("month",month);
        args.putInt("day",day);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        year = getArguments().getInt("year");
        month = getArguments().getInt("month");
        day = getArguments().getInt("day");

        View view = inflater.inflate(R.layout.trade_fragment_datepicker,null);
        DatePicker datePicker = view.findViewById(R.id.date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ((DatePickerCallback)getActivity()).callback(type,year,monthOfYear,dayOfMonth);
            }
        });

        Button ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
