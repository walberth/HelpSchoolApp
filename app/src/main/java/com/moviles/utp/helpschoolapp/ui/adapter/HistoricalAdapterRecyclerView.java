package com.moviles.utp.helpschoolapp.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.HistoricalResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Gustavo Ramos M. on 11/11/2017.
 */

public class HistoricalAdapterRecyclerView extends RecyclerView.Adapter<HistoricalAdapterRecyclerView.HistoricalViewHolder> {

    private ArrayList<HistoricalResponse> events;
    private int resource;
    private Activity activity;

    public HistoricalAdapterRecyclerView(ArrayList<HistoricalResponse> events, int resource, Activity activity) {
        this.events = events;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public HistoricalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new HistoricalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoricalViewHolder holder, int position) {
        HistoricalResponse historical = events.get(position);
        holder.dateCard.setText(historical.getDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class HistoricalViewHolder extends RecyclerView.ViewHolder {

        private TextView dateCard;

        public HistoricalViewHolder(View itemView) {
            super(itemView);
            dateCard = (TextView) itemView.findViewById(R.id.dateCard);
        }
    }
}
