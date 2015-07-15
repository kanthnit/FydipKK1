package com.example.kk.fydipkk1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 07-07-2015.
 */
public class MatchlistAdapter extends BaseAdapter {

//    public MatchlistAdapter(Context context, String[] names) {
//        super(context, R.layout.matchlist_row,names);
//    }

    LayoutInflater matchlistInflater;
    List<Match> matchList;

    public MatchlistAdapter(Context context, List<Match> matches) {
        matchlistInflater = LayoutInflater.from(context);
        matchList = matches;
    }

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchList.get(position);
    }

    @Override
    public long getItemId(int position) {
    //    return matchList.get(position).get_id();
        return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View matchlistView = matchlistInflater.inflate(R.layout.matchlist_row, parent, false);

        String strplayer1 = matchList.get((int)getItemId(position)).get_player1();
        String strplayer11 = matchList.get((int)getItemId(position)).get_player11();
        String strplayer2 = matchList.get((int)getItemId(position)).get_player2();
        String strplayer22 = matchList.get((int)getItemId(position)).get_player22();
        int strscore1 = matchList.get((int)getItemId(position)).get_points1();
        int strscore2 = matchList.get((int)getItemId(position)).get_points2();
        if(!strplayer11.isEmpty())
            strplayer1 += "/" + strplayer11;
        if(!strplayer22.isEmpty())
            strplayer2 += "/" + strplayer22;
        TextView player1 = (TextView) matchlistView.findViewById(R.id.txtlistrowplayer1);
        TextView player2 = (TextView) matchlistView.findViewById(R.id.txtlistrowplayer2);
        TextView score1 = (TextView) matchlistView.findViewById(R.id.txtlistrowscore1);
        TextView score2 = (TextView) matchlistView.findViewById(R.id.txtlistrowscore2);

        player1.setText(strplayer1);
        player2.setText(strplayer2);
        score1.setText(String.valueOf(strscore1));
        score2.setText(String.valueOf(strscore2));

        return matchlistView;
    }
}
