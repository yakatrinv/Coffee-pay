package com.coffeepay.conrtroller;

import com.coffeepay.dto.AddressDto;
import com.coffeepay.dto.MachineDto;
import com.coffeepay.service.IAddressService;
import com.coffeepay.service.IMachineService;
import com.coffeepay.service.IModelMachineService;
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

import static util.DataViews.ADD_AFTER_ADDRESS_PAGE;
import static util.DataViews.ADD_AFTER_MACHINE_PAGE;
import static util.DataViews.ATTR_ADDRESS;
import static util.DataViews.ATTR_ADDRESSES_LIST;
import static util.DataViews.ATTR_ADDRESS_ID;
import static util.DataViews.ATTR_ID;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MACHINES_LIST;
import static util.DataViews.ATTR_MODELS_MACHINE_LIST;
import static util.DataViews.ATTR_MODEL_ID;
import static util.DataViews.ATTR_MODEL_MACHINE;
import static util.DataViews.ATTR_PAGE_NAME_LIST;
import static util.DataViews.ATTR_PAGE_PAGE;
import static util.DataViews.ATTR_PAGE_SIZE;
import static util.DataViews.ATTR_PAGE_TOTAL_PAGE;
import static util.DataViews.ATTR_SEARCH_ADDRESS_CITY;
import static util.DataViews.ATTR_SEARCH_ADDRESS_STREET;
import static util.DataViews.ATTR_SEARCH_MACHINE_BRAND;
import static util.DataViews.ATTR_SEARCH_MACHINE_CITY;
import static util.DataViews.ATTR_SEARCH_MACHINE_MODEL;
import static util.DataViews.ATTR_SEARCH_MACHINE_SERIAL_NUMBER;
import static util.DataViews.ATTR_SEARCH_MACHINE_STREET;
import static util.DataViews.DEFAULT_PAGE;
import static util.DataViews.DEFAULT_PAGE_SIZE;
import static util.DataViews.PAGE_ADD_ADDRESS;
import static util.DataViews.PAGE_ADD_MACHINE;
import static util.DataViews.PAGE_EDIT_ADDRESS;
import static util.DataViews.PAGE_EDIT_MACHINES;
import static util.DataViews.PAGE_EDIT_MODEL_MACHINE;
import static util.DataViews.PAGE_LIST_ADDRESSES;
import static util.DataViews.PAGE_LIST_MACHINES;
import static util.DataViews.PAGE_REDIRECT_LIST_ADDRESSES;
import static util.DataViews.PAGE_REDIRECT_LIST_MACHINES;
import static util.DataViews.URL_DELETE;
import static util.DataViews.URL_EDIT;
import static util.DataViews.URL_NEW;
import static util.DataViews.URL_UPDATE;

@Controller
@RequiredArgsConstructor
@RequestMapping(ADD_AFTER_MACHINE_PAGE)
public class MachineController {
    private final IMachineService machineService;
    private final IModelMachineService modelMachineService;
    private final IAddressService addressService;

    @GetMapping
    public String getMachines(
            Model model,
            @RequestParam(value = ATTR_SEARCH_MACHINE_SERIAL_NUMBER,
                    defaultValue = "") String serialNumber,
            @RequestParam(value = ATTR_SEARCH_MACHINE_CITY,
                    defaultValue = "") String city,
            @RequestParam(value = ATTR_SEARCH_MACHINE_STREET,
                    defaultValue = "") String street,
            @RequestParam(value = ATTR_SEARCH_MACHINE_BRAND,
                    defaultValue = "") String brand,
            @RequestParam(value = ATTR_SEARCH_MACHINE_MODEL,
                    defaultValue = "") String nameModel,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(required = false,
                    defaultValue = DEFAULT_PAGE_SIZE) int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<MachineDto> pageable = machineService.findAll(serialNumber,nameModel,brand,city, street, pageRequest);

        model.addAttribute(ATTR_PAGE_NAME_LIST, ADD_AFTER_MACHINE_PAGE);
        model.addAttribute(ATTR_MACHINES_LIST, pageable.getContent());
        model.addAttribute(ATTR_PAGE_SIZE, pageable.getSize());
        model.addAttribute(ATTR_PAGE_PAGE, pageable.getNumber() + 1);
        model.addAttribute(ATTR_PAGE_TOTAL_PAGE,
                pageable.getTotalPages() == 0 ?
                        pageable.getTotalPages() + 1 :
                        pageable.getTotalPages());
        model.addAttribute(ATTR_SEARCH_MACHINE_SERIAL_NUMBER, serialNumber);
        model.addAttribute(ATTR_SEARCH_MACHINE_BRAND, brand);
        model.addAttribute(ATTR_SEARCH_MACHINE_MODEL, nameModel);
        model.addAttribute(ATTR_SEARCH_MACHINE_CITY, city);
        model.addAttribute(ATTR_SEARCH_MACHINE_STREET, street);

        return PAGE_LIST_MACHINES;
    }

    @GetMapping(URL_NEW)
    public String newMachine(Model model) {
        model.addAttribute(ATTR_MODELS_MACHINE_LIST,modelMachineService.getAllModels());
        model.addAttribute(ATTR_ADDRESSES_LIST,addressService.getAllAddresses());
        model.addAttribute(ATTR_MACHINE, new MachineDto());

        return PAGE_ADD_MACHINE;
    }

    @PostMapping
    public String createMachine(@ModelAttribute(ATTR_MACHINE) @Valid MachineDto machineDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PAGE_ADD_MACHINE;
        }
        machineService.save(machineDto);

        return PAGE_REDIRECT_LIST_MACHINES;
    }

    @GetMapping(URL_EDIT)
    public String editMachine(Model model,
                              @PathVariable(ATTR_ID) long id) {
        model.addAttribute(ATTR_MODELS_MACHINE_LIST,modelMachineService.getAllModels());
        model.addAttribute(ATTR_ADDRESSES_LIST,addressService.getAllAddresses());
        model.addAttribute(ATTR_MACHINE, machineService.findById(id));

        return PAGE_EDIT_MACHINES;
    }

    @PatchMapping(URL_UPDATE)
    public String updateMachine(@ModelAttribute(ATTR_MACHINE) @Valid MachineDto machineDto,
                                BindingResult bindingResult,
                                @RequestParam(value = ATTR_ADDRESS_ID, required = false) Long address_id,
                                @RequestParam(value = ATTR_MODEL_ID, required = false) Long model_id) {

        if (bindingResult.hasErrors()) {
            return PAGE_EDIT_MACHINES;
        }

        machineDto.setAddress(addressService.findById(address_id));
        machineDto.setModel(modelMachineService.findById(model_id));
        machineService.save(machineDto);

        return PAGE_REDIRECT_LIST_MACHINES;
    }

    @DeleteMapping(URL_DELETE)
    public String deleteMachine(@PathVariable(ATTR_ID) long id) {

        machineService.deleteById(id);

        return PAGE_REDIRECT_LIST_MACHINES;
    }

}
