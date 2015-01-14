package com.UpGrade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;

public class ClassListManager {

	ArrayList<UpGradeClass> list = null;
	Context context = null;

	public ClassListManager(Context c) {
		context = c;

		list = readFromInternalStorage();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<UpGradeClass> readFromInternalStorage() {
		ArrayList<UpGradeClass> toReturn;
		FileInputStream fis;
		try {
			fis = context.openFileInput("storage");
			ObjectInputStream oi = new ObjectInputStream(fis);
			toReturn = (ArrayList<UpGradeClass>) oi.readObject();
			oi.close();
			fis.close();
		} catch (FileNotFoundException e) {
			Log.e("InternalStorage", e.getMessage());
			toReturn = new ArrayList<UpGradeClass>();
		} catch (IOException e) {
			Log.e("InternalStorage", e.getMessage());
			toReturn = new ArrayList<UpGradeClass>();
		} catch (ClassNotFoundException e) {
			Log.e("InternalStorage", e.getMessage());
			toReturn = new ArrayList<UpGradeClass>();
		}
		return toReturn;
	}

	public void saveToInternalStorage(ArrayList<UpGradeClass> list) {

		// Sort the list first
		Collections.sort(list, new Comparator<Object>() {

			@Override
			public int compare(Object lhs, Object rhs) {
				
				UpGradeClass left = (UpGradeClass) lhs;
				UpGradeClass right = (UpGradeClass) rhs;
				return left.getName().compareTo(right.getName());
			}

		});

		try {
			context.deleteFile("storage");
			FileOutputStream fos = context.openFileOutput("storage",
					Context.MODE_PRIVATE);
			ObjectOutputStream of = new ObjectOutputStream(fos);
			of.writeObject(list);
			of.flush();
			of.close();
			fos.close();
		} catch (Exception e) {
			Log.e("InternalStorage", e.getMessage());
		}
	}

	public ArrayList<UpGradeClass> getList() {
		return list;
	}

	// finds and deletes a class from the data.
	public boolean deleteClass(int pos) {

		list.remove(pos);
		saveToInternalStorage(list);
		return true;

	}

	public void updateClassinList(UpGradeClass toUpdate) {

		list.remove(toUpdate);
		// toUpdate.updateClass();
		list.add(toUpdate);
		this.saveToInternalStorage(list);

	}

	public void addClass(UpGradeClass toAdd) {
		list.add(toAdd);
		this.saveToInternalStorage(list);
	}

}
