package com.moviles.utp.helpschoolapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.ListRequestResponse;
import java.util.ArrayList;

/**
 * Created by Walberth Gutierrez Telles on 11/4/2017.
 */

public class ListRequestAdapter extends RecyclerView.Adapter<ListRequestAdapter.ListRequestViewHolder> {
    private ArrayList<ListRequestResponse> listRequestResponseArrayList;

    public ListRequestAdapter(ArrayList<ListRequestResponse> listRequestResponseArrayList) {
        this.listRequestResponseArrayList = listRequestResponseArrayList;
    }

    public static class ListRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView requestType;
        private TextView labelRequest;
        private TextView status;

        public ListRequestViewHolder (View itemView) {
            super(itemView);
            requestType = (TextView) itemView.findViewById(R.id.txtType);
            labelRequest = (TextView) itemView.findViewById(R.id.txtRequest);
            status = (TextView) itemView.findViewById(R.id.txtStatus);
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
