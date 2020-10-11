package com.fundoo.label.service;


import com.fundoo.label.dto.LabelDto;
import com.fundoo.note.exception.AuthenticationException;

public interface ILabelService {
    boolean createLabel(LabelDto labelDto, String token) throws AuthenticationException;

    boolean editLabel(LabelDto labelDto, String token) throws AuthenticationException;

}
