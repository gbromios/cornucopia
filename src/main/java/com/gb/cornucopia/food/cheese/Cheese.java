package com.gb.cornucopia.food.cheese;

public class Cheese {
	public static BlockCheeseAged cheese_wheel_aged;
	public static BlockCheeseYoung cheese_wheel_young;
	public static void preInit(){
		cheese_wheel_aged = new BlockCheeseAged();
		cheese_wheel_young = new BlockCheeseYoung();
		
	}
	public static void init() {
	}
	public static void postInit() {}

	
}
