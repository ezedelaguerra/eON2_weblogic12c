package com.freshremix.model;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractBaseModel<PKType extends Serializable> {

	private static final String FAILED_TO_GET_THE_PRIMARY_KEY_VALUE = "Failed to get the primary key value";
	private static final String FAILED_TO_FIND_THE_PRIMARY_KEY = "Failed to find the primary key";
	private static final String FAILED_TO_SET_THE_PRIMARY_KEY_VALUE = "Failed to set the primary key value";

	@Target(value = ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface PrimaryKey {
	}

	//default version of 1
	private Integer version = 1;

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	@SuppressWarnings("unchecked")
	public PKType getPrimaryKey() {
		Field pkField = findPrimaryKeyField();
		if (pkField != null){
				PropertyDescriptor propertyDescriptor;
				try {
					propertyDescriptor = new PropertyDescriptor(pkField.getName(), getClass());
					Method getMethod = propertyDescriptor.getReadMethod();
					PKType result = (PKType) getMethod.invoke(this);
					return result;
				} catch (IntrospectionException e) {
					throw new RuntimeException(FAILED_TO_GET_THE_PRIMARY_KEY_VALUE, e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(FAILED_TO_GET_THE_PRIMARY_KEY_VALUE, e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(FAILED_TO_GET_THE_PRIMARY_KEY_VALUE, e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(FAILED_TO_GET_THE_PRIMARY_KEY_VALUE, e);
				}
		}
		throw new RuntimeException(FAILED_TO_FIND_THE_PRIMARY_KEY);
	}

	public void setPrimaryKey(PKType pk) {
		Field pkField = findPrimaryKeyField();
		if (pkField != null){
			PropertyDescriptor propertyDescriptor;
			try {
				propertyDescriptor = new PropertyDescriptor(pkField.getName(), getClass());
				Method setMethod = propertyDescriptor.getWriteMethod();
				setMethod.invoke(this);

			} catch (IntrospectionException e) {
				throw new RuntimeException(FAILED_TO_SET_THE_PRIMARY_KEY_VALUE, e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(FAILED_TO_SET_THE_PRIMARY_KEY_VALUE, e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(FAILED_TO_SET_THE_PRIMARY_KEY_VALUE, e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(FAILED_TO_SET_THE_PRIMARY_KEY_VALUE, e);
			}
		}
		throw new RuntimeException(FAILED_TO_FIND_THE_PRIMARY_KEY);
	}
	
	private Field findPrimaryKeyField() {
		for (Field f : getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(PrimaryKey.class)){
				return f;
			}
		}
		
		return null;
	}

}
