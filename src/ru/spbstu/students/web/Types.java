package ru.spbstu.students.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Types {
	
	Buyer("Buyer",1),
    Seller("Seller",2);
	
	private String label;
	private int key;

    private Types(String label, int key) {
        this.label = label;
        this.key = key;
    }
    
    public static Types getByLabel(String label) {
        for (Types f : values()) {
            if (f.label.equals(label)) {
                return f;
            }
        }
		return null;
    }
    
    public static int getKeyByLabel(String label) {
    	for (Types f : values()) {
            if (f.label.equals(label)) {
                return f.key;
            }
        }
		return (Integer) null;
    }
    
    public static List<String> labelList() {
        List<String> res = new ArrayList<String>();
        
        for (Types f : values()) {
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
