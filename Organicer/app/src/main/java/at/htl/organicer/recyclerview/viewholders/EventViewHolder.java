package at.htl.organicer.recyclerview.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.htl.organicer.entities.Event;

/**
 * Created by phili on 05.10.2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    TextView tv1, tv2;
    public EventViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(android.R.id.text1);
        tv2 = itemView.findViewById(android.R.id.text2);
    }

    public void updateUI(Event event){
        tv1.setText(String.format("%d: %s", event.getId(), event.getName()));
        tv2.setText(event.getDescription());
    }
}
