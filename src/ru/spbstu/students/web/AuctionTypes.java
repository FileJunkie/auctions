package ru.spbstu.students.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum AuctionTypes {
	
	English("English",1),
	Dutch("Dutch",2);
	
	private String label;
	private int key;

    private AuctionTypes(String label, int key) {
        this.label = label;
        this.key = key;
    }
    
    public static AuctionTypes getByLabel(String label) {
        for (AuctionTypes f : values()) {
            if (f.label.equals(label)) {
                return f;
            }
        }
		return null;
    }
    
    public static int getKeyByLabel(String label) {
    	for (AuctionTypes f : values()) {
            if (f.label.equals(label)) {
                return f.key;
            }
        }
		return (Integer) null;
    }
    
    public static List<String> labelList() {
        List<String> res = new ArrayList<String>();
        
        for (AuctionTypes f : values()) {
            res.add(f.label);
        }
        Collections.sort(res);
        return res;
    }
    public String getLabel() {
        return this.label;
    }

	public int getKey() {
		return key;
	}

}
