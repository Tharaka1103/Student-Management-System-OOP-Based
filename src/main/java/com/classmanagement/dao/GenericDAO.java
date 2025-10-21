package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import java.util.List;

/**
 * Generic DAO Interface
 * Demonstrates Abstraction and Interface OOP concepts
 * @author Class Management Team
 * @version 1.0
 * @param <T> Entity type
 */
public interface GenericDAO<T> {
    
    /**
     * Create new entity
     * @param entity Entity to create
     * @return Created entity with generated ID
     * @throws DatabaseException if operation fails
     */
    T create(T entity) throws DatabaseException;
    
    /**
     * Update existing entity
     * @param entity Entity to update
     * @return true if successful, false otherwise
     * @throws DatabaseException if operation fails
     */
    boolean update(T entity) throws DatabaseException;
    
    /**
     * Delete entity by ID
     * @param id Entity ID
     * @return true if successful, false otherwise
     * @throws DatabaseException if operation fails
     */
    boolean delete(int id) throws DatabaseException;
    
    /**
     * Find entity by ID
     * @param id Entity ID
     * @return Entity if found, null otherwise
     * @throws DatabaseException if operation fails
     */
    T findById(int id) throws DatabaseException;
    
    /**
     * Get all entities
     * @return List of all entities
     * @throws DatabaseException if operation fails
     */
    List<T> findAll() throws DatabaseException;
}