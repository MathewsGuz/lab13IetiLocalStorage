package com.eci.cosw.taskplanner.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eci.cosw.taskplanner.Model.Task;
import com.eci.cosw.taskplanner.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);

        String taskInformation = "Responsible: " + task.getResponsible() +
                "\nStatus: " + task.getStatus() + "\nDescription: " + task.getDescription()
                + "\nDue date: " + task.getDueDate();

        holder.getTextView().setText(taskInformation);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            textView = viewItem.findViewById(R.id.taskText);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
