package com.aditya.pictattendanceTry.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aditya.pictattendanceTry.R;
import com.aditya.pictattendanceTry.model.Update;
import com.aditya.pictattendanceTry.utils.Utils;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder> {

    private List<Update> list;
    Integer red, green;

    public UpdateAdapter(List<Update> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UpdateAdapter.UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UpdateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.update_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAdapter.UpdateViewHolder updateViewHolder, int i) {
        Update update = list.get(i);
        if(red==null) {
            red = updateViewHolder.itemView.getContext().getResources().getColor(R.color.red);
            green = updateViewHolder.itemView.getContext().getResources().getColor(R.color.green);
        }
        updateViewHolder.title.setText(update.getSubjectName());
        updateViewHolder.prev.setText(String.format("%s%%", String.valueOf(update.getPrev())));
        updateViewHolder.curr.setText(String.format("%s%%", String.valueOf(update.getCurr())));
        double diff = Utils.round(update.getCurr()-update.getPrev(),2);
        if(diff>=0) {
         updateViewHolder.change.setTextColor(green);
            updateViewHolder.change.setText(String.format("+%s%%", String.valueOf(diff)));
        }
            else {
            updateViewHolder.change.setTextColor(red);

            updateViewHolder.change.setText(String.format("%s%%", String.valueOf(diff)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class UpdateViewHolder extends RecyclerView.ViewHolder{

        TextView title, prev, curr, change;

         UpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            change = itemView.findViewById(R.id.change);
            prev = itemView.findViewById(R.id.prev);
            curr = itemView.findViewById(R.id.curr);
        }
    }
}
