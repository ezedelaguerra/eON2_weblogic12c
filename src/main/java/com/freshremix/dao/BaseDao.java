package com.freshremix.dao;

import java.io.Serializable;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.freshremix.exception.OptimisticLockException;
import com.freshremix.model.AbstractBaseModel;

public interface BaseDao<Entity extends AbstractBaseModel<PKType>, PKType extends Serializable> {

	/**
	 * Saves the entity. Uses the default statementName =
	 * sqlMapNameSpace+"."+"save"
	 * 
	 * @param entity
	 * @return Entity Object with the primary key set
	 */
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public abstract Entity save(Entity entity);

	/**
	 * Updates the entity. Uses the default statementName =
	 * sqlMapNameSpace+"."+"update"
	 * 
	 * @param entity
	 * @return Entity Object with updated version
	 */
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public abstract Entity update(Entity entity) throws OptimisticLockException;

	/**
	 * Retrieves a record given a primary key
	 * @param pk
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, readOnly=true)
	public abstract Entity getEntity(PKType pk);

	/**
	 * Saves or updates an entity
	 * @param entity
	 * @return
	 * @throws OptimisticLockException
	 */
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public abstract Entity saveOrUpdateEntity(Entity entity) throws OptimisticLockException;

	/**
	 * Empty's the table 
	 * @return number of records deleted
	 * 
	 */
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public abstract int deleteAll();

	/**
	 * Deletes a record by its PK
	 * @param pk
	 * @return 
	 */
	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
	public abstract void delete(PKType pk);

}