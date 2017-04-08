package rohitnahata.mailingsystem.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Models.StudentDetailsModel;
import rohitnahata.mailingsystem.R;


public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.MyViewHolder> {

    private final ArrayList<StudentDetailsModel> studentDetailModels;

    public StudentDetailsAdapter(ArrayList<StudentDetailsModel> studentDetailModels) {
        this.studentDetailModels = studentDetailModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_details_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StudentDetailsModel studentDetailsModel1 = studentDetailModels.get(position);
        holder.name.setText(studentDetailsModel1.getName());
        holder.email_id.setText(studentDetailsModel1.getEmail_id());
        holder.uid.setText(studentDetailsModel1.getId());
        holder.classroom.setText(studentDetailsModel1.getClassroom());

    }

    @Override
    public int getItemCount() {
        return studentDetailModels.size();
    }

    public void animateTo(List<StudentDetailsModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(List<StudentDetailsModel> newModels) {

        for (int i = studentDetailModels.size() - 1; i >= 0; i--) {
            final StudentDetailsModel model = studentDetailModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
//            notifyDataSetChanged();
        }
    }

    private void applyAndAnimateAdditions(List<StudentDetailsModel> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final StudentDetailsModel model = newModels.get(i);
            if (!studentDetailModels.contains(model)) {
                addItem(i, model);
            }
        }
//        notifyDataSetChanged();
    }

    private void applyAndAnimateMovedItems(List<StudentDetailsModel> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final StudentDetailsModel model = newModels.get(toPosition);
            final int fromPosition = studentDetailModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
//        notifyDataSetChanged();
    }

    private StudentDetailsModel removeItem(int position) {
        final StudentDetailsModel model = studentDetailModels.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
        return model;
    }

    private void addItem(int position, StudentDetailsModel model) {
        studentDetailModels.add(position, model);
        notifyItemInserted(position);
//        notifyDataSetChanged();

    }

    private void moveItem(int fromPosition, int toPosition) {
        final StudentDetailsModel model = studentDetailModels.remove(fromPosition);
        studentDetailModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
//        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView uid;
        public final TextView email_id;
        public final TextView classroom;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.recyclerName);
            uid = (TextView) view.findViewById(R.id.recyclerUID);
            email_id = (TextView) view.findViewById(R.id.recyclerEmail);
            classroom = (TextView) view.findViewById(R.id.recyclerClassroom);
        }
    }


}

