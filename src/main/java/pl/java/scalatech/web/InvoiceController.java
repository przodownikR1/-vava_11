package pl.java.scalatech.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Invoice;
import pl.java.scalatech.entity.InvoiceType;
import pl.java.scalatech.repository.InvoiceRepository;

@Controller
@RequestMapping("/invoice")
@Slf4j
public class InvoiceController extends AbstractRepoController<Invoice>{

    private final static String INVOICE_VIEW = "invoice";
    private final static String INVOICE_EDIT = "invoiceEdit";
    private final static String INVOICE_REDIRECT = "redirect:/invoice/";

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(JpaRepository<Invoice,Long> invoiceRepository) {
       super(invoiceRepository);
       this.invoiceRepository = (InvoiceRepository) invoiceRepository;
    }
    @RequestMapping("/all")
    public @ResponseBody List<Invoice> invoices(){
        return invoiceRepository.findAll();

    }

    @ModelAttribute("types")
    public InvoiceType[] invoiceTypes() {
        return InvoiceType.values();
    }

    @Override
    protected String getView() {
        return INVOICE_VIEW;
    }

    @Override
    protected String getEditView() {
        return INVOICE_EDIT;
    }

    @Override
    protected Invoice createEmpty() {
        return new Invoice();
    }

    @Override
    protected String getRedirect() {
        return INVOICE_REDIRECT;
    }

}
