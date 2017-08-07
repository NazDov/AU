package www.uni_weimar.de.au.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUNewsFeedDefaultCategory;

/**
 * Created by nazar on 06.08.17.
 */

public class AUEventScheduleItemRecyclerViewAdapter extends RecyclerView.Adapter<AUEventScheduleItemRecyclerViewAdapter.EventScheduleItemHolder> {

    final List<AUFacultyEventSchedule> facultyEventScheduleList;

    public AUEventScheduleItemRecyclerViewAdapter(List<AUFacultyEventSchedule> facultyEventScheduleList) {
        this.facultyEventScheduleList = facultyEventScheduleList;

    }

    @Override
    public EventScheduleItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_event_chedule_recycler_view_item, parent, false);
        return new EventScheduleItemHolder(rootView);
    }

    @Override
    public void onBindViewHolder(EventScheduleItemHolder holder, int position) {
        AUFacultyEventSchedule facultyEventScheduleItem = facultyEventScheduleList.get(position);
        holder.auEventScheduleItemLocation.setText(facultyEventScheduleItem.getEventScheduleRoom());
        StringBuilder eventScheduleDateTextBuilder = new StringBuilder();
        eventScheduleDateTextBuilder.append(facultyEventScheduleItem.getEventScheduleDuration())
                .append(",")
                .append(facultyEventScheduleItem.getEventScheduleTime());
        holder.auEventScheduleItemDateTime.setText(eventScheduleDateTextBuilder.toString());

    }

    @Override
    public int getItemCount() {
        return facultyEventScheduleList != null && !facultyEventScheduleList.isEmpty() ?
                facultyEventScheduleList.size() : 0;
    }

    static class EventScheduleItemHolder extends RecyclerView.ViewHolder {

        TextView auEventScheduleItemLocation;
        TextView auEventScheduleItemDateTime;

        EventScheduleItemHolder(View itemView) {
            super(itemView);
            auEventScheduleItemLocation = (TextView) itemView.findViewById(R.id.auEventScheduleItemLocation);
            auEventScheduleItemDateTime = (TextView) itemView.findViewById(R.id.auEventScheduleItemDateTime);
        }
    }


}
