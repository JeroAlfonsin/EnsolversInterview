package com.ensolverInt.products.rest;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ensolverInt.products.daos.FolderDAO;
import com.ensolverInt.products.daos.ItemsDAO;
import com.ensolverInt.products.entities.Folder;
import com.ensolverInt.products.entities.Item;


@RestController
@RequestMapping("/folders")
@CrossOrigin("*")
public class FolderRest {

	@Autowired
	private FolderDAO fDAO;
	@Autowired
	private ItemsDAO iDAO;
	
	@GetMapping
	public ResponseEntity<List<Folder>> getFolders()
	{
		return ResponseEntity.ok(fDAO.findAll());
	}
	@PostMapping
	public ResponseEntity<Folder> createFolder (@RequestBody Folder folder)
	{
		// Its hardcoded because when iDAO want to save the item in DB, if id is null it dosn't work  
		folder.setId(100);
		//item.setFolder(actFolder);
		Folder folderS=fDAO.save(folder);
		return ResponseEntity.ok(folderS);
	}
	
	@DeleteMapping(value= "{folderId}")
	public ResponseEntity<Folder> deleteItem (@PathVariable("folderId") Long folderId)
	{
		Folder folderD=fDAO.findById(folderId).get();
		deleteItems(iDAO.findAll(), folderId);
		fDAO.deleteById(folderId);
		return ResponseEntity.ok(folderD);
	}
	
	@PutMapping()
	public ResponseEntity<Folder> updateItems(@RequestBody Folder folder)
	{
		Folder folderUp= fDAO.findById(folder.getId()).get();
		folderUp.setName(folder.getName());
		fDAO.save(folderUp);
		return ResponseEntity.ok(folderUp);
	}
	
	private void deleteItems(List<Item> l, long id) {
		for (Item i : l){
				if (i.getFolder().getId()== id){
				iDAO.delete(i);
			}
		}
	}

}
