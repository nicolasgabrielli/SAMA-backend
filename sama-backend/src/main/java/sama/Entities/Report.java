package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.Models.Section;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "report")
public class Report {

    @Id
    private String id;
    private int year;
    private Date creationDate;
    private Date lastUpdatedDate;
    private List<Section> sections;
    private String state;

    public Report(String reportId) {
    }
}