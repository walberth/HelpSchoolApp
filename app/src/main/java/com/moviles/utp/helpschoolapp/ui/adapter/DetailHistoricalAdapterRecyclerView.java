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

import java.util.List;

/**
 * Created by Gustavo Ramos M. on 12/11/2017.
 */

public class DetailHistoricalAdapterRecyclerView extends RecyclerView.Adapter<DetailHistoricalAdapterRecyclerView.DetailHistoricalViewHolder> {

    private List<HistoricalResponse> events;
    private int resource;
    private Activity activity;

    public DetailHistoricalAdapterRecyclerView(List<HistoricalResponse> events, int resource, Activity activity) {
        this.events = events;
        this.resource = resource;
        this.activity = activity;
    }

    public class DetailHistoricalViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameCard;
        private TextView typeRequestCard;
        private TextView timestampCard;
        private TextView actionCard;
        private TextView eventCard;
        private TextView statusCard;
        private TextView statusLabel;
        private View horizontalLine;

        public DetailHistoricalViewHolder(View itemView) {
            super(itemView);
            usernameCard = (TextView) itemView.findViewById(R.id.usernameCard);
            typeRequestCard = (TextView) itemView.findViewById(R.id.typeCard);
            timestampCard = (TextView) itemView.findViewById(R.id.timestampCard);
            actionCard = (TextView) itemView.findViewById(R.id.actionCard);
            eventCard = (TextView) itemView.findViewById(R.id.eventCard);
            statusCard = (TextView) itemView.findViewById(R.id.statusCard);
            statusLabel = (TextView) itemView.findViewById(R.id.statusLabel);
            horizontalLine = (View) itemView.findViewById(R.id.historicalHorizontalLine);
        }
    }

    @Override
    public DetailHistoricalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new DetailHistoricalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailHistoricalViewHolder holder, int position) {
        HistoricalResponse event = events.get(position);
        holder.usernameCard.setText(event.getUsername());
        holder.typeRequestCard.setText(event.getTypeRequest());
        holder.timestampCard.setText(event.getTimestamp());
        holder.actionCard.setText(event.getAction());
        holder.eventCard.setText(event.getEvent());
        if (event.getStatus().length() > 0)
            holder.statusCard.setText(event.getStatus());
        else {
            holder.statusCard.setVisibility(View.GONE);
            holder.statusLabel.setVisibility(View.GONE);
        }
        if (position == (events.size() - 1)) {
            holder.horizontalLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
