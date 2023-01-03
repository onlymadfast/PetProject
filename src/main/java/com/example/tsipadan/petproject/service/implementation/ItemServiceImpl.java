package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.dto.ItemDTO;
import com.example.tsipadan.petproject.exception.EntityDuplicateException;
import com.example.tsipadan.petproject.exception.EntityNotFoundException;
import com.example.tsipadan.petproject.exception.IncorrectValueException;
import com.example.tsipadan.petproject.mapper.ItemMapper;
import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.enumeration.Category;
import com.example.tsipadan.petproject.repository.ItemRepository;
import com.example.tsipadan.petproject.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String DELETE = "Item has been deleted";

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Override
    public ItemDTO findItemById(UUID itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isPresent()) {
            return itemMapper.mapToDTO(itemOptional.get());
        } else
            throw new EntityNotFoundException("Item with < " + itemId + " > doesn't exist ");
    }

    @Override
    public Optional<Item> findOptionalItem(UUID itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public Page<ItemDTO> findAllItems(Pageable pageable) {
        Page<Item> pageResult = itemRepository.findAll(pageable);
        List<ItemDTO> itemDTOS = pageResult.map(itemMapper::mapToDTO).toList();
        return new PageImpl<>(itemDTOS, pageable, pageResult.getTotalElements());
    }

    @Override
    public Page<ItemDTO> findItemByCategory(String category, Pageable pageable) {
        Page<Item> pageResult = itemRepository.findItemByCategory(Category.valueOf(category), pageable);
        List<ItemDTO> itemDTOS = pageResult.map(itemMapper::mapToDTO).toList();
        return new PageImpl<>(itemDTOS, pageable, pageResult.getTotalElements());
    }

    @Override
    public void increaseItemQuantityOnWarehouse(List<Item> itemList) {
        itemList.forEach(item -> {
            if (item.getQuantity() > 0) {
                Optional<Item> itemOptional = itemRepository.findById(item.getId());
                if (itemOptional.isPresent()) {
                    Item i = itemOptional.get();
                    i.setQuantityOnWarehouse(i.getQuantityOnWarehouse() + i.getQuantity());
                    Item save = itemRepository.save(i);
                    log.info("Decrease the quantity of item < {} >", save.getId());
                } else
                    throw new EntityNotFoundException("Item with id < " + item.getId() + " > doesn't exist");
            } else
                throw new IncorrectValueException("Quantity of item < 0");
        });
    }

    @Transactional
    @Override
    public void decreaseItemQuantityOnWarehouse(List<Item> itemList) {
        itemList.forEach(item -> {
            if (item.getQuantity() > 0) { //возможно не нужно, так как подразумевается, что отображение товаров >0
                Optional<Item> itemOptional = itemRepository.findById(item.getId());
                if (itemOptional.isPresent()) {
                    Item i = itemOptional.get();
                    i.setQuantityOnWarehouse(i.getQuantityOnWarehouse() - i.getQuantity());
                    Item save = itemRepository.save(i);
                    log.info("Decrease the quantity of item < {} >", save.getId());
                } else
                    throw new EntityNotFoundException("Item with id < " + item.getId() + " > doesn't exist");
            } else
                throw new IncorrectValueException("Quantity of item < 0");
        });
    }

    @Transactional
    @Override
    public ItemDTO createItem(Item entity) {
        Optional<Item> find = itemRepository.findByName(entity.getName());
        if (find.isPresent()) {
            throw new EntityDuplicateException("Item with given name < " + entity.getName() + " > already exist");
        } else {
            return setItemFieldsOnCreateOrUpdate(null, entity);
        }
    }

    @Transactional
    @Override
    public ItemDTO updateItem(UUID itemId, Item entity) {
        Optional<Item> find = itemRepository.findById(itemId);
        if (find.isPresent()) {
            return setItemFieldsOnCreateOrUpdate(find.get(), entity);
        } else
            throw new EntityNotFoundException("Item with id < " + itemId + " > doesn't exist");
    }

//    @Override
//    public ItemDTO updateImage(String name, Category category) {
//        Item item = itemRepository.findItemByName(name);
//        if (item == null) throw new EntityNotFoundException("Not found item with name: " + name);
//        item.setImage(setImageUrl(category));
//        log.info("Update image by item: " + name + " from category " + category);
//        return itemMapper.mapToDTO(itemRepository.save(item));
//    }

    @Override
    public String deleteItemByName(UUID itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isPresent()) {
            itemRepository.deleteById(itemId);
            return DELETE;
        } else
            throw new EntityNotFoundException("Item with id < " + itemId + " > doesn't exist");
    }

    @Override
    public List<ItemDTO> getTopItemsByCountBuyDesc() {
        return itemRepository.findByOrderByCountBuyDesc().stream()
                .filter(item -> item.getCountBuy() > 0)
                .map(itemMapper::mapToDTO)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int increaseOrDecreaseItemQuantityOnWarehouse(UUID itemId, int size, String operation) {
        Optional<Item> optionalItem = findOptionalItem(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (operation.equals("INCREASE")) {
                item.setQuantityOnWarehouse(item.getQuantityOnWarehouse() + size);
            } else if (operation.equals("DECREASE")) {
                item.setQuantityOnWarehouse(item.getQuantityOnWarehouse() - size);
            }
            Item save = itemRepository.save(item);
            log.info("{} the number of item < " + itemId + " > " +
                    "in stock to < " + save.getQuantityOnWarehouse() + " >", operation);
            return save.getQuantityOnWarehouse();
        } else
            throw new EntityNotFoundException("Item with id < " + itemId + " > doesn't exist");
    }


    private String setImageUrl(Category category) {
        String[] clothing = { "clothing1.jpg", "clothing2.jpg", "clothing3.jpg" };
        String[] shoes = { "shoes1.jpg", "shoes2.jpg", "shoes3.jpg" };
        String[] acc = { "acc1.jpg", "acc2.jpg", "acc3.jpg" };
        String[] randomPic = { "ran1.jpg", "ran2.jpg", "ran3.jpg" };

        switch (category) {
            case CLOTHING: return ServletUriComponentsBuilder.fromCurrentContextPath().path("/item/image/"
                    + clothing[new Random().nextInt(3)]).toUriString();
            case SHOES: return ServletUriComponentsBuilder.fromCurrentContextPath().path("/item/image/"
                    + shoes[new Random().nextInt(3)]).toUriString();
            case ACCESSORIES: return ServletUriComponentsBuilder.fromCurrentContextPath().path("/item/image/"
                    + acc[new Random().nextInt(3)]).toUriString();
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/item/image/"
                + randomPic[new Random().nextInt(3)]).toUriString();
    }

    private ItemDTO setItemFieldsOnCreateOrUpdate(Item item, Item entity) {
        if (item == null) { //create
            Item create = new Item();
            setFields(create, entity);
            ItemDTO result = itemMapper.mapToDTO(itemRepository.save(create));
            log.info("Create item with id < {} >", result.getId());
            return result;
        } else { //update
            setFields(item, entity);
            ItemDTO result = itemMapper.mapToDTO(itemRepository.save(item));
            log.info("Update item with id < {} >", result.getId());
            return result;
        }
    }

    private void setFields(Item item, Item entity) {
        item.setImage(setImageUrl(entity.getCategory())); //временное решение с картинками
        item.setName(entity.getName());
        item.setDescription(entity.getDescription());
        item.setQuantity(entity.getQuantity());
        item.setQuantityOnWarehouse(entity.getQuantityOnWarehouse());
        item.setPrice(entity.getPrice());
        item.setCategory(entity.getCategory());
        item.setCountBuy(entity.getCountBuy());
        item.setOnSale(entity.isOnSale());
        if (entity.getOrder() != null) {
            item.setOrder(entity.getOrder());
        }
    }

    //stop on realization field images https://www.youtube.com/watch?v=8ZPsZBcue50&t=4955s

}
