package com.example.bpmanager.Data;

public enum HabitType {
	SALT(0),WEIGT(1), WAIST(2), EXER(3), DRINK(4), SMOKE(5), STRESS(6);
	private int value;
	 
    private HabitType(int value) {
            this.value = value;
    }
}
