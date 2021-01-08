package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCustomAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> countryModelListFiltered;


    public MyCustomAdapter(Context context, List<CountryModel> countryModelList) {
        super(context,R.layout.list_custom_item,countryModelList);
        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelListFiltered=countryModelList;
    }


    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Override
    public CountryModel getItem(int position) {
        return countryModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.list_custom_item,parent,false);
        TextView countryName=convertView.findViewById(R.id.Countryname);
        ImageView imageView=convertView.findViewById(R.id.imageFlag);

        countryName.setText(countryModelListFiltered.get(position).getCountry());

        Glide.with(context).load(countryModelListFiltered.get(position).getFlag()).into(imageView);

        return convertView;
    }



    // for function of search bar
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;

                }else{
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel itemsModel:countryModelList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelListFiltered = (List<CountryModel>) results.values;
                AffectedCountriesActivity.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
