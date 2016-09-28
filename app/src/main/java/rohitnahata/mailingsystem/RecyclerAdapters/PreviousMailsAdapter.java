package rohitnahata.mailingsystem.RecyclerAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import rohitnahata.mailingsystem.Models.PreviousMailModel;
import rohitnahata.mailingsystem.R;

public class PreviousMailsAdapter extends RecyclerView.Adapter<PreviousMailsAdapter.MyViewHolder> {

    String letter;
    int color;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private List<PreviousMailModel> previousMailModelList;


    public PreviousMailsAdapter(List<PreviousMailModel> previousMailModelList) {
        this.previousMailModelList = previousMailModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_mails_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PreviousMailModel previousMailModel = previousMailModelList.get(position);
        holder.time.setText(previousMailModel.getStrTime_sent());
        holder.subjectText.setText(previousMailModel.getStrSubject());
        holder.recipientText.setText(previousMailModel.getStrRecipients());
        holder.bodyText.setText(previousMailModel.getStrBody());
        letter = (String.valueOf(previousMailModel.getStrRecipients().charAt(0))).toUpperCase();
        color = generator.getColor(letter);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, color /*generator.getRandomColor()*/);
        holder.nameImage.setImageDrawable(drawable);
        if (previousMailModel.getStrAttachments() != null && previousMailModel.getStrAttachments().size() > 0) {
            holder.attachmentPresent.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return previousMailModelList.size();
    }

    public void animateTo(List<PreviousMailModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(List<PreviousMailModel> newModels) {

        for (int i = previousMailModelList.size() - 1; i >= 0; i--) {
            final PreviousMailModel model = previousMailModelList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<PreviousMailModel> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PreviousMailModel model = newModels.get(i);
            if (!previousMailModelList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<PreviousMailModel> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PreviousMailModel model = newModels.get(toPosition);
            final int fromPosition = previousMailModelList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
//        notifyDataSetChanged();
    }

    public PreviousMailModel removeItem(int position) {
        final PreviousMailModel model = previousMailModelList.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
        return model;
    }

    public void addItem(int position, PreviousMailModel model) {
        previousMailModelList.add(position, model);
        notifyItemInserted(position);
//        notifyDataSetChanged();

    }

    public void moveItem(int fromPosition, int toPosition) {
        final PreviousMailModel model = previousMailModelList.remove(fromPosition);
        previousMailModelList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
//        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, subjectText, bodyText, recipientText;
        public ImageView nameImage, attachmentPresent;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            subjectText = (TextView) view.findViewById(R.id.subjectText);
            recipientText = (TextView) view.findViewById(R.id.recipientText);
            bodyText = (TextView) view.findViewById(R.id.bodyText);
            nameImage = (ImageView) view.findViewById(R.id.nameImage);
            attachmentPresent = (ImageView) view.findViewById(R.id.attachmentPresent);
        }


    }

}