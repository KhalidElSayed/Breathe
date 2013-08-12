package com.github.johnpersano.breathe.graphs;

import android.graphics.Path;
import android.graphics.Region;

public class Bar {
    private int color;
	private String name;
	private float value;
	private Path path;
	private Region region;

	private String mTopLabel;

	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

    
    public void setBarTopLabel(String topLabel) {
    	
    	mTopLabel = topLabel;
    	
    }
    
    public String getTopBarLabel() {
    	
    	return mTopLabel;
    	
    }

}
