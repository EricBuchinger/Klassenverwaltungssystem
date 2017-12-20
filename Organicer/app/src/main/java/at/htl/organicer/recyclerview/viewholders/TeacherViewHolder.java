package at.htl.organicer.recyclerview.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.htl.organicer.entities.Event;
import at.htl.organicer.entities.Teacher;

/**
 * Created by phili on 05.10.2017.
 */

public class TeacherViewHolder extends RecyclerView.ViewHolder {
    TextView tv1, tv2;
    public TeacherViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(android.R.id.text1);
        tv2 = itemView.findViewById(android.R.id.text2);
    }

    public void updateUI(Teacher teacher){
        tv1.setText(String.format("%s: %s %s", teacher.getName(), teacher.getFirstName(), teacher.getLastName()));
        tv2.setText(String.format("Active: %s", teacher.isActive()));
    }
}
