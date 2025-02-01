package com.datingapp.enums;

public enum MessageType {
	TEXT,
	VIDEO,
	IMAGE;
	
	public String toLowerCaseName() {
        return this.name().toLowerCase();
    }
}
