package com.jingyu.otm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jingyu.otm.R;
import com.jingyu.otm.activity.HomeActivity;
import com.jingyu.otm.databinding.FragmentUpdateBinding;
import com.jingyu.otm.db.User;
import com.jingyu.otm.repository.RunRepository;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {
    private static final String TAG = "UpdateFragment";
    FragmentUpdateBinding binding;
    private RunRepository repository;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Long Id = ((HomeActivity)getActivity()).giveMeUserId();
        Log.d(TAG, "now the user is" + Id.toString());

        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editUsername.toString();
                Double height = Double.parseDouble(binding.editHeight.toString());
                Integer age = Integer.parseInt(binding.editAge.toString());
                Double weight = Double.parseDouble(binding.editWeight.toString());
                String password = binding.editPassword.toString();


                try {
                    User user = repository.getUser(Id);
                    if (!name.isEmpty()) {
                        user.name = name;
                    }

                    if (height != null) {
                        user.height = height;
                    }

                    if (age != null) {
                        user.age = age;
                    }

                    if (weight != null) {
                        user.weight = weight;
                    }

                    if (!password.isEmpty()) {
                        user.password = name;
                    }
                    repository.updateUser(user);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        repository = new RunRepository();
        binding = FragmentUpdateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}