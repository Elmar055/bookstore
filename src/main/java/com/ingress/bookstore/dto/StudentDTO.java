package com.ingress.bookstore.dto;

import java.time.LocalDate;
import java.util.List;

public class StudentDTO {
    private Long id;

    private String name;

    private List<Long> subscribedAuthorIds;

    public List<Long> getSubscribedAuthorIds()
    {
        return subscribedAuthorIds;
    }

    public void setSubscribedAuthorIds(List<Long> subscribedAuthorIds)
    {
        this.subscribedAuthorIds = subscribedAuthorIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
