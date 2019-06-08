package com.citta.lucidkanban.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citta.lucidkanban.R;
import com.citta.lucidkanban.activities.TaskDetailActivity;
import com.citta.lucidkanban.data.Storage;
import com.citta.lucidkanban.model.Task;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class TasksFragment extends Fragment {

    private RecyclerView tasksList;
    private Context taskFragmentContext;
    Storage storage;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskFragmentContext = context;
        storage= new Storage(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tasks_fragment, container, false);
        tasksList = view.findViewById(R.id.tasksList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {

        // Create an instance of SectionedRecyclerViewAdapter
        final SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        // Add your Sections
        sectionAdapter.addSection(new MySection());

/*        GridLayoutManager glm = new GridLayoutManager(taskFragmentContext, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });*/

        // Set up your RecyclerView with the SectionedRecyclerViewAdapter
        tasksList.setLayoutManager(new LinearLayoutManager(taskFragmentContext, LinearLayoutManager.VERTICAL, false));
        tasksList.setAdapter(sectionAdapter);
    }

    //
    // region inner structure
    //

    class MySection extends StatelessSection {
        List<Task> itemList = (List<Task>) storage.retrieve("File", Storage.Directory.Documents);



        public MySection() {
            // call constructor with layout resources for this Section header and items
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.tasks_item)
                    .build());
        }

        @Override
        public int getContentItemsTotal() {
            return itemList.size(); // number of items of this section
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            // return a custom instance of ViewHolder for the items of this section
            return new MyItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

            // bind your view here
            itemHolder.hydrate(itemList.get(position));
        }
    }


    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTitleLabel;
        private final TextView taskDescLabel;
        private final TextView taskDateLabel;

        public MyItemViewHolder(View itemView) {
            super(itemView);


            taskTitleLabel = itemView.findViewById(R.id.taskTitle);
            taskDescLabel = itemView.findViewById(R.id.taskDescription);
            taskDateLabel = itemView.findViewById(R.id.taskDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(taskFragmentContext, TaskDetailActivity.class);
                    intent.putExtra("itemNumber", getAdapterPosition());
                    intent.putExtra("isUserClickedExistingTask", true);
                    startActivity(intent);
                }
            });
        }

        public void hydrate(Task task) {
            taskTitleLabel.setText(task.taskTitle);
            taskDescLabel.setText(task.taskDescription);
            taskDateLabel.setText(task.taskDate);
        }
    }

    // endregion

}
