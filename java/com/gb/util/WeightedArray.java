package com.gb.util;

import java.util.ArrayList;
import java.util.Random;

public class WeightedArray<T> {
	private ArrayList<Choice<T>> choices = new ArrayList<Choice<T>>(); 
	private int totalWeight = 0;
	public WeightedArray(){}

	// returns this to allow lazy in line defining. why cant i generic array :(
	public WeightedArray<T> add(T t, int weight){
		this.choices.add(new Choice<T>(t, weight));
		this.totalWeight += weight;
		return this;
	}

	public T getRandom(Random r) {
		int i = r.nextInt(this.totalWeight);
		for (Choice<T> c : this.choices) {
			if ((i -= c.weight) < 0)	{
				return c.choice;
			}
		}
		throw new RuntimeException("this should never happen, there's a bug in WeightedArray :(");
	}
	
	public static final class Choice<T>{
		public final T choice;
		public final int weight;
		public Choice(T choice, int weight) {
			this.weight = weight;
			this.choice = choice;
		}
	}

	
}

