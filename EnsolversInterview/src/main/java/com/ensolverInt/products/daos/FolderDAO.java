package com.ensolverInt.products.daos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ensolverInt.products.entities.Folder;


public interface FolderDAO extends JpaRepository<Folder, Long> {

}
