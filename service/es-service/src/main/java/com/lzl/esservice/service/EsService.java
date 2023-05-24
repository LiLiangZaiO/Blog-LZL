package com.lzl.esservice.service;

import com.lzl.common_utils.domain.R;
import com.lzl.esservice.entity.RequestParams;

public interface EsService {
    R search(RequestParams params);

    R uploadDocument();

    R getSuggestions(String prefix);

    void deleteById(String id);

    void insertById(String id);
}
