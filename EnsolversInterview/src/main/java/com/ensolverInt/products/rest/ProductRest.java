package com.ensolverInt.products.rest;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

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
public class ProductRest {
	
	
	// This comment (Autowired), indicates to the springboot compiler to create an object that implements ItemsDAO interface
	// as we know, interfaces can't be instantiated.
	@Autowired
	private ItemsDAO iDAO;
	@Autowired
	//private FolderDAO fDAO;
	
	//private Folder actFolder;
	
//	@RequestMapping(value="", method = RequestMethod.GET)
//	public String welcome()
//	{
//		return "welcome";
//	}
//	
	/**
	 * 
	 * This method receive the username and password from the view, this params are send by aplication user
	 */
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public ResponseEntity<List<Folder>> checkUsAndPass(String username, String password, ModelMap mp)
//	{
//		if(username.equals("user") && password.equals("user123") ) return ResponseEntity.ok(fDAO.findAll());	
//		else return ResponseEntity.noContent().build();
//	}
//	
	// This method send to the view a list of all the items saved in DB
	@GetMapping
	public ResponseEntity<List<Item>> getItems()
	{
		return ResponseEntity.ok(iDAO.findAll());
	}
	
	//This method simulates an sql inner join
//	private List<Item> innerJoin(List<Item> l, Long idF) {
//		List<Item> ret= new LinkedList<Item>();
//		for(Item i : l)
//		{
//			if(i.getFolder().getId() == idF)
//			{
//				ret.add(i);
//			}
//		}
//		return ret;
//	}

//	@RequestMapping(value="/index/new", method= RequestMethod.GET)
//	public String newItem (ModelMap mp)
//	{
//		return "new";
//	}
//	
	
	/**
	 * This method receives an item from the view, with the name that the user entered
	 * 
	 */
	@PostMapping
	public ResponseEntity<List<Item>> createItem (@RequestBody Item item)
	{
		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
		item.setId(100);
		//item.setFolder(actFolder);
		iDAO.save(item);
		return ResponseEntity.ok(iDAO.findAll());
	}
	
	/**
	 * 
	 * This method receives the id of the item that want to be delete. It look for the item in BD and destroy it
	 */
	@DeleteMapping(value= "{itemId}")
	public ResponseEntity<List<Item>> deleteItem (@PathVariable("itemId") Long itemId)
	{
		iDAO.deleteById(itemId);
		return ResponseEntity.ok(iDAO.findAll());
	}
	

	
	
	//The methods below represet the implementation of phase 2
	
//	@RequestMapping(value="folderIndex", method=RequestMethod.GET)
//	public String getFolders(ModelMap mp)
//	{
//		mp.put("folders", fDAO.findAll());
//		return "folderIndex";
//	}
//	
//	@RequestMapping(value="/folderNew", method= RequestMethod.GET)
//	public String newFolder (ModelMap mp)
//	{
//		return "folderNew";
//	}
//	
//	
//	@RequestMapping(value="/folderCreate" , method=RequestMethod.POST )
//	public String createFolder (Folder folder,ModelMap mp)
//	{
//		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
//		folder.setId(100);
//		fDAO.save(folder);
//		mp.put("folders", fDAO.findAll());
//		return "folderIndex";
//	}
//	
//	@RequestMapping(value="deleteFolder/{idFolder}", method=RequestMethod.GET)
//	public String deleteFolder(ModelMap mp,@PathVariable Long idFolder)
//	{
//		actFolder= fDAO.findById(idFolder).get();
//		deleteItems(iDAO.findAll(),actFolder.getId());
//		fDAO.deleteById(idFolder);
//		mp.put("folders", fDAO.findAll());
//		return "folderIndex";
//	}
//
//	private void deleteItems(List<Item> l, long id) {
//		for (Item i : l)
//		{
//			if (i.getFolder().getId()== id)
//			{
//				iDAO.delete(i);
//			}
//		}
//	}
	
	
	
}
