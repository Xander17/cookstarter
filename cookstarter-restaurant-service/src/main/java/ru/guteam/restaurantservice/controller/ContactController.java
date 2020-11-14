package ru.guteam.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guteam.restaurantservice.dto.ContactDTO;
import ru.guteam.restaurantservice.model.Contact;
import ru.guteam.restaurantservice.service.ContactService;

import static org.springframework.http.HttpStatus.OK;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.contactAndStatus;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.status;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addContact(@RequestHeader(JWT_HEADER) String token,
                                                 @RequestBody ContactDTO contact) {
        contactService.saveContact(contact);
        return status(OK);
    }

    @CrossOrigin
    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<ContactDTO> getContact(@RequestHeader(JWT_HEADER) String token,
                                                 @PathVariable Long restaurantId) {
        ContactDTO contact = contactService.getContactByRestaurantId(restaurantId);
        return contactAndStatus(contact, OK);
    }

    @CrossOrigin
    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateContact(@RequestHeader(JWT_HEADER) String token,
                                                    @RequestBody ContactDTO contact) {
        contactService.updateContact(contact);
        return status(OK);
    }

    @CrossOrigin
    @GetMapping("/delete/{restaurantId}")
    public ResponseEntity<HttpStatus> deleteContact(@RequestHeader(JWT_HEADER) String token,
                                                    @PathVariable Long restaurantId) {
        contactService.deleteContactByRestaurantId(restaurantId);
        return status(OK);
    }
}
