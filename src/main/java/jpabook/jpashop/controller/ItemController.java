package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**** 아이템 등록 ****/
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        // 코드가 지저분하니 다음부턴 생성 메서드를 만들어서 사용할것.
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/"; // 저장된 책 목록으로 바로가게끔 설정
    }

    /**** 아이템 목록 ****/
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemsList";
    }

    /**** 아이템 수정 ****/
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Book item = (Book) itemService.findItem(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        // Book을 보내는게 아니라 Form을 보낸다
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable String itemId) {

        /* Form을 Service,Repository 계층에 직접 넘기기엔 지저분해서 Controller에서 어설프게 Book 엔티티를 생성하여 파라미터로 넘겼다.
         * 지금은 이렇게 작성했지만, 코드가 깔끔하지 않다. 리팩토링시 아래 코드들을
         * itemService.updateItem(itemId, form.getName(), form,getPrice(),....); 형식으로 넘겨주도록 리팩토링한다.
         * 만약에 메서드에 넘길 데이터가 너무 많다 싶으면 Service계층에 DTO를 만들고(ex) UpdateItemDto ),
         * public void updateItem(Long itemId, UpdateItemDto itemDto) {}으로 사용할 메서드를 만들어도 된다.
         */
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }
}
