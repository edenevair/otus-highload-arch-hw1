package ru.otus.highload.hw.controller.api.dto;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class ListDataHolder<T> implements Serializable {

    private static final Long serialVersionUID = 1L;

    private List<T> userList;

    public ListDataHolder(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            this.userList = Collections.emptyList();
        }
        this.userList = ImmutableList.copyOf(list);
    }
}
