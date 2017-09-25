package www.uni_weimar.de.au.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;

/**
 * Created by user on 22.09.17.
 */

public class AUMyEventSchedulesRecyclerViewAdapter extends RecyclerView.Adapter<AUMyEventSchedulesRecyclerViewAdapter.EventScheduleHolder> {

    private List<AUFacultyEventSchedule> eventSchedules;

    public AUMyEventSchedulesRecyclerViewAdapter(List<AUFacultyEventSchedule> eventSchedules) {
        this.eventSchedules = eventSchedules;
    }

    @Override
    public EventScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_fav_event_item, parent, false);
        return new EventScheduleHolder(rootView);
    }

    @Override
    public void onBindViewHolder(EventScheduleHolder holder, int position) {
        AUFacultyEventSchedule eventSchedule = eventSchedules.get(position);
        holder.eventScheduleName.setText(eventSchedule.getEventName());
        holder.eventScheduleTime.setText(String.format("%s, %s", eventSchedule.getEventScheduleDuration(),
                eventSchedule.getEventScheduleTime()));
        holder.eventSchedulePlace.setText(eventSchedule.getEventScheduleRoom());
    }

    @Override
    public int getItemCount() {
        return eventSchedules != null && !eventSchedules.isEmpty() ?
                eventSchedules.size() : 0;
    }

    public void setEventSchedules(List<AUFacultyEventSchedule> eventScheduleList) {
        this.eventSchedules = eventScheduleList;
    }

    class EventScheduleHolder extends RecyclerView.ViewHolder {

        TextView eventScheduleName;
        TextView eventScheduleTime;
        TextView eventSchedulePlace;


        EventScheduleHolder(View itemView) {
            super(itemView);
            eventScheduleName = (TextView) itemView.findViewById(R.id.au_my_schedule_event_name);
            eventScheduleTime = (TextView) itemView.findViewById(R.id.au_my_schedule_event_time);
            eventSchedulePlace = (TextView) itemView.findViewById(R.id.au_my_schedule_event_place);
        }
    }
}
