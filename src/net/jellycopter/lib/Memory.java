package net.jellycopter.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;


public class Memory {
	private static final File db_file = new File("db");
	
	public static void mute(){
		Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);	
	}
	public static void unmute(){
		Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.WARNING);	
	}
	
	public static String[][] read(String what) 
			throws MemoryException{
		List<String[]> r = new ArrayList<String[]>();
		SQLiteConnection db = new SQLiteConnection(db_file);
		try{
			db.open(true);
			SQLiteStatement st = db.prepare("SELECT * FROM "+what);
			try{
				while(st.step()){
					String[] tmp = new String[st.columnCount()];
					for(int i = 0; i < st.columnCount(); ++i){
						tmp[i] = st.columnString(i);
					}
					r.add(tmp);
				}
			}
			catch(SQLiteException e){
				System.err.println(e.getClass()+": "+e.getMessage());
				throw new MemoryException(); }
			finally{ st.dispose(); }
		}
		catch(SQLiteException e){
			System.err.println(e.getClass()+": "+e.getMessage());
			throw new MemoryException(); }
		finally{ db.dispose(); }
	
		return r.toArray(new String[r.size()][]);
	}
	
	public static void write(String what, String[] data)
			throws MemoryException{
		SQLiteConnection db = new SQLiteConnection(db_file);
		try{
			db.open(true);
			StringBuilder request = new StringBuilder();
			request.append("INSERT OR REPLACE INTO ").append(what);
			request.append(" VALUES (");
			for(String d: data){
				request.append("\"").append(d).append("\",");
			}
			request.deleteCharAt(request.length()-1).append(")");
			SQLiteStatement st = db.prepare(request.toString());
			try{
				st.stepThrough();
			}catch(SQLiteException e){ 
				System.err.println(e.getClass()+": "+e.getMessage());
				throw new MemoryException(); }
			finally{ st.dispose(); }
		}catch(SQLiteException e){ 
			System.err.println(e.getClass()+": "+e.getMessage());
			throw new MemoryException(); }
		finally{ db.dispose(); }
	}
	public static void build(String what, String[] how)
			throws MemoryException{
		SQLiteConnection db = new SQLiteConnection(db_file);
		try{
			db.open();
			StringBuilder request = new StringBuilder();
			request.append("CREATE TABLE ").append(what);
			request.append(" (");
			boolean first = true;
			for(String col: how){
				request.append(col);
				if(first) {
					request.append(" PRIMARY KEY");
					first = false;
				}
				request.append(",");
			}
			request.deleteCharAt(request.length()-1).append(")");
			
			SQLiteStatement st = db.prepare(request.toString());
			try{
				st.stepThrough();
			}catch(SQLiteException e){ 
				System.err.println(e.getClass()+": "+e.getMessage());
				throw new MemoryException(); }
			finally{ st.dispose(); }
		}catch(SQLiteException e){ 
			System.err.println(e.getClass()+": "+e.getMessage());
			throw new MemoryException(); }
		finally{ db.dispose(); }
	}
}