package rohitnahata.mailingsystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Models.StudentDetailsModel;
import rohitnahata.mailingsystem.R;

public class StudentSuggestionAdapter extends ArrayAdapter<StudentDetailsModel> {

    private final Context context;
    private final List<StudentDetailsModel> items;
    private final List<StudentDetailsModel> tempItems;
    private final List<StudentDetailsModel> suggestions;
    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    private final Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String temp = ((StudentDetailsModel) resultValue).getEmail_id();
            if (!temp.equals(""))
                return ((StudentDetailsModel) resultValue).getEmail_id();
            else
                return ((StudentDetailsModel) resultValue).getClassroom();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StudentDetailsModel people : tempItems) {
                    if (people.getClassroom().toLowerCase().contains(constraint.toString().toLowerCase()))
                        suggestions.add(people);
                    else if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                        suggestions.add(people);
                    else if (people.getEmail_id().toLowerCase().contains(constraint.toString().toLowerCase()))
                        suggestions.add(people);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<StudentDetailsModel> filterList = (ArrayList<StudentDetailsModel>) results.values;
            if (results.count > 0) {
                clear();
                for (StudentDetailsModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public StudentSuggestionAdapter(Context context, List<StudentDetailsModel> items) {
        super(context, R.layout.activity_main, R.id.lbl_name, items);
        this.context = context;
        this.items = items;
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_recipients_suggestions, parent, false);
        }
        StudentDetailsModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            String tp = people.getClassroom() + "\n" + people.getName() + "\n" + people.getEmail_id();
            lblName.setText(tp);
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}