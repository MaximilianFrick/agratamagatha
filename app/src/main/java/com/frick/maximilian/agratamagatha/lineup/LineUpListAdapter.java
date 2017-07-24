package com.frick.maximilian.agratamagatha.lineup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frick.maximilian.agratamagatha.R;

import java.util.Collections;
import java.util.List;

class LineUpListAdapter extends RecyclerView.Adapter<LineUpListAdapter.ViewHolder> {
   class ViewHolder extends RecyclerView.ViewHolder {
      TextView bandName;
      View item;

      ViewHolder(View itemView) {
         super(itemView);
         item = itemView.findViewById(R.id.item_band);
         bandName = (TextView) itemView.findViewById(R.id.band_name);
      }
   }

   private List<Band> bands = Collections.emptyList();
   private BandClickedListener listener;

   LineUpListAdapter(List<Band> bands, BandClickedListener listener) {
      this.bands = bands;
      this.listener = listener;
   }

   @Override
   public int getItemCount() {
      return bands.size();
   }

   @Override
   public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.bandName.setText(bands.get(position)
            .getTitle());
      holder.item.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            listener.onBandClicked(bands.get(holder.getAdapterPosition()));
         }
      });
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      View view = inflater.inflate(R.layout.row_layout, parent, false);
      return new ViewHolder(view);
   }
}
