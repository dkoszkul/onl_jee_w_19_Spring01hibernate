package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.dao.BookDao;
import pl.coderslab.dao.PublisherDao;
import pl.coderslab.model.Book;
import pl.coderslab.model.Publisher;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("bookForm")
public class BookFormController {

    private static final String BOOK_FORM_ADD = "bookForm-add";

    private final BookDao bookDao;
    private final PublisherDao publisherDao;

    @GetMapping
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return BOOK_FORM_ADD;
    }

    @PostMapping
    public String handleAddBookForm(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return BOOK_FORM_ADD;
        }

        log.info("Adding new book - {}", book);
        bookDao.saveBook(book);
        return "redirect:/bookForm/all";
    }


    @GetMapping("all")
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDao.findAll());

        return "bookForm-list";
    }

    @ModelAttribute("publishers")
    public List<Publisher> getPublishers() {
        return publisherDao.findAll();
    }
}
