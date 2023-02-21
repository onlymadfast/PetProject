package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.ItemDTO;
import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    /**
     * Create item object
     *
     * @param entity - item object
     * @return item dto
     */
    ItemDTO createItem(Item entity);

    /**
     * Update item object
     *
     * @param itemId - item identifier
     * @param entity - item object
     * @return item dto
     */
    ItemDTO updateItem(UUID itemId, Item entity);

    /**
     * Find item by id
     *
     * @param itemId - item identifier
     * @return item dto
     */
    ItemDTO findItemById(UUID itemId);

    /**
     * Find all items with pagination starting with 0
     * if need sort, example: -price || +price
     *
     * @param pageable - pageable object
     * @return - item list with pagination
     */
    Page<ItemDTO> findAllItems(Pageable pageable);

    /**
     * Find all items by category with pagination
     *
     * @param category - category
     * @param pageable - pageable object
     * @return - item list by category with pagination
     */
    Page<ItemDTO> findItemByCategory(String category, Pageable pageable);

    /**
     * Increase item quantity on warehouse, only if order process successfully done
     *
     * @param itemList - items list
     */
    void increaseItemQuantityOnWarehouse(List<Item> itemList);

    /**
     * Decrease item quantity on warehouse, only by admin permission
     *
     * @param itemList - items list
     */
    void decreaseItemQuantityOnWarehouse(List<Item> itemList);

    /**
     * Delete item by
     *
     * @param itemId - item identifier
     * @return - response
     */
    Response deleteItemById(UUID itemId);

    /**
     * Find popular items by count of buy, limit 10
     *
     * @return - list popular items
     */
    List<ItemDTO> getTopItemsByCountBuyDesc();

    //админский доступ:
    //увеличение/уменьшение кол-ва товаров на складе
    int increaseOrDecreaseItemQuantityOnWarehouse(UUID itemId, int size, String operation);


}
