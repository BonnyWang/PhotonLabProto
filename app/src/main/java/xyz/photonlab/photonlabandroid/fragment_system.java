package xyz.photonlab.photonlabandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


public class fragment_system extends FullScreenFragment {

    Button btBack;

    ConstraintLayout clPrivacy;
    TextView tvDeviceName;


    TinyDB tinyDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tinyDB = new TinyDB(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_system, container, false);

        btBack = view.findViewById(R.id.backButton_System);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        clPrivacy = view.findViewById(R.id.policy);
        clPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.photonlab.xyz/privacypolicy.html"));
                startActivity(browserIntent);
            }
        });

        tvDeviceName = view.findViewById(R.id.tvDeviceName);
        if (tinyDB.getString("LocalIp").equals("")) {
            // No change
            // No device -B
        } else {
            String ipAddr = tinyDB.getString("LocalIp");
            tvDeviceName.setText(ipAddr.trim(), TextView.BufferType.SPANNABLE);
////            tvMacAddr.setText();
        }

        return view;
    }

}
