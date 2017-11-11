package com.moviles.utp.helpschoolapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
            cardListRequest = (CardView) itemView.findViewById(R.id.cardListRequest);
        }
    }

    @Override
    public ListRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ListRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRequestViewHolder holder, int position) {
        PendingRequestResponse listPendingRequest = listPendingRequestResponse.get(position);

        holder.labelRequest.setText(listPendingRequest.getRequest());
        holder.timeStampCReq.setText(listPendingRequest.getTimeStampCReq());
        holder.status.setText(listPendingRequest.getStatusRequest());

        holder.cardListRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetailRequest = new Intent(mActivity, DetailResponseActivity.class);
                mActivity.startActivity(intentDetailRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPendingRequestResponse.size();
    }
}
/*
public class ListRequestAdapter extends RecyclerView.Adapter<ListRequestAdapter.ListRequestViewHolder> {


    public ListRequestAdapter(ArrayList<ListRequestResponse> listRequestResponseArrayList) {
        this.listRequestResponseArrayList = listRequestResponseArrayList;
    }

    public static class ListRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView requestType;
        private TextView labelRequest;
        private TextView status;

        public ListRequestViewHolder                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 (View itemView) {
            super(itemView);
            requestType = itemView.findViewById(R.id.timeStampCReq);
            labelRequest = itemView.findViewById(R.id.txtRequest);
            status = itemView.findViewById(R.id.txtStatus);
        }

        public void BindListRequest (ListRequestResponse listRequestResponse) {
            requestType.setText("Tipo Solicitud: " + listRequestResponse.getRequestType());
            labelRequest.setText("Solicitud: " + listRequestResponse.getLabelRequest());
            status.setText("Estado: " + listRequestResponse.getStatus());
        }
    }

    @Override
    public ListRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_request, parent, Boolean.FALSE);
        return new ListRequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListRequestViewHolder holder, int position) {
        ListRequestResponse listRequestResponse = listRequestResponseArrayList.get(position);
        holder.BindListRequest(listRequestResponse);
    }

    @Override
    public int getItemCount() {
        return listRequestResponseArrayList.size();
    }

    public ArrayList<ListRequestResponse> getData() {
        return listRequestResponseArrayList;
    }

    public void setData(ArrayList<ListRequestResponse> listRequestResponseArrayList) {

        this.listRequestResponseArrayList = listRequestResponseArrayList;
    }
}
*/
