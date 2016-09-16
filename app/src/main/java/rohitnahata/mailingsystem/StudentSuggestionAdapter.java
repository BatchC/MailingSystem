package rohitnahata.mailingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rohitnahata.mailingsystem.Models.StudentDetails;

public class StudentSuggestionAdapter extends ArrayAdapter<StudentDetails> {

    Context context;
    int resource, textViewResourceId;
    List<StudentDetails> items, tempItems, suggestions;
    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((StudentDetails) resultValue).getEmail_id();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StudentDetails people : tempItems) {
                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
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
            List<StudentDetails> filterList = (ArrayList<StudentDetails>) results.values;
            if (results.count > 0) {
                clear();
                for (StudentDetails people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public StudentSuggestionAdapter(Context context, int resource, int textViewResourceId, List<StudentDetails> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_recipients_suggestions, parent, false);
        }
        StudentDetails people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            String tp = people.getName() + " " + people.getEmail_id() + " " + people.getClassroom();
            lblName.setText(tp);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}