package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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

        Book book = new Book();
        System.out.println("id:" + form.getId());
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
