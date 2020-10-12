package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.note.exception.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RefreshScope
public class LabelService implements ILabelService {

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    @Lazy
    RestTemplate restTemplate;

    @Value("{microservice.user-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    @Override
    public boolean createLabel(LabelDto labelDto, String token) throws AuthenticationException {
        Label label = new Label();
        BeanUtils.copyProperties(labelDto, label);
        try {
            Integer userId = restTemplate.getForObject(ENDPOINT_URL + token, Integer.class);
            label.setUserId(userId);
            labelRepository.save(label);
            return true;
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
    }

    @Override
    public boolean editLabel(LabelDto labelDto, String token) throws AuthenticationException {
        try {
            restTemplate.getForObject(ENDPOINT_URL + token, Integer.class);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
        Optional<Label> label = labelRepository.findById(labelDto.getLabel_Id());
        return label.map((value) -> {
            value.setLabelName(labelDto.getLabelName());
            value.setModifiedDate(LocalDateTime.now());
            labelRepository.save(value);
            return true;
        }).orElseThrow(() -> new LabelException("Label Not Present", 404));
    }

}

