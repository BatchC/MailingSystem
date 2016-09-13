package rohitnahata.mailingsystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rohit on 05/09/2016.
 */
public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.MyViewHolder> {

    private List<StudentDetails> studentDetails;

    public StudentDetailsAdapter(List<StudentDetails> studentDetails) {
        this.studentDetails = studentDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_details_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StudentDetails studentDetails1 = studentDetails.get(position);
        holder.name.setText(studentDetails1.getName());
        holder.email_id.setText(studentDetails1.getEmail_id());
        holder.uid.setText(studentDetails1.getId());
        holder.classroom.setText(studentDetails1.getClassroom());

    }

    @Override
    public int getItemCount() {
        return studentDetails.size();
    }

    public void animateTo(List<StudentDetails> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(List<StudentDetails> newModels) {

        for (int i = studentDetails.size() - 1; i >= 0; i--) {
            final StudentDetails model = studentDetails.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
//            notifyDataSetChanged();
        }
    }

    private void applyAndAnimateAdditions(List<StudentDetails> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final StudentDetails model = newModels.get(i);
            if (!studentDetails.contains(model)) {
                addItem(i, model);
            }
        }
//        notifyDataSetChanged();
    }

    private void applyAndAnimateMovedItems(List<StudentDetails> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final StudentDetails model = newModels.get(toPosition);
            final int fromPosition = studentDetails.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
//        notifyDataSetChanged();
    }

    public StudentDetails removeItem(int position) {
        final StudentDetails model = studentDetails.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
        return model;
    }

    public void addItem(int position, StudentDetails model) {
        studentDetails.add(position, model);
        notifyItemInserted(position);
//        notifyDataSetChanged();

    }

    public void moveItem(int fromPosition, int toPosition) {
        final StudentDetails model = studentDetails.remove(fromPosition);
        studentDetails.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
//        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, uid, email_id, classroom;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.recyclerName);
            uid = (TextView) view.findViewById(R.id.recyclerUID);
            email_id = (TextView) view.findViewById(R.id.recyclerEmail);
            classroom = (TextView) view.findViewById(R.id.recyclerClassroom);
        }
    }


}

