/**
 *
 * SelectPalinaActivity.java
 * 
 * Created: Jan 16, 2011 11:41:06 AM
 * 
 * Copyright (C) 2011 Paolo Dongilli and Markus Windegger
 *
 * This file is part of SasaBus.

 * SasaBus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SasaBus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SasaBus.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package it.sasabz.android.sasabus.fragments;

import java.util.Vector;

import it.sasabz.android.sasabus.R;
import it.sasabz.android.sasabus.SASAbus;
import it.sasabz.android.sasabus.R.id;
import it.sasabz.android.sasabus.R.layout;
import it.sasabz.android.sasabus.R.menu;
import it.sasabz.android.sasabus.R.string;
import it.sasabz.android.sasabus.classes.adapter.MyListAdapter;
import it.sasabz.android.sasabus.classes.dbobjects.Bacino;
import it.sasabz.android.sasabus.classes.dbobjects.BacinoList;
import it.sasabz.android.sasabus.classes.dbobjects.DBObject;
import it.sasabz.android.sasabus.classes.dbobjects.Linea;
import it.sasabz.android.sasabus.classes.dbobjects.LineaList;
import it.sasabz.android.sasabus.classes.dbobjects.Palina;
import it.sasabz.android.sasabus.classes.dbobjects.PalinaList;
import it.sasabz.android.sasabus.classes.dialogs.About;
import it.sasabz.android.sasabus.classes.dialogs.Credits;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArrivalFragment extends Fragment implements OnItemClickListener {

	//saves the linea global for this object
    private Linea linea;
    
    //saves the arrival global for this object
    private Palina departure;
    
    //saves the list of possible parture bus-stops for this object
    private Vector<DBObject> list;

    private Bacino bacino = null;
    
    private ArrivalFragment() {
    }
    
    public ArrivalFragment(Bacino bacino, Linea linea, Palina departure)
    {
    	this();
    	this.departure = departure;
    	this.linea = linea;
    	this.bacino = bacino;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View result = inflater.inflate(R.layout.palina_listview_layout,  container, false);
    	TextView titel = (TextView)result.findViewById(R.id.untertitel);
        titel.setText(R.string.select_destination);
        
        Resources res = getResources();
        
        TextView lineat = (TextView)result.findViewById(R.id.line);
        TextView from = (TextView)result.findViewById(R.id.from);
        TextView to = (TextView)result.findViewById(R.id.to);
        
        lineat.setText(res.getString(R.string.line_txt) + " " + linea.toString());
        to.setText("");
        from.setText(res.getString(R.string.from) + " " + departure.toString());
        
        fillData(result);
    	return result;
    }
    

    /**
     * this method fills the possible parture busstops into the list_view
     */
    private void fillData(View result) {
        list = PalinaList.getListDestinazione(departure.getName_de(), linea.getId(), bacino.getTable_prefix());
        MyListAdapter paline = new MyListAdapter(SASAbus.getContext(), R.id.text, R.layout.arrival_row, list);
        ListView listview = (ListView)result.findViewById(android.R.id.list);
        listview.setAdapter(paline);
        listview.setOnItemClickListener(this);
    }
    

	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long id) {
		// TODO Auto-generated method stub
		Palina arrival = (Palina)list.get(position);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		
		Fragment fragment = fragmentManager.findFragmentById(R.id.onlinefragment);
		if(fragment != null)
		{
			ft.remove(fragment);
		}
		fragment = new OrarioFragment(bacino, linea, departure, arrival);
		ft.add(R.id.onlinefragment, fragment);
		ft.addToBackStack(null);
		ft.commit();
		fragmentManager.executePendingTransactions();
	}
}
