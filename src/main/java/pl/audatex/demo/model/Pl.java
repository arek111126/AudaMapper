package pl.audatex.demo.model;


import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Component
@Getter
public class Pl {
    private Map<String, String> subscribe;


}
