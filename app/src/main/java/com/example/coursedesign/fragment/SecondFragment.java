package com.example.coursedesign.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.MainActivity;
import com.example.coursedesign.widget.DetialGallery;

import java.util.logging.Handler;

/**
 * Created by yuanqiang on 2016/3/17.
 */
public class SecondFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,container,false);
        ((MainActivity) getActivity()).setToolbar(false, R.string.fragment_second);
   return view;
}
}
