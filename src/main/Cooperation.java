package main;

//Class which keeps record of the total cooperation in days between two employees
public class Cooperation {
    private long cooperationDurationInDays;
    private Employee employee1;
    private Employee employee2;

    public Cooperation(long durationCooperationInDays, Employee employee1, Employee employee2) {
        this.cooperationDurationInDays = durationCooperationInDays;
        this.employee1 = employee1;
        this.employee2 = employee2;
    }

    public long getCooperationDurationInDays() {
        return cooperationDurationInDays;
    }

    public void setCooperationDurationInDays(long cooperationDurationInDays) {
        this.cooperationDurationInDays = cooperationDurationInDays;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public void setEmployee(Employee employee1) {
        this.employee1 = employee1;
    }

    public Employee getEmployee2() {
        return employee2;
    }

    public void setEmployee2(Employee employee2) {
        this.employee2 = employee2;
    }
}
