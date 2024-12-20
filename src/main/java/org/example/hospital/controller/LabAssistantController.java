package org.example.hospital.controller;

import org.example.hospital.converter.LabAssistantConverter;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.request.LabAssistantUserRequest;
import org.example.hospital.service.LabAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/labAssistant")
public class LabAssistantController {
    private final LabAssistantService labAssistantService;
    private final LabAssistantConverter labAssistantConverter = new LabAssistantConverter();

    @Autowired
    public LabAssistantController(LabAssistantService labAssistantService) {
        this.labAssistantService = labAssistantService;
    }

    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.GET)
    public ResponseEntity<LabAssistantDTO> getLabAssistant(@PathVariable Long labAssistantId)
    {
        try {
            LabAssistantDTO labAssistantDTO = labAssistantService.getLabAssistantById(labAssistantId);
            return new ResponseEntity<>(labAssistantDTO, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLabAssistant(@PathVariable Long labAssistantId)
    {
        try {
            labAssistantService.deleteLabAssistant(labAssistantId);
            return new ResponseEntity<>("Lab assistant deleted", HttpStatus.OK);

        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{labAssistantId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLabAssistant(@PathVariable Long labAssistantId, @RequestBody LabAssistantDTO labAssistantDTO)
    {
        try {
            labAssistantService.updateLabAssistant(labAssistantId,labAssistantDTO);
            return new ResponseEntity<>("Lab assistant updated", HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> addLabAssistant(@RequestBody LabAssistantUserRequest labAssistantUserRequest)
    {
        try {
            labAssistantService.addLabAssistant(labAssistantUserRequest.getLabAssistantDTO(), labAssistantUserRequest.getUserDTO());
            return new ResponseEntity<>("User registered successfully",HttpStatus.CREATED);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<LabAssistantDTO>> getAllLabAssistants()
    {
        List<LabAssistantDTO> labAssistantDTOS = labAssistantService.getAllLabAssistant();
        return new ResponseEntity<>(labAssistantDTOS, HttpStatus.OK);
    }
}
