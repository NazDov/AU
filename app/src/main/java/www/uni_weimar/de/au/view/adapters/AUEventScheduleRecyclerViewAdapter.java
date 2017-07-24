package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;

/**
 * Created by ndovhuy on 24.07.2017.
 */
public class AUEventScheduleRecyclerViewAdapter extends RecyclerView.Adapter<AUEventScheduleRecyclerViewAdapter.EventScheduleHolder> {

    private final Context context;
    private final List<AUFacultyEventSchedule> auFacultyEventScheduleList;

    public AUEventScheduleRecyclerViewAdapter(Context context, List<AUFacultyEventSchedule> auFacultyEventScheduleList) {
        this.context = context;
        this.auFacultyEventScheduleList = auFacultyEventScheduleList;
    }

    @Override
    public EventScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_event_schedule_single_item, parent, false);
        return new EventScheduleHolder(root);
    }

    @Override
    public void onBindViewHolder(EventScheduleHolder holder, int position) {
        AUFacultyEventSchedule auFacultyEventSchedule = auFacultyEventScheduleList.get(position);
        holder.auEventScheduleItemTag.setText(auFacultyEventSchedule.getEventScheduleDay());
        holder.auEventScheduleItemZeit.setText(auFacultyEventSchedule.getEventScheduleTime());
        holder.auEventScheduleItemRhytmus.setText(auFacultyEventSchedule.getEventSchedulePeriod());
        holder.auEventScheduleItemDauer.setText(auFacultyEventSchedule.getEventScheduleDuration());
        holder.auEventScheduleItemRaum.setText(auFacultyEventSchedule.getEventScheduleRoom());
        holder.auEventScheduleItemMaxPart.setText(auFacultyEventSchedule.getEventMaxParticipants());
    }

    @Override
    public int getItemCount() {
        return auFacultyEventScheduleList == null || auFacultyEventScheduleList.isEmpty() ? 0 : auFacultyEventScheduleList.size();
    }

    class EventScheduleHolder extends RecyclerView.ViewHolder {

        TextView auEventScheduleItemTag;
        TextView auEventScheduleItemZeit;
        TextView auEventScheduleItemRhytmus;
        TextView auEventScheduleItemDauer;
        TextView auEventScheduleItemRaum;
        TextView auEventScheduleItemMaxPart;


        EventScheduleHolder(View itemView) {
            super(itemView);
            auEventScheduleItemTag = (TextView) itemView.findViewById(R.id.auEventScheduleItemTag);
            auEventScheduleItemZeit = (TextView) itemView.findViewById(R.id.auEventScheduleItemZeit);
            auEventScheduleItemRhytmus = (TextView) itemView.findViewById(R.id.auEventScheduleItemRhytmus);
            auEventScheduleItemDauer = (TextView) itemView.findViewById(R.id.auEventScheduleItemDauer);
            auEventScheduleItemRaum = (TextView) itemView.findViewById(R.id.auEventScheduleItemRaum);
            auEventScheduleItemMaxPart = (TextView) itemView.findViewById(R.id.auEventScheduleItemMaxPart);
        }
    }

}
