package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

//Class with supporting methods which add up to the end result of the task
public class Utility {
    //reads the file line by line and creates Employee record from each line read
    public static List<Employee> readEmployeesFromCSV(String fileName) {
        List<Employee> employees = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();

            while (line != null) {

                String[] attributes = line.split(",");

                Employee employee = createEmployeeRecord(attributes);

                employees.add(employee);

                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return employees;
    }

    // Finds the maximum number(days) amongst all the cooperations that occurred
    public static Cooperation findMaxCooperation(List<Cooperation> cooperations) {
        return cooperations
                .stream()
                .max(Comparator.comparingLong(Cooperation::getCooperationDurationInDays))
                .orElse(null);
    }


    // saves all the matches that occur between colleagues in common projects in a
    // separate class
    public static List<Cooperation> saveCooperationInformation(List<Employee> employees) {

        long cooperationInDays = 0;
        List<Cooperation> cooperations = new ArrayList<>();

        for (int i = 0; i < employees.size() - 1; i++) {

            for (int j = i; j < employees.size(); j++) {

                if (employees.get(i).equals(employees.get(j)))
                    continue;

                if (employees.get(i).getProjectId() != employees.get(j).getProjectId())
                    continue;

                cooperationInDays = calculateOverlap(employees.get(i).getDateFrom(), employees.get(i).getDateTo(),
                        employees.get(j).getDateFrom(), employees.get(j).getDateTo());

                if (cooperationInDays == 0)
                    continue;

                Cooperation existingMatch = findMatch(cooperations, employees.get(i).getEmployeeId(),
                        employees.get(j).getEmployeeId());

                if (existingMatch == null)
                    cooperations.add(new Cooperation(cooperationInDays, employees.get(i),
                            employees.get(j)));
                else {
                    cooperationInDays += existingMatch.getCooperationDurationInDays();
                    existingMatch.setCooperationDurationInDays(cooperationInDays);
                }
            }
        }
        return cooperations;
    }

    // checks the current couple of employees: if they have already worked together
    // on a project or if it's their first time
    public static Cooperation findMatch(List<Cooperation> coops, Integer emplId1, Integer emplId2) {
        for (Cooperation coop : coops) {
            if ((coop.getEmployee1().getEmployeeId() == emplId1 || coop.getEmployee2().getEmployeeId() == emplId1)
                    && (coop.getEmployee1().getEmployeeId() == emplId2 || coop.getEmployee2().getEmployeeId() == emplId2))
                return coop;
        }
        return null;
    }

    public static Employee createEmployeeRecord(String[] metadata) {
        final int employeeId = Integer.parseInt(metadata[0].trim());
        final int projectId = Integer.parseInt(metadata[1].trim());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            String dateFromRaw = metadata[2].trim();
            dateFrom = !dateFromRaw.equals("NULL") ? formatter.parse(dateFromRaw) : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String dateToRaw = metadata[3].trim();
            dateTo = !dateToRaw.equals("NULL") ? formatter.parse(dateToRaw) : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Employee(employeeId, projectId, dateFrom, dateTo);
    }


    //calculates whether it has overlap between two given dates
    //and if so it returns long value representing the overlap in days
    private static long calculateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {

        if (startDate1.after(endDate2) || endDate1.before(startDate2))
            return 0;

        final Date startOverlap = startDate1.after(startDate2) ? startDate1 : startDate2;
        final Date endOverlap = endDate1.before(endDate2) ? endDate1 : endDate2;

        final long diffInMillies = Math.abs(endOverlap.getTime() - startOverlap.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static void printResultCooperationData(String fileName) {
        List<Employee> employees = Utility.readEmployeesFromCSV(fileName);
        List<Cooperation> cooperations = saveCooperationInformation(employees);
        Cooperation longestCooperation = findMaxCooperation(cooperations);
        System.out.println("Employees with ids: " + longestCooperation.getEmployee1().getEmployeeId() + " and "
                + longestCooperation.getEmployee2().getEmployeeId() + " have worked together for "
                + longestCooperation.getCooperationDurationInDays() + " days.");
    }
}
