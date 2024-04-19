package main;

import java.util.Calendar;
import java.util.Date;
//Employee class which is a mapping for the data in each line of the CSV class
public class Employee {
    private final int employeeId;
    private final int projectId;
    private final Date dateFrom;
    private final Date dateTo;

    public Employee(int employeeId, int projectId, Date dateFrom, Date dateTo) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom != null ? dateFrom : Calendar.getInstance().getTime();
        this.dateTo = dateTo != null ? dateTo : Calendar.getInstance().getTime();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}

