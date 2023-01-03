package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.ItemDTO;
import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.enumeration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemService {

    //создание товара
    ItemDTO createItem(Item entity);

    //обновление товара
    ItemDTO updateItem(UUID itemId, Item entity);

    //поиск товара
    ItemDTO findItemById(UUID itemId);

    Optional<Item> findOptionalItem(UUID itemId);

    //отдает весь список элементов с пагинацией начинается с 0 + сортировка по полю -price || +price
    Page<ItemDTO> findAllItems(Pageable pageable);

    //отдает весь список предметов по категории  с пагинацией
    Page<ItemDTO> findItemByCategory(String category, Pageable pageable);

    //уменьшает кол-во товаров на складе если заказ успешный
    void increaseItemQuantityOnWarehouse(List<Item> itemList);

    //увеличивает кол-во товаров на складе если это удаление заказа руками админа
    void decreaseItemQuantityOnWarehouse(List<Item> itemList);

    //удаляет предмет по id
    String deleteItemByName(UUID itemId);

    //получить топ предметов по количеству продаж, лимитированы до 10
    List<ItemDTO> getTopItemsByCountBuyDesc();

    //админский доступ:
    //увеличение/уменьшение кол-ва товаров на складе
    int increaseOrDecreaseItemQuantityOnWarehouse(UUID itemId, int size, String operation);


}
