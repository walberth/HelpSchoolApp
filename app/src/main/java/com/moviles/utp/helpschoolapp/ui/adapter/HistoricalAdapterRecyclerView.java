package com.moviles.utp.helpschoolapp.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.EventsResponse;
import com.moviles.utp.helpschoolapp.data.model.HistoricalResponse;
import com.moviles.utp.helpschoolapp.helper.utils.Dates;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gustavo Ramos M. on 11/11/2017.
 */

public class HistoricalAdapterRecyclerView extends RecyclerView.Adapter<HistoricalAdapterRecyclerView.HistoricalViewHolder> {

    private List<EventsResponse> eventsList;
    private int resource;
    private Activity activity;

    public HistoricalAdapterRecyclerView(List<EventsResponse> eventsList, int resource, Activity activity) {
        this.eventsList = eventsList;
        this.resource = resource;
        this.activity = activity;
    }

    public class HistoricalViewHolder extends RecyclerView.ViewHolder {

        private TextView dateCard;

        public HistoricalViewHolder(View itemView) {
            super(itemView);
            dateCard = (TextView) itemView.findViewById(R.id.dateCard);
        }
    }

    @Override
    public HistoricalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new HistoricalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoricalViewHolder holder, int position) {
        EventsResponse event = eventsList.get(position);
        holder.dateCard.setText(Dates.getFormatNameCardView(Dates.getCalendarByDate(event.getDate())));
        //HistoricalResponse historical = events.get(position);
        //ArrayList<HistoricalResponse> eventsList = eventsMap.get();
        //holder.dateCard.setText(historical.getDate());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
