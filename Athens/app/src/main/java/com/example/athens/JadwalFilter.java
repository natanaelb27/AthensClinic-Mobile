package com.example.athens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class JadwalFilter extends Fragment {
    private Integer[] fotoJadwal = {R.drawable.jadwalfull, R.drawable.jadwalakbar, R.drawable.jadwalbudi, R.drawable.jadwalsiti, R.drawable.jadwalnanda, R.drawable.jadwaltuti};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_jadwal_filter, container, false);
        int parameterIndex = getArguments().getInt("index");
        ImageView imgFilter = (ImageView) view.findViewById(R.id.imgFilter);
        imgFilter.setImageResource(fotoJadwal[parameterIndex]);
        return view;
    }
}
