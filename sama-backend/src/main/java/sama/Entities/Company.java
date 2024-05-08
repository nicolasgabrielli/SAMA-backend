package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sama.Models.ReportTuple;

import java.util.List;

@Setter
@Getter
@Document(collection = "company")
public class Company {

    @Id
    private String id;

    private String name;

    private List<ReportTuple> reports;

    public Company() {
    }

    public void addReport(Report report) {
        reports.add(new ReportTuple(report.getYear(), report.getId()));
    }
}