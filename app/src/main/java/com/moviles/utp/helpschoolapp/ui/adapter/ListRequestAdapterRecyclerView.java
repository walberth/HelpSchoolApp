package com.moviles.utp.helpschoolapp.ui.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.moviles.utp.helpschoolapp.DetailResponseActivity;
import com.moviles.utp.helpschoolapp.EditActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;
import com.moviles.utp.helpschoolapp.ui.fragment.ListRequestFragment;

import java.util.ArrayList;

import static com.moviles.utp.helpschoolapp.helper.controller.VolleyController.TAG;

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
        private TextView textViewOptions;
        public CardView cardListRequest;

        public ListRequestViewHolder(View itemView) {
            super(itemView);
            timeStampCReq = (TextView) itemView.findViewById(R.id.timeStampCReq);
            labelRequest = (TextView) itemView.findViewById(R.id.txtRequest);
            status = (TextView) itemView.findViewById(R.id.txtStatus);
            cardListRequest = (CardView) itemView.findViewById(R.id.cardListR);
            textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }

    @Override
    public ListRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ListRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListRequestViewHolder holder, int position) {
        final PendingRequestResponse listPendingRequest = listPendingRequestResponse.get(position);

        holder.labelRequest.setText(listPendingRequest.getRequest());
        holder.timeStampCReq.setText(listPendingRequest.getTimeStampCReq());
        holder.status.setText(listPendingRequest.getStatusRequest());

        Log.d(TAG, "onBindViewHolder: llegamos aca");

        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mActivity, holder.textViewOptions);
                popupMenu.inflate(R.menu.cardview_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.mnuEditar:
                                Log.d(TAG, "onMenuItemClick: valor presionado fue editar");
                                Intent intentDetailRequest = new Intent(mActivity, EditActivity.class);
                                intentDetailRequest.putExtra("requestId", listPendingRequest.getIdRequest());
                                mActivity.startActivity(intentDetailRequest);

                                //TODO: IMPLEMENTAR LA EDICION EN UN DIALOG AQUI MAS ADELANTE
                                break;
//                            case R.id.mnuEliminar:
//                                Log.d(TAG, "onMenuItemClick: valor presionado fue eliminar");
//                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

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

    public Integer removeItem(int position){
        PendingRequestResponse pending = listPendingRequestResponse.get(position);
        listPendingRequestResponse.remove(pending);
        return Integer.parseInt(pending.getIdRequest());
    }
}
