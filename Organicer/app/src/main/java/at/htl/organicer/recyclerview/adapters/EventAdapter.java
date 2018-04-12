package at.htl.organicer.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;



import at.htl.organicer.R;
import at.htl.organicer.database.FirebaseContext;
import at.htl.organicer.entities.Event;
import at.htl.organicer.recyclerview.viewholders.EventViewHolder;

/**
 * Created by phili on 05.10.2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    //todo interactionlistener
    private List<Event> eventList = new LinkedList<>();

    public EventAdapter(LinkedList<Event> events){
        this.eventList = events;
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry_layout, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        holder.updateUI(eventList.get(position));
        holder.buttonDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventList.get(position).userDislikes.contains(FirebaseContext.getInstance().mAuth.getUid()))
                    FirebaseContext.getInstance().showMessage("Downvote hinzugefügt!");
                eventList.get(position).addDislike(FirebaseContext.getInstance().mAuth.getUid());
                FirebaseContext.getInstance().updateEvent(eventList.get(position));

            }
        });
        holder.buttonUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventList.get(position).userUpvotes.contains(FirebaseContext.getInstance().mAuth.getUid()))
                    FirebaseContext.getInstance().showMessage("Upvote hinzugefügt!");
                eventList.get(position).addUpvote(FirebaseContext.getInstance().mAuth.getUid());
                FirebaseContext.getInstance().updateEvent(eventList.get(position));

            }
        });
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
