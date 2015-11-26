package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Invoice;
import pl.java.scalatech.repository.InvoiceRepository;

@Controller
@RequestMapping("/invoice")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class InvoiceController {
    
    
    private final @NonNull InvoiceRepository invoiceRepo;
    
    @RequestMapping("/")
    public String invoice(Model model){
        model.addAttribute("invoice", new Invoice());
        return "invoice";
    }

}
