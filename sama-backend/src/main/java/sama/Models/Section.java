package sama.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Section {
    private String name;
    private List<Field> fields;
}
