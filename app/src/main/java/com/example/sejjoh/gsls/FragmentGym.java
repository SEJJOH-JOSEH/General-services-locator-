package com.example.sejjoh.gsls;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sejjoh.gsls.Interface.ItemClickListener;
import com.example.sejjoh.gsls.models.GymModel;
import com.example.sejjoh.gsls.viewHolder.ViewHolderService;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGym extends Fragment {
    private RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;

    int color;


    public FragmentGym() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentGym(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerservices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference().child("Gym");

        FirebaseRecyclerOptions<GymModel> options = new FirebaseRecyclerOptions.Builder<GymModel>()
                .setQuery(query, GymModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<GymModel, ViewHolderService>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderService holder, int position, @NonNull GymModel model) {

                holder.post_name.setText(model.getGymName());

                Glide.with(getActivity())
                        .load(model.getGymimage())
                        .into(holder.imageView);



                holder.setItemClickListener(new ItemClickListener() {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent aiport=new Intent(getActivity(), GymDetails.class);
                        aiport.putExtra("GymId",adapter.getRef(position).getKey()); //send hotel Id to new activity
                        startActivity(aiport);

                    }
                });
            }

            @Override
            public ViewHolderService onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_service, parent, false);

                return new ViewHolderService(view);
            }


        };

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
//        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

}
