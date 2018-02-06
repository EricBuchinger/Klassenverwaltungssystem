package at.htl.organicer.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import at.htl.organicer.R;
import at.htl.organicer.entities.TimeUnit;
import at.htl.organicer.recyclerview.viewholders.TimeTableEntryViewHolder;

/**
 * Created by phili on 05.10.2017.
 */

public class TimeTableEntryAdapter extends RecyclerView.Adapter<TimeTableEntryViewHolder> {
    //todo interactionlistener
    private List<TimeUnit> timeUnits = new LinkedList<>();

    public TimeTableEntryAdapter(LinkedList<TimeUnit> timeUnits){
        this.timeUnits = timeUnits;
    }
    @Override
    public TimeTableEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry_layout, parent, false);
        return new TimeTableEntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TimeTableEntryViewHolder holder, int position) {
        holder.updateUI(timeUnits.get(position));
        //todo onclicklistener
    }


    @Override
    public int getItemCount() {
        return timeUnits.size();
    }
}
