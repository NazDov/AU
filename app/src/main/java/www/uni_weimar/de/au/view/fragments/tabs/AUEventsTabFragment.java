package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;

/**
 * Created by ndovhuy on 18.06.2017.
 */
public class AUEventsTabFragment extends AUMainMenuTabFragment {

    @InjectView(R.id.newsFeedTextView)
    TextView textView;

    public static AUEventsTabFragment newInstance(String title) {
        Bundle args = new Bundle();
        AUEventsTabFragment fragment = new AUEventsTabFragment();
        fragment.mainMenuTabTitle = title;
        args.putCharSequence("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_news_feed_tabs_layout, container, false);
        ButterKnife.inject(this, rootView);
        String title = getArguments().getString("title");
        textView.setText(title);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
