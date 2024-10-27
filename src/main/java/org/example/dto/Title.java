package org.example.dto;

public record Title(Long id,String line) {
    private  static long count = 0;
    public Title(Title origin){
         this(count++, origin.line);
    }

}
