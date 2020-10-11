package com.fundoo.label.controller;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.service.ILabelService;
import com.fundoo.note.dto.ResponseDto;
import com.fundoo.note.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/fundoo/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createLabel(@Valid @RequestBody LabelDto labelDto, @RequestHeader(value = "Authorization", required = false) String token) throws AuthenticationException {
        if (labelService.createLabel(labelDto,token))
            return new ResponseDto("Label Created", 201);
        return new ResponseDto("Error Creating label", 400);
    }

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto editLabel(@Valid @RequestBody LabelDto labelDto, @RequestHeader(value = "Authorization", required = false) String token) throws AuthenticationException {
        if (labelService.editLabel(labelDto, token))
            return new ResponseDto("Label Edited Successfully", 201);
        return new ResponseDto("Error Editing label", 400);
    }

}
