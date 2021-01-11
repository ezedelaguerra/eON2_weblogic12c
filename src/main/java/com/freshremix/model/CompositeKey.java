package com.freshremix.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class to store composite keys.
 * 
 * Can be used if you need multiple values as key-reference for the java Map.
 * 
 * Ex. You want a Date-Seller-Buyer combination as key for the map of orders.
 * 
 * You can do this: CompositeKey<String> key = new CompositeKey<String>(3);
 * key.add(0,key1); key.add(1,key2); key.add(2,key3); or
 * 
 * You can do this: CompositeKey<String> key = new
 * CompositeKey<String>(Key1,Key2,Key3);
 * 
 * @author michael
 * 
 * @param <T>
 */
public class CompositeKey<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> compositeKeyFields;
	private int numberOfFields = 1;

	public CompositeKey(){

	}

	/**
	 * Creates the object with a number of fields
	 * 
	 * @param numberOfFields
	 */
	public CompositeKey(int numberOfFields) {
		super();
		if (numberOfFields <= 0) {
			throw new IllegalArgumentException("Invalid number of Fields");
		}
		this.numberOfFields = numberOfFields;
		compositeKeyFields = new ArrayList<T>(this.numberOfFields);
	}

	/**
	 * Creates a composite key with variable arguments
	 * @param fields
	 */
	public CompositeKey(T... fields) {
		super();
		if (fields == null || fields.length == 0) {
			throw new IllegalArgumentException("Invalid number of Fields");
		}

		this.numberOfFields = fields.length;
		compositeKeyFields = Arrays.asList(fields);
	}

	/**
	 * Puts a key on a particular position
	 * 
	 * @param position
	 * @param value
	 */
	public void putKey(int position, T value) {
		if (position >= this.numberOfFields || position <0) {
			throw new IllegalArgumentException("Invalid number of Fields");
		}
		compositeKeyFields.add(position, value);
	}

	/**
	 * Gets the key located at the position
	 * 
	 * @param position
	 * @return
	 */
	public T getKey(int position) {
		if (position >= this.numberOfFields || position <0) {
			throw new IllegalArgumentException("Invalid position");
		}
		return this.compositeKeyFields.get(position);
	}

	/**
	 * returns the number of key fields
	 * 
	 * @return
	 */
	public int getNumberOfFields() {
		return this.numberOfFields;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		for (T key : compositeKeyFields) {
			hcb.append(key);
		}

		return hcb.toHashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey<T> other = (CompositeKey<T>) obj;

		if (this.getNumberOfFields() != other.getNumberOfFields()) {
			return false;
		}
		EqualsBuilder eb = new EqualsBuilder();
		for (int i = 0; i < this.numberOfFields; i++) {

			eb.append(this.getKey(i), other.getKey(i));
		}
		return eb.isEquals();
	}

	@Override
	public String toString() {
		return "CompositeKey [compositeKeyFields=" + compositeKeyFields + "]";
	}
	
	

}
