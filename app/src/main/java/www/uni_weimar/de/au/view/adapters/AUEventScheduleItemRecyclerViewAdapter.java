package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUFavouriteEventSchedule;
import www.uni_weimar.de.au.orm.AUEventScheduleFavouriteORM;

/**
 * Created by nazar on 06.08.17.
 */

public class AUEventScheduleItemRecyclerViewAdapter extends RecyclerView.Adapter<AUEventScheduleItemRecyclerViewAdapter.EventScheduleItemHolder> {

    private static final String EVENT_SCHEDULE_ID = "eventScheduleId";
    private final List<AUFacultyEventSchedule> facultyEventSchedules;
    private final AUEventScheduleFavouriteORM scheduleFavouriteORM;
    private final Context context;

    public AUEventScheduleItemRecyclerViewAdapter(List<AUFacultyEventSchedule> facultyEventSchedules, Context context) {
        this.facultyEventSchedules = facultyEventSchedules;
        scheduleFavouriteORM = new AUEventScheduleFavouriteORM(Realm.getDefaultInstance());
        this.context = context;
    }

    @Override
    public EventScheduleItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_event_chedule_recycler_view_item, parent, false);
        return new EventScheduleItemHolder(rootView);
    }

    @Override
    public void onBindViewHolder(EventScheduleItemHolder holder, int position) {
        AUFacultyEventSchedule facultyEventScheduleItem = facultyEventSchedules.get(position);
        holder.auEventScheduleItemLocation.setText(facultyEventScheduleItem.getEventScheduleRoom());
        String eventScheduleDateTextBuilder = facultyEventScheduleItem.getEventScheduleDuration() +
                "," +
                facultyEventScheduleItem.getEventScheduleTime();
        holder.auEventScheduleItemDateTime.setText(eventScheduleDateTextBuilder);
        holder.setUpDefaultEventIsAddedStatus(position);

    }

    @Override
    public int getItemCount() {
        return facultyEventSchedules != null && !facultyEventSchedules.isEmpty() ?
                facultyEventSchedules.size() : 0;
    }

    class EventScheduleItemHolder extends RecyclerView.ViewHolder {

        TextView auEventScheduleItemLocation;
        TextView auEventScheduleItemDateTime;
        ImageView auFavEventScheduleBtn;
        boolean isAddedAsFavouriteEventSchedule;

        EventScheduleItemHolder(View itemView) {
            super(itemView);
            auEventScheduleItemLocation = (TextView) itemView.findViewById(R.id.auEventScheduleItemLocation);
            auEventScheduleItemDateTime = (TextView) itemView.findViewById(R.id.auEventScheduleItemDateTime);
            auFavEventScheduleBtn = (ImageView) itemView.findViewById(R.id.auFavEventScheduleBtn);
            auFavEventScheduleBtn.setOnClickListener(favImage -> {
                final AUFacultyEventSchedule selectedEventSchedule = facultyEventSchedules.get(getAdapterPosition());
                if (isAddedAsFavouriteEventSchedule) {
                    removeFromFavouriteEventSchedule((ImageView) favImage, selectedEventSchedule);
                } else {
                    addToFavouriteEventSchedule((ImageView) favImage, selectedEventSchedule);
                }
            });
        }

         void setUpDefaultEventIsAddedStatus(int position) {
            final AUFacultyEventSchedule selectedEventSchedule = facultyEventSchedules.get(position);
            if (scheduleFavouriteORM.hasItem(EVENT_SCHEDULE_ID, selectedEventSchedule.getEventScheduleId())) {
                auFavEventScheduleBtn.setImageResource(R.drawable.selected_star);
                isAddedAsFavouriteEventSchedule = true;
            } else {
                auFavEventScheduleBtn.setImageResource(R.drawable.unselected_star);
                isAddedAsFavouriteEventSchedule = false;
            }
        }

        private void removeFromFavouriteEventSchedule(ImageView favImage, AUFacultyEventSchedule selectedEventSchedule) {
            favImage.setImageResource(R.drawable.unselected_star);
            scheduleFavouriteORM.deleteByID(selectedEventSchedule.getEventScheduleId());
            isAddedAsFavouriteEventSchedule = false;
            Toast.makeText(context, "removing item from favourites", Toast.LENGTH_SHORT).show();
        }

        private void addToFavouriteEventSchedule(ImageView favImage, AUFacultyEventSchedule selectedEventSchedule) {
            favImage.setImageResource(R.drawable.selected_star);
            scheduleFavouriteORM.add(new AUFavouriteEventSchedule(selectedEventSchedule.getEventScheduleId()));
            isAddedAsFavouriteEventSchedule = true;
            Toast.makeText(context, "adding item to favourites", Toast.LENGTH_SHORT).show();
        }
    }


}
