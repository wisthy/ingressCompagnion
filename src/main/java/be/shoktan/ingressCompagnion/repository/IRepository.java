package be.shoktan.ingressCompagnion.repository;

import java.util.List;

import be.shoktan.ingressCompagnion.exceptions.NotFoundException;

/**
 * root interface for repositories
 * @author wisthler
 *
 * @param <T>
 */
public interface IRepository<T> {
	
	/**
	 * 
	 * @return the number of element of that type in the repository
	 */
	long count();
	
	/**
	 * save a new item into the repository
	 * @param item the item to save
	 * @return the saved item
	 */
	T save(T item);
	
	/**
	 * update an existing item into the repository
	 * @param item the item to update
	 */
	void update(T item);
	
	/**
	 * search an item based on its id
	 * @param id the id of the searched item
	 * @return the item
	 * @throws NotFoundException when the item with that id cannot be found in the repository
	 */
	T findOne(long id) throws NotFoundException;
	
	/**
	 * 
	 * @return the list of all the items of that type in the repository
	 */
	List<T> findAll();
}
