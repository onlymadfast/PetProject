package com.example.tsipadan.petproject.repository;

import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.enumeration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Page<Item> findItemByCategory(Category category, Pageable pageable);
    Optional<Item> findByName(String name);
    List<Item> findByOrderByCountBuyDesc();

}
