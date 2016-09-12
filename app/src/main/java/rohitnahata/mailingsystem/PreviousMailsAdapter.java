package rohitnahata.mailingsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.List;

import rohitnahata.mailingsystem.databinding.PreviousMailsRecyclerBinding;

//public class PreviousMailsAdapter extends RecyclerView.Adapter<PreviousMailsAdapter.MyViewHolder> {
public class PreviousMailsAdapter extends SortedListAdapter<PreviousMailModel> {

    String letter;
    int color;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private List<PreviousMailModel> previousMailModelList;

    @Override
    protected ViewHolder<? extends PreviousMailModel> onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        final PreviousMailsRecyclerBinding binding = PreviousMailsRecyclerBinding.inflate(layoutInflater, viewGroup, false);
        return new MyViewHolder(binding);
    }

//
//    public PreviousMailsAdapter(List<PreviousMailModel> previousMailModelList) {
//        this.previousMailModelList = previousMailModelList;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.previous_mails_recycler, parent, false);
//
//        return new MyViewHolder(itemView);
//    }

    @Override
    protected boolean areItemsTheSame(PreviousMailModel previousMailModel, PreviousMailModel t1) {
        return false;
    }

    @Override
    protected boolean areItemContentsTheSame(PreviousMailModel previousMailModel, PreviousMailModel t1) {
        return false;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PreviousMailModel previousMailModel = previousMailModelList.get(position);
        holder.time.setText(previousMailModel.getStrTime_sent());
        holder.subjectText.setText(previousMailModel.getStrSubject());
        holder.recipientText.setText(previousMailModel.getStrRecipients());
        holder.bodyText.setText(previousMailModel.getStrBody());
        letter = String.valueOf(previousMailModel.getStrRecipients().charAt(0));
        System.out.println(letter);
        color = generator.getColor(letter);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, color /*generator.getRandomColor()*/);
        holder.nameImage.setImageDrawable(drawable);
        if (previousMailModel.getStrAttachments() != null && previousMailModel.getStrAttachments().size() > 0) {
            holder.attachmentPresent.setVisibility(View.VISIBLE);
        }


//        holder.nameImage.setImageResource(previousMailModel.getGenre());
//        holder.attachmentPresent.setImageResource(previousMailModel.getYear());

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
            notifyDataSetChanged();
        }
    }

    private void applyAndAnimateAdditions(List<PreviousMailModel> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PreviousMailModel model = newModels.get(i);
            if (!previousMailModelList.contains(model)) {
                addItem(i, model);
            }
        }notifyDataSetChanged();
    }

    private void applyAndAnimateMovedItems(List<PreviousMailModel> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PreviousMailModel model = newModels.get(toPosition);
            final int fromPosition = previousMailModelList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }notifyDataSetChanged();
    }

    public PreviousMailModel removeItem(int position) {
        final PreviousMailModel model = previousMailModelList.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, PreviousMailModel model) {
        previousMailModelList.add(position, model);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final PreviousMailModel model = previousMailModelList.remove(fromPosition);
        previousMailModelList.add(toPosition, model);
        notifyDataSetChanged();
//        notifyItemMoved(fromPosition, toPosition);
    }

    //    public class MyViewHolder extends RecyclerView.ViewHolder {
    public class MyViewHolder extends SortedListAdapter.ViewHolder<PreviousMailModel> {
        private final PreviousMailsRecyclerBinding mBinding;
        public TextView time, subjectText, bodyText, recipientText;
        public ImageView nameImage, attachmentPresent;

        public MyViewHolder(PreviousMailsRecyclerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

//        public void bind(PreviousMailsRecyclerBinding item) {
//            item.setModel(mBinding);
//        }

        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            subjectText = (TextView) view.findViewById(R.id.subjectText);
            recipientText = (TextView) view.findViewById(R.id.recipientText);
            bodyText = (TextView) view.findViewById(R.id.bodyText);
            nameImage = (ImageView) view.findViewById(R.id.nameImage);
            attachmentPresent = (ImageView) view.findViewById(R.id.attachmentPresent);
            mBinding = null;
        }

        @Override
        protected void performBind(PreviousMailModel previousMailModel) {
            mBinding.setModel(previousMailModel);

        }


    }


//    public void setFilter(List<PreviousMailModel> mpreviousMailModelList) {
//        previousMailModelList = new ArrayList<>();
//        previousMailModelList.addAll(mpreviousMailModelList);
//        notifyDataSetChanged();
//    }
}