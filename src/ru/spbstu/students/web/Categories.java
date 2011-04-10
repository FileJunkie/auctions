package ru.spbstu.students.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Categories {
	
	Lite("Lite",1),
    Full("Full",2),
    VIP("VIP",3);
	
	private String label;
	private int key;

    private Categories(String label, int key) {
        this.label = label;
        this.key = key;
    }
    
    public static Categories getByLabel(String label) {
        for (Categories f : values()) {
            if (f.label.equals(label)) {
                return f;
            }
        }
		return null;
    }
    
    public static int getKeyByLabel(String label) {
    	for (Categories f : values()) {
            if (f.label.equals(label)) {
                return f.key;
            }
        }
		return (Integer) null;
    }
    
    public static List<String> labelList() {
        List<String> res = new ArrayList<String>();
        
        for (Categories f : values()) {
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
