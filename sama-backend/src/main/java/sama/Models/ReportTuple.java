package sama.Models;

public class ReportTuple {
    private int year;
    private String reportId;

    public ReportTuple() {
    }

    public ReportTuple(int year, String reportId) {
        this.year = year;
        this.reportId = reportId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}