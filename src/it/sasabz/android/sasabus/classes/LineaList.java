/**
 * 
 *
 * LineaList.java
 * 
 * Created: 13.12.2011 20:13:31
 * 
 * Copyright (C) 2011 Paolo Dongilli & Markus Windegger
 * 
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
package it.sasabz.android.sasabus.classes;

import it.sasabz.android.sasabus.SASAbus;

import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Markus Windegger (markus@mowiso.com)
 *
 */
public class LineaList extends DBObjectList {
	
	/**                                                                                                                                                                                                          
	 * This function returns a vector of all the objects momentanly avaiable in the database                                                                                                                     
	 * @return a vector of objects if all goes right, alternativ it returns a MyError                                                                                                                              
	 */
	public static  Vector <DBObject>  getList() throws Exception
	{
		SQLiteDatabase sqlite = MySQLiteDBAdapter.getInstance(SASAbus.getContext());
		Cursor cursor = sqlite.rawQuery("select distinct bacino from linee_corse where bacino <> ''", null);
		if(cursor.getCount() != 0)
		{
			int id = 0;
			do {
				Bacino element = new Bacino();
				element.setBacinoName(cursor.getString(cursor.getColumnIndex("bacino")));
				element.setId(id);
				++id;
			} while(!cursor.isLast());
		}
		else
			list = null;
		return list;
	}
	
	public static Vector <DBObject> getListBacino(String bacino)
	{
		SQLiteDatabase sqlite = MySQLiteDBAdapter.getInstance(SASAbus.getContext());
		String[] args = {bacino};
		Cursor cursor = sqlite.rawQuery("select *  from linee where localita = ?", args);
		if(cursor.getCount() != 0)
		{
			do {
				Linea element = new Linea(cursor);
				list.add(element);
			} while(!cursor.isLast());
		}
		else
			list = null;
		return list;
	}
	
	public static Cursor getCursorBacino(String bacino)
	{
		SQLiteDatabase sqlite = MySQLiteDBAdapter.getInstance(SASAbus.getContext());
		String[] args = {bacino};
		Cursor cursor = sqlite.rawQuery("select * from linee where localita = ?", args);
		return cursor;
	}
	
	
	public static Cursor getCursor ()
	{
		SQLiteDatabase sqlite = MySQLiteDBAdapter.getInstance(SASAbus.getContext());
		Cursor cursor = sqlite.rawQuery("select * from linee", null);
		return cursor;
	}
}