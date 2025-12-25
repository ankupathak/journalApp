package com.portfolio.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteResponse {
    private String text;
    private String author;
    private String[] tags;
}
