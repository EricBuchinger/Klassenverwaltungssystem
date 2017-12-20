package at.htl.organicer.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.updateUI(eventList.get(position));
        //todo onclicklistener
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
