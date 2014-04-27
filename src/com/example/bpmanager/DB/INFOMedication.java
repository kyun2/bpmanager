package com.example.bpmanager.DB;

import java.util.ArrayList;

public class INFOMedication {
	
	public static ArrayList<INFOMedication> InfoData;
	
	public int mId;
	public String mName;
	public String mImg;
	public String mDescription;

	public INFOMedication(int id, String name, String img, String description) 
	{
		mId = id;
		mName = name;
		mImg = img;
		mDescription = description;
	}
	
	public static void Make()
	{
		InfoData = new ArrayList<INFOMedication>();
		
		for (int i = 0; i < 50; i++)
		{
			InfoData.add(new INFOMedication(i, "Drug" + (i+1), "", "Drug" + (i+1) + " 입니다."));
		}
	}
	
	public static INFOMedication getInfoMedicine(int id)
	{
		return InfoData.get(id);
	}
}
