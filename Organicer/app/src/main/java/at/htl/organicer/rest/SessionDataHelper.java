package at.htl.organicer.rest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.LinkedList;

import at.htl.organicer.entities.Student;
import at.htl.organicer.entities.Subject;
import at.htl.organicer.entities.Teacher;
import at.htl.organicer.entities.TimeGrid;
import at.htl.organicer.entities.TimeTable;
import at.htl.organicer.entities.Weekday;
import at.htl.organicer.entities.TimeUnit;

/**
 * Created by phili on 29.11.2017.
 */

public class SessionDataHelper {
    private RestHelperAlternative.AuthData authData;
    private LinkedList<Teacher> teachers;
    private LinkedList<Student> students;
    private LinkedList<Subject> subjects;
    private LinkedList<TimeGrid> timeGrids;

    /*
    private LinkedList<Teacher> classes;
    private LinkedList<Teacher> subjects;
    private LinkedList<Teacher> rooms;
     */

    //region Getters and Setters

    public LinkedList<TimeGrid> getTimeGrids() {
        return timeGrids;
    }

    private void setTimeGrids(LinkedList<TimeGrid> timeGrids) {
        this.timeGrids = timeGrids;
    }

    public LinkedList<Teacher> getTeachers() {
        return teachers;
    }


    private void setTeachers(LinkedList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public LinkedList<Student> getStudents() {
        return students;
    }

    private void setStudents(LinkedList<Student> students) {
        this.students = students;
    }

    public LinkedList<Subject> getSubjects() {
        return subjects;
    }

    private void setSubjects(LinkedList<Subject> subjects) {
        this.subjects = subjects;
    }


    //endregion

    public SessionDataHelper(){
        setTeachers(new LinkedList<Teacher>());
        setStudents(new LinkedList<Student>());
        setSubjects(new LinkedList<Subject>());
    }


    void parseData(JSONArray data, String parsetype) throws JSONException {
        JSONObject actual;
        //JSONArray dids;
        //LinkedList<Integer> didsAsInteger = new LinkedList<>();

        switch (parsetype) {
            case "teacher":
                Teacher actualTeacher;
                LinkedList<Teacher> tempTeachers = new LinkedList<>();
                for (int i = 0; i < data.length(); i++) {
                    actual = (JSONObject) data.get(i);
                    actualTeacher = new Teacher();
                    actualTeacher.setId(actual.getInt("id"));
                    actualTeacher.setName(actual.getString("name"));
                    actualTeacher.setFirstName(actual.getString("foreName"));
                    actualTeacher.setLastName(actual.getString("longName"));
                    actualTeacher.setTitle(actual.getString("title"));
                    actualTeacher.setActive(actual.getBoolean("active"));

                    //dids
                /*
                dids = actual.getJSONArray("dids");
                for (int j = 0; j < dids.length(); j++) {
                    actual = (JSONObject) dids.get(i);
                    didsAsInteger.add(actual.getInt("id"));
                }

                actualTeacher.setDids(didsAsInteger);
                */

                    tempTeachers.add(actualTeacher);
                }

                setTeachers(tempTeachers);

                break;

            case "subject":
                Subject actualSubject;
                LinkedList<Subject> tempSubjects = new LinkedList<>();
                for (int i = 0; i < data.length(); i++) {
                    actual = (JSONObject) data.get(i);
                    actualSubject = new Subject();
                    actualSubject.setId(actual.getInt("id"));
                    actualSubject.setName(actual.getString("name"));
                    actualSubject.setLongName(actual.getString("longName"));
                    actualSubject.setAlternateName(actual.getString("alternateName"));
                    actualSubject.setActive(actual.getBoolean("active"));

                    tempSubjects.add(actualSubject);
                }

                setSubjects(tempSubjects);
                break;

            case "student":
                Student actualStudent;
                LinkedList<Student> tempStudents = new LinkedList<>();
                for (int i = 0; i < data.length(); i++) {
                    actual = (JSONObject) data.get(i);
                    actualStudent = new Student();
                    actualStudent.setId(actual.getInt("id"));
                    actualStudent.setApiKey(actual.getString("key"));
                    actualStudent.setName(actual.getString("name"));
                    actualStudent.setFirstName(actual.getString("foreName"));
                    actualStudent.setLastName(actual.getString("longName"));
                    actualStudent.setGender(actual.getString("gender"));


                    tempStudents.add(actualStudent);
                }

                setStudents(tempStudents);
                break;

            case "timegrid":
                TimeGrid actualTimeGrid;
                LinkedList<TimeGrid> tempTimeGrids = new LinkedList<>();
                JSONArray timeUnits;
                JSONObject actualTimeUnit;
                LinkedList<TimeUnit> tempTimeUnits = new LinkedList<>();
                TimeUnit actualTimeUnitObject;
                for (int i = 0; i < data.length(); i++) {
                    actual = (JSONObject) data.get(i);
                    actualTimeGrid = new TimeGrid();
                    actualTimeGrid.setWeekday(Weekday.values()[actual.getInt("day")-1]);

                    timeUnits = actual.getJSONArray("timeUnits");

                    for(int j = 0; j < timeUnits.length(); j++){
                        actualTimeUnit = timeUnits.getJSONObject(j);
                        actualTimeUnitObject = new TimeUnit();

                        String from,  to;
                        from = String.valueOf(actualTimeUnit.getInt("startTime"));
                        to = String.valueOf(actualTimeUnit.getInt("endTime"));


                        actualTimeUnitObject.setName(actualTimeUnit.getString("name"));
                        actualTimeUnitObject.setStartTime(from);
                        actualTimeUnitObject.setEndTime(to);


                        tempTimeUnits.add(actualTimeUnitObject);
                    }
                    actualTimeGrid.setTimeUnits(tempTimeUnits);
                    tempTimeUnits = new LinkedList<>();


                    tempTimeGrids.add(actualTimeGrid);
                }

                setTimeGrids(tempTimeGrids);
                break;

            case "timeTable":
                TimeTable actualTimeTable = new TimeTable();
                JSONArray tempArray;

                for(int i = 0; i < data.length();  i++){
                    actual = (JSONObject) data.get(i);
                    actualTimeTable.setId(actual.getInt("id"));

                }
                break;
            default:
                throw new RuntimeException("Invalid parsetype");
        }
    }

    public RestHelperAlternative.AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(RestHelperAlternative.AuthData authData) {
        this.authData = authData;
    }
}
