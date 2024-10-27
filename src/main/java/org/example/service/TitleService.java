package org.example.service;

import org.example.dto.Title;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TitleService {
    private final List<Title> titleList = List.of(
            new Title(0L, "X-rays"),
            new Title(1L, "Weather"),
            new Title(2L, "x way")
    );

    private final Map<Long, Title> numberToString ;

    public TitleService() {
        List<Title> titles = new ArrayList<>(titleList);
        numberToString = titles.stream().collect(Collectors.toMap(Title::id, t -> t));
    }

    public Title getTitle(long id) {
       return numberToString.values().stream().filter(t -> t.id() == id).findFirst().orElseThrow();
    }

    public Collection<Title> getAll(){
        return  numberToString.values();
    }

    public Collection<Title> getWithParam(String startWithLikeX){
        String lowerCaseStartWithLikeX = startWithLikeX.toLowerCase(Locale.ROOT);
       return startWithLikeX.isBlank() ? numberToString.values() : numberToString.values()
                .stream().filter(t -> t.line().toLowerCase(Locale.ROOT).startsWith(lowerCaseStartWithLikeX)).toList();
    }
    public Title createTitle(Title example){
        if(example.id() != null) throw new IllegalArgumentException("Title can't create because id setting by database");
        if(numberToString.values().stream().anyMatch(t -> t.line().equals(example.line())))
            throw new IllegalArgumentException("This title exists");
        Title title = new Title(example);
        numberToString.put(title.id(),title);
        return numberToString.get(title.id());
    }
}


