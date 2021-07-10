package com.ensolverInt.products.rest;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensolverInt.products.daos.FolderDAO;
import com.ensolverInt.products.daos.ItemsDAO;
import com.ensolverInt.products.entities.Folder;
import com.ensolverInt.products.entities.Item;


/***
 * 
 * @author Jeronimo Alfonsin
 * 
 * This class acts like a bridge between frontend and backend, all methods in this class return a url(String),
 * this is like this becuase with this urls the controller knows what template show to us.
 * The RequestMapping before the class declaration indicates the base path for the class and it works the same
 * way in methods.
 * The object ModelMap is a way to share object between frontend and backend
 *
 */


@RestController
@RequestMapping("/items")
@CrossOrigin("*")
public class ItemRest {
	
	
	// This comment (Autowired), indicates to the springboot compiler to create an object that implements ItemsDAO interface
	// as we know, interfaces can't be instantiated.
	@Autowired
	private ItemsDAO iDAO;
	
	@Autowired
	private FolderDAO fDAO;
	private long folderAct;
	// This method send to the view a list of all the items saved in DB
	@RequestMapping(value="{idFolder}")
	public ResponseEntity<List<Item>> getItems(@PathVariable("idFolder") Long idFolder)
	{
		folderAct= idFolder;
		return ResponseEntity.ok(innerJoin(iDAO.findAll(),idFolder));
	}
	
	//This method simulates an sql inner join
	private List<Item> innerJoin(List<Item> l, Long idF) {
		List<Item> ret= new LinkedList<Item>();
		for(Item i : l)
		{
			if(i.getFolder().getId() == idF)
			{
				ret.add(i);
			}
		}
		return ret;
	}
	
	/**
	 * This method receives an item from the view, with the name that the user entered
	 * 
	 */
	@PostMapping
	public ResponseEntity<Item> createItem (@RequestBody Item item)
	{
		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
		item.setId(100);
		item.setFolder(fDAO.findById(folderAct).get());
		Item itemS=iDAO.save(item);
		return ResponseEntity.ok(itemS);
	}
	
	/**
	 * 
	 * This method receives the id of the item that want to be delete. It look for the item in BD and destroy it
	 */
	@DeleteMapping(value= "{itemId}")
	public ResponseEntity<Item> deleteItem (@PathVariable("itemId") Long itemId)
	{
		Item itemd=iDAO.findById(itemId).get();
		iDAO.deleteById(itemId);
		return ResponseEntity.ok(itemd);
	}
	
	@PutMapping()
	public ResponseEntity<Item> updateItems(@RequestBody Item item)
	{
		Item iAct= iDAO.findById(item.getId()).get();
		iAct.setName(item.getName());
		iDAO.save(iAct);
		return ResponseEntity.ok(iAct);
	}
}
