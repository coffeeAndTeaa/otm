package com.jingyu.otm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingyu.otm.R;
import com.jingyu.otm.activity.HomeActivity;
import com.jingyu.otm.activity.RunActivity;
import com.jingyu.otm.activity.SuggestionActivity;
import com.jingyu.otm.databinding.FragmentRunBinding;
import com.jingyu.otm.repository.RunRepository;
import com.jingyu.otm.viewModel.RecordViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunFragment extends Fragment {
    private FragmentRunBinding binding;
    private RecordViewModel viewModel;
    private static final String TAG = "RunFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RunFragment newInstance(String param1, String param2) {
        RunFragment fragment = new RunFragment();
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
        Long Id = ((HomeActivity)getActivity()).giveMeUserId();
        Log.d(TAG, "now the user is" + Id.toString());
        RunRepository repository = new RunRepository();
        viewModel = new RecordViewModel(repository, Id);
        RecyclerView recyclerView = binding.recyclerView;
        ListAdapter adapter = new ListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getAllRuns()
                .observe(
                            getViewLifecycleOwner(),
                            runs -> {
                                Log.d(TAG, "onViewCreated: "+ runs.size());
                                adapter.setRuns(runs);
                                int count = 0;
                                for (int i = 0; i < runs.size(); i++) {
                                    count += runs.get(i).steps;
                                }
                                // This is the step count that displays on the Run Page.
                                binding.steps.setText("Total Steps: " + count);
                            }
                        );

        binding.newRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SuggestionActivity.class)
                        .putExtra("userId", Id));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        binding = FragmentRunBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}