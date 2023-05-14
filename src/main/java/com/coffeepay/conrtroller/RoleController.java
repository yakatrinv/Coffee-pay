package com.coffeepay.conrtroller;

import com.coffeepay.dto.RoleDto;
import com.coffeepay.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static util.DataViews.ADD_AFTER_ROLES_PAGE;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_ROLE;
import static util.DataViews.ATTR_ROLES_LIST;
import static util.DataViews.ATTR_SEARCH_ROLE_NAME;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_ROLE;
import static util.DataViews.PAGE_EDIT_ROLE;
import static util.DataViews.PAGE_LIST_ROLES;
import static util.DataViews.PAGE_REDIRECT_LIST_ROLES;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_ROLES_PAGE)
public class RoleController {
    private final IRoleService roleService;

    @GetMapping
    public String getRoles(Model model,
                           @RequestParam(value = ATTR_SEARCH_ROLE_NAME, defaultValue = "") String name,
                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<RoleDto> pageable = roleService.findAll(name, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_ROLES_PAGE);
        model.addAttribute(ATTR_ROLES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE, pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_ROLE_NAME, name);

        return PAGE_LIST_ROLES;
    }

    @GetMapping(URL_NEW)
    public String newRole(Model model) {
        model.addAttribute(ATTR_ROLE, new RoleDto());

        return PAGE_ADD_ROLE;
    }

    @PostMapping
    public String createRole(@ModelAttribute(ATTR_ROLE) @Valid RoleDto roleDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_ADD_ROLE;
        }
        roleService.save(roleDto);

        return PAGE_REDIRECT_LIST_ROLES;
    }

    @GetMapping(URL_EDIT)
    public String editRole(Model model,
                           @PathVariable(ATTR_ID) Integer id) {

        model.addAttribute(ATTR_ROLE, roleService.findById(id));

        return PAGE_EDIT_ROLE;
    }

    @PatchMapping(URL_UPDATE)
    public String updateRole(@ModelAttribute(ATTR_ROLE) @Valid RoleDto roleDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_ROLE;
        }

        roleService.save(roleDto);

        return PAGE_REDIRECT_LIST_ROLES;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteRole(@PathVariable(ATTR_ID) Integer id) {

        roleService.deleteById(id);

        return PAGE_REDIRECT_LIST_ROLES;
    }

}
