package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.utils.StaticDateUtils;
import www.uni_weimar.de.au.view.listeners.AUItemStateListener;

/**
 * Created by ndovhuy on 19.06.2017.
 */

public class AUNewsFeedRecyclerViewAdapter extends RecyclerView.Adapter<AUNewsFeedRecyclerViewAdapter.NewsFeedVH> {

    private AUItemStateListener<AUNewsFeed> auNewsFeedLikedItemListener;
    private List<AUNewsFeed> auNewsFeedList;
    private Context context;


    public AUNewsFeedRecyclerViewAdapter(Context context, List<AUNewsFeed> auNewsFeedList) {
        this.auNewsFeedList = auNewsFeedList;
        this.context = context;
    }

    @Override
    public NewsFeedVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_single_news_feed_item, parent, false);
        return new NewsFeedVH(view);
    }

    @Override
    public void onBindViewHolder(NewsFeedVH holder, int position) {
        AUNewsFeed auNewsFeedItem = auNewsFeedList.get(position);
        holder.newsFeedTitle.setText(auNewsFeedItem.getTitle());
        holder.newsFeedDescription.setText(auNewsFeedItem.getDesciption());
        Glide.with(context).load(auNewsFeedItem.getImgUrl())
                .thumbnail(0.4f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(context.getResources().getDrawable(R.drawable.news_article))
                .into(holder.newsFeedImage);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        String pubDate = auNewsFeedItem.getPubDate().replace("CEST", "").trim();
        try {
            Date currentDate = new Date();
            Date dateOfPublication = formatter.parse(pubDate);
            populateTimeAfterPubIndicator(holder, dateOfPublication, currentDate);
        } catch (ParseException e) {
            String[] dates = pubDate.split(" ");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dates[1]));
            cal.set(Calendar.MONTH, StaticDateUtils.months.get(dates[2]));
            cal.set(Calendar.YEAR, Integer.valueOf(dates[3]));
            String[] hours = dates[4].split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hours[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(hours[1]));
            cal.set(Calendar.SECOND, Integer.valueOf(hours[2]));
            Date dateOfPublication = cal.getTime();
            populateTimeAfterPubIndicator(holder, dateOfPublication, new Date());
            e.printStackTrace();
        }

    }

    private void populateTimeAfterPubIndicator(NewsFeedVH holder, Date dateOfPublication, Date currentDate) {
        long duration = currentDate.getTime() - dateOfPublication.getTime();
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration - TimeUnit.HOURS.toMillis(diffInHours));
        if (diffInHours != 0) {
            holder.newsFeedTimeSince.setText(Long.toString(diffInHours) + " h " + Long.toString(diffInMinutes) + " min ago ");
        } else {
            holder.newsFeedTimeSince.setText(Long.toString(diffInMinutes) + " min ago ");
        }
    }

    @Override
    public int getItemCount() {
        if (auNewsFeedList != null) {
            return auNewsFeedList.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class NewsFeedVH extends RecyclerView.ViewHolder {

        CardView auNewsFeedCardView;
        TextView newsFeedTitle;
        TextView newsFeedTimeSince;
        ImageView newsFeedImage;
        TextView newsFeedDescription;
        Button newsFeedLikeBtn;


        NewsFeedVH(View itemView) {
            super(itemView);
            auNewsFeedCardView = (CardView) itemView.findViewById(R.id.au_news_feed_card_view);
            newsFeedTitle = (TextView) itemView.findViewById(R.id.news_feed_title);
            newsFeedTimeSince = (TextView) itemView.findViewById(R.id.news_feed_time_since);
            newsFeedImage = (ImageView) itemView.findViewById(R.id.news_feed_image);
            newsFeedDescription = (TextView) itemView.findViewById(R.id.news_feed_description);
            newsFeedLikeBtn = (Button) itemView.findViewById(R.id.news_feed_like_btn);
            newsFeedLikeBtn.setOnClickListener(v -> {
                auNewsFeedLikedItemListener.onLiked(auNewsFeedList.get(getAdapterPosition()));
            });
        }
    }


    public void setAuNewsFeedList(List<AUNewsFeed> auNewsFeedList) {
        this.auNewsFeedList = auNewsFeedList;
    }

    public void setAuNewsFeedLikedItemListener(AUItemStateListener<AUNewsFeed> auNewsFeedLikedItemListener) {
        this.auNewsFeedLikedItemListener = auNewsFeedLikedItemListener;
    }
}
