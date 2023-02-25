package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.ItemDTO;
import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.service.api.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.tsipadan.petproject.utils.SortUtils.parseSort;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/view/list")
    private Page<ItemDTO> getAllItems(@Nullable Integer page,
                                      @Nullable Integer size,
                                      @Nullable String sort) {
        page = page == null ? 0 : page > 0 ? page - 1 : page;
        size = size == null ? 10 : size;
        Sort parsedSort = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size).withSort(parsedSort);
        return itemService.findAllItems(pageable);
    }

    @GetMapping("/view/{itemId}")
    private ItemDTO getItem(@PathVariable UUID itemId) {
        return itemService.findItemById(itemId);
    }

    @GetMapping("/view/item/category/{category}")
    private Page<ItemDTO> getItemByCategory(@PathVariable String category,
                                            @Nullable Integer page,
                                            @Nullable Integer size) {
        page = page == null ? 0 : page > 0 ? page - 1 : page;
        size = size == null ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);
        return itemService.findItemByCategory(category, pageable);
    }

    @GetMapping("/view/popular")
    public List<ItemDTO> getTopItems() {
        return itemService.getTopItemsByCountBuyDesc();
    }

    @PostMapping("/edit")
    private ItemDTO createItem(@RequestBody Item entity) {
        return itemService.createItem(entity);
    }

    @PutMapping("/edit/{itemId}")
    private ItemDTO updateItem(@PathVariable UUID itemId,
                               @RequestBody Item entity) {
        return itemService.updateItem(itemId, entity);
    }

    @DeleteMapping("/edit/{itemId}")
    private Response deleteItem(@PathVariable UUID itemId) {
        return itemService.deleteItemById(itemId);
    }

//    @PutMapping("/update/image/{name}/{category}")
//    public ItemDTO updateImage(@PathVariable String name,
//                               @PathVariable Category category) {
//        return itemService.updateImage(name, category);
//    }

//    @GetMapping(path = "/image/{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
//        return Files.readAllBytes(Paths.get(System.getProperty("user.home")
//                + "/IdeaProjects/PetProject/src/main/resources/items/" + fileName));
//    }


}
