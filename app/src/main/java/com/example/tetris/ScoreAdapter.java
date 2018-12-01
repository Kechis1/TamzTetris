package com.example.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<Score> {

    Context context;
    int layoutResourceId;
    List<Score> data = null;

    public ScoreAdapter(Context context, int layoutResourceId, List<Score> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScoreHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ScoreHolder();
            holder.tvUerName = row.findViewById(R.id.tvUerName);
            holder.tvLevel = row.findViewById(R.id.tvLevel);
            holder.tvScore = row.findViewById(R.id.tvScore);

            row.setTag(holder);
        }
        else
        {
            holder = (ScoreHolder)row.getTag();
        }

        Score score = data.get(position);
        holder.tvUerName.setText(score.getName());
        holder.tvLevel.setText(score.getLevel()+"");
        holder.tvScore.setText(score.getPoints()+"");
        return row;
    }

    static class ScoreHolder
    {
        TextView tvUerName;
        TextView tvLevel;
        TextView tvScore;
    }
}
