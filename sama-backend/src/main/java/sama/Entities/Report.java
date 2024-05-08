package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.Models.Category;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "report")
public class Report {

    @Id
    private String id;
    private int year;
    private List<Category> categories;
    private Date creationDate;
    private Date lastUpdatedDate;
    private String state;
    private String companyId;

    public Report(String companyId) {
    }

    public Report() {
    }

    public Report(int year, List<Category> categories, Date creationDate, Date lastUpdatedDate, String state, String companyId) {
        this.year = year;
        this.categories = categories;
        this.creationDate = creationDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.state = state;
        this.companyId = companyId;
    }
}