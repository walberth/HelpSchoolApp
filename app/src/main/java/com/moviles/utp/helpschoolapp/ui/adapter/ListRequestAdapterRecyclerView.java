package com.moviles.utp.helpschoolapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.moviles.utp.helpschoolapp.DetailResponseActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;
import java.util.ArrayList;

/**
 * Created by Walberth Gutierrez Telles on 11/4/2017.
 */

public class ListRequestAdapterRecyclerView extends RecyclerView.Adapter<ListRequestAdapterRecyclerView.ListRequestViewHolder>{
    private ArrayList<PendingRequestResponse> listPendingRequestResponse;
    private int resource;
    private Activity mActivity;

    public ListRequestAdapterRecyclerView(ArrayList<PendingRequestResponse> listPendingRequestResponse, int resource, Activity activity) {
        this.listPendingRequestResponse = listPendingRequestResponse;
        this.resource = resource;
        mActivity = activity;
    }

    public class ListRequestViewHolder extends RecyclerView.ViewHolder {

        private TextView timeStampCReq;
        private TextView labelRequest;
        private TextView status;
        private CardView cardListRequest;

        public ListRequestViewHolder(View itemView) {
            super(itemView);
            timeStampCReq = (TextView) itemView.findViewById(R.id.timeStampCReq);
            labelRequest = (TextView) itemView.findViewById(R.id.txtRequest);
            status = (TextView) itemView.findViewById(R.id.txtStatus);
            cardListRequest = (CardView) itemView.findViewById(R.id.cardListR);
        }
    }

    @Override
    public ListRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ListRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRequestViewHolder holder, int position) {
        final PendingRequestResponse listPendingRequest = listPendingRequestResponse.get(position);

        holder.labelRequest.setText(listPendingRequest.getRequest());
        holder.timeStampCReq.setText(listPendingRequest.getTimeStampCReq());
        holder.status.setText(listPendingRequest.getStatusRequest());

        Log.d("onBindViewHolder", "IdRequest " + listPendingRequest.getIdRequest());

        holder.cardListRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intentDetailRequest = new Intent(mActivity, DetailResponseActivity.class);
                    intentDetailRequest.putExtra("requestId", listPendingRequest.getIdRequest());
                    mActivity.startActivity(intentDetailRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPendingRequestResponse.size();
    }
}
