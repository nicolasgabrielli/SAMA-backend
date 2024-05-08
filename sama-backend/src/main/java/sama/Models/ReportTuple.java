package sama.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportTuple {
    private int year;
    private String reportId;

    public ReportTuple() {
    }

    public ReportTuple(int year, String reportId) {
        this.year = year;
        this.reportId = reportId;
    }

}