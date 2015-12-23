package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.java.scalatech.entity.Role;
import pl.java.scalatech.repository.RoleRepository;

@Controller
@RequestMapping("/role")
public class RoleController extends AbstractRepoController<Role>{
    private final static String ROLE_VIEW = "role";
    private final static String ROLE_EDIT = "roleEdit";
    private final static String ROLE_REDIRECT = "redirect:/role/";
   
    @Autowired
    private CounterService counterService;
    
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(JpaRepository<Role,Long> roleRepository) {
       super(roleRepository);
       this.roleRepository = (RoleRepository) roleRepository;
    }


    @Override
    protected String getView() {
        counterService.increment("role-getView");
        return ROLE_VIEW;
    }

    @Override
    protected String getEditView() {
        counterService.increment("role-editView");
        return ROLE_EDIT;
    }

    @Override
    protected Role createEmpty() {
        return new Role();
    }

    @Override
    protected String getRedirect() {
        return ROLE_REDIRECT;
    }
}
