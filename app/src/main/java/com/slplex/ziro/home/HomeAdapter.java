package com.slplex.ziro.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.slplex.ziro.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    List<ToDoModel> toDoModels = new ArrayList<>();
    HomeInterface homeInterface;
    public HomeAdapter(HomeInterface homeInterface)
    {
        this.homeInterface=homeInterface;

    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView title,date;
        CheckBox checkBox;

        public HomeViewHolder(View v) {
            super(v);
            date=v.findViewById(R.id.date);
            title=v.findViewById(R.id.title);
            checkBox=v.findViewById(R.id.checkbox);
        }
    }

    @Override
    public int getItemCount() {
        return toDoModels.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ToDoModel toDoModel=toDoModels.get(position);
        holder.title.setText(toDoModel.getTitle());
        holder.date.setText(toDoModel.getDate());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                homeInterface.onItemChecked(toDoModel,position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.onItemClicked(toDoModel,position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date, null));

    }

    public List<ToDoModel> getToDoModels() {
        return toDoModels;
    }

    public void setToDoModels(List<ToDoModel> toDoModels) {
        this.toDoModels = toDoModels;
    }
    public interface HomeInterface{
        public void onItemClicked(ToDoModel model,int pos);
        public void onItemChecked(ToDoModel model,int pos);
    }
}
