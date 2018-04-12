package at.htl.organicer.recyclerview.viewholders;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.htl.organicer.R;
import at.htl.organicer.entities.Event;

/**
 * Created by phili on 05.10.2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    TextView tv1, tv2;
    public Button buttonUpVote;
    public Button buttonDownVote;
    public EventViewHolder(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(R.id.tv_event_name);
        tv2 = itemView.findViewById(R.id.tv_subject);
        buttonUpVote = itemView.findViewById(R.id.buttonupvote);
        buttonDownVote = itemView.findViewById(R.id.buttondownvote);
    }

    public void updateUI(Event event){
        tv1.setText(event.getName());
        tv2.setText(event.getSubject());
        if(event.isConfirmed()){
            buttonUpVote.setVisibility(View.INVISIBLE);
            buttonDownVote.setVisibility(View.INVISIBLE);
            itemView.setBackgroundColor(Color.GREEN);
        }
    }
}
