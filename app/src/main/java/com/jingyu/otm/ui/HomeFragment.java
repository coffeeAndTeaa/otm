package com.jingyu.otm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Jack adding these
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jingyu.otm.R;
import com.jingyu.otm.activity.HomeActivity;
import com.jingyu.otm.databinding.FragmentHomeBinding;
import com.jingyu.otm.db.User;
import com.jingyu.otm.repository.RunRepository;
import com.jingyu.otm.viewModel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    FragmentHomeBinding binding;
    private HomeViewModel viewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Long Id = ((HomeActivity) getActivity()).giveMeUserId();
        Log.d(TAG, "now the user is" + Id.toString());
        RunRepository repository = new RunRepository();
        viewModel = new HomeViewModel(repository);
        viewModel.setUserId(Id);

        viewModel.getUser()
                .observe(
                        getViewLifecycleOwner(),
                        user -> {
                            Log.d(TAG, "Username is " + user.name);
                            binding.displayUsername.setText(user.name);

                            Double bmi = repository.getTheBmiForUser(user);
                            String BMI = String.format("BMI: %2.1f", bmi);
                            binding.displayBMI.setText(BMI);

                            float stepCt = (float) 11.1;
                            String stepStr = String.format("Total Steps: %.0f", stepCt);
                            binding.displaySteps.setText(stepStr);
                            // TODO: Get the step count from the runs

                            //public View onCreateView(...){
                            //    tabsmain xxx = (tabsmain)getActivity();
                            //    lc.setChecked(xxx.lf_ch);
                            //}
                        }
                );





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        binding.displayDate.setText(date);
        return binding.getRoot();

    }
}