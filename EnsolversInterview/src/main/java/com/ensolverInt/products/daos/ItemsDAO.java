package com.ensolverInt.products.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensolverInt.products.entities.Item;

public interface ItemsDAO extends JpaRepository<Item, Long> {

}
