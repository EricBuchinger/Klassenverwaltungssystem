package at.htl.organicer.recyclerview.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import at.htl.organicer.R;
import at.htl.organicer.entities.TimeUnit;

/**
 * Created by phili on 05.10.2017.
 */

public class TimeTableEntryViewHolder extends RecyclerView.ViewHolder {
    TextView tv_time;
    TextView tv_info;
    ImageButton imageBtnPlus;
    public TimeTableEntryViewHolder(View itemView) {
        super(itemView);
        tv_time = itemView.findViewById(R.id.tv_StartTime);
        tv_info = itemView.findViewById(R.id.tv_Info);
        imageBtnPlus = itemView.findViewById(R.id.imageBtnPlus);
    }

    public void updateUI(TimeUnit timeUnit){
        tv_time.setText(timeUnit.getStartTime() + " - " + timeUnit.getEndTime());
        //tv_info.setText(timeUnit.getEndTime());
    }
}
