package com.example.doctorhealthapp2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomSheetPatientFragment extends BottomSheetDialogFragment {

  String mTag;
  public static  BottomSheetPatientFragment newInstance(String tag) {

      BottomSheetPatientFragment f = new BottomSheetPatientFragment();
      Bundle args = new Bundle();
      args.putString("TAG",tag);
      f.setArguments(args);
      return f;

  }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getString("TAG");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_patient,container,false);
        return view;


    }
}
