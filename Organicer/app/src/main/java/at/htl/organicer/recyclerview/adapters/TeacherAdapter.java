package at.htl.organicer.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import at.htl.organicer.entities.Event;
import at.htl.organicer.entities.Teacher;
import at.htl.organicer.recyclerview.viewholders.EventViewHolder;
import at.htl.organicer.recyclerview.viewholders.TeacherViewHolder;

/**
 * Created by phili on 05.10.2017.
 */

public class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {
    //todo interactionlistener
    private List<Teacher> teachers = new LinkedList<>();

    public TeacherAdapter(LinkedList<Teacher> teachers){
        this.teachers = teachers;
    }
    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new TeacherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        holder.updateUI(teachers.get(position));
        //todo onclicklistener
    }


    @Override
    public int getItemCount() {
        return teachers.size();
    }
}
