package com.ensolverInt.products.rest;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


@Controller
@RequestMapping("/")
public class ProductRest {
	
	
	// This comment (Autowired), indicates to the springboot compiler to create an object that implements ItemsDAO interface
	// as we know, interfaces can't be instantiated.
	@Autowired
	private ItemsDAO iDAO;
	@Autowired
	private FolderDAO fDAO;
	
	private Folder actFolder;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String welcome()
	{
		return "/welcome";
	}
	
	/**
	 * 
	 * This method receive the username and password from the view, this params are send by aplication user
	 */
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkUsAndPass(String username, String password, ModelMap mp)
	{
		if(username.equals("user") && password.equals("user123") )
		{
			mp.put("folders", fDAO.findAll());
			return "/folderIndex";
		}
			
		else return("/userOrPassInc");
	}
	
	// This method send to the view a list of all the items saved in DB
	@RequestMapping(value="index/{idFolder}", method=RequestMethod.GET)
	public String getItems(ModelMap mp,@PathVariable Long idFolder)
	{
		actFolder= fDAO.findById(idFolder).get();
		List<Item> l= iDAO.findAll();
		List<Item> lRet= innerJoin(l,actFolder.getId());
		mp.put("items", lRet);
		return "/index";
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

	@RequestMapping(value="/index/new", method= RequestMethod.GET)
	public String newItem (ModelMap mp)
	{
		return "/new";
	}
	
	
	/**
	 * This method receives an item from the view, with the name that the user entered
	 * 
	 */
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public String createItem (Item item,ModelMap mp)
	{
		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
		item.setId(100);
		item.setFolder(actFolder);
		iDAO.save(item);
		List<Item> l= iDAO.findAll();
		List<Item> lRet= innerJoin(l,actFolder.getId());
		mp.put("items", lRet);
		return "/index";
	}
	
	/**
	 * 
	 * This method receives the id of the item that want to be delete. It look for the item in BD and destroy it
	 */
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteItem (@PathVariable("id") Long itemId, ModelMap mp)
	{
		iDAO.deleteById(itemId);
		mp.put("items", iDAO.findAll());
		return "/index";
	}
	
	/**
	 * 
	 * It´s similar to deleteItem, because it receives an id and it look for the item in BD, but in this case
	 * it send back the item to the view so the user can edit it.
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editItem(@PathVariable("id") Long itemId, ModelMap mp)
	{
		mp.put("item", iDAO.findById(itemId));
		return "/edit";
	}
	
	
	// This method save the edited item, and send to the view the list of all items
	
	@RequestMapping(value="/act", method= RequestMethod.POST)
	public String actItem(ModelMap mp, Item i)
	{
		Item iAct= iDAO.findById(i.getId()).get();
		iAct.setName(i.getName());
		iDAO.save(iAct);
		mp.put("items", iDAO.findAll());
		return "/index";
			
	}
	
	
	//The methods below represet the implementation of phase 2
	
	@RequestMapping(value="folderIndex", method=RequestMethod.GET)
	public String getFolders(ModelMap mp)
	{
		mp.put("folders", fDAO.findAll());
		return "/folderIndex";
	}
	
	@RequestMapping(value="/folderNew", method= RequestMethod.GET)
	public String newFolder (ModelMap mp)
	{
		return "/folderNew";
	}
	
	
	@RequestMapping(value="/folderCreate" , method=RequestMethod.POST )
	public String createFolder (Folder folder,ModelMap mp)
	{
		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
		folder.setId(100);
		fDAO.save(folder);
		mp.put("folders", fDAO.findAll());
		return "/folderIndex";
	}
	
	@RequestMapping(value="deleteFolder/{idFolder}", method=RequestMethod.GET)
	public String deleteFolder(ModelMap mp,@PathVariable Long idFolder)
	{
		actFolder= fDAO.findById(idFolder).get();
		deleteItems(iDAO.findAll(),actFolder.getId());
		fDAO.deleteById(idFolder);
		mp.put("folders", fDAO.findAll());
		return "/folderIndex";
	}

	private void deleteItems(List<Item> l, long id) {
		for (Item i : l)
		{
			if (i.getFolder().getId()== id)
			{
				iDAO.delete(i);
			}
		}
	}
	
	
	
}
