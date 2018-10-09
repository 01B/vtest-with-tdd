import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by no.pain.no.code@gmail.com
 * nopainnocode.tistory.com
 */
public class ProjectMemberReport {

    //    private Map<String, Integer> projects;
    private List<List<Project>> bunchOfProjects;
    private List<Project> projects;
    private Map<String, String> alreadySignedUpStudents;
    private String line;

    public ProjectMemberReport() {
        bunchOfProjects = new ArrayList<>();
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
    }

    public String getProjectsNameAndCountOfMembers() {
        Scanner scanner = new Scanner(System.in);
        Project project = new Project();

        while (scanner.hasNext()
                && !(line = scanner.nextLine()).equals("0")) {

            if ("1".equals(line)) {
                bunchOfProjects.add(projects);
                initializeValuesForNewTemplate();
            } else {
                if (isStudentNickname(line)) {
                    if (doesStudentAleadySignedUp(line))
                        removeStudent(project);
                    else {
                        if (project.canStudentSignUpThenDo(line))
                            alreadySignedUpStudents.put(line, project.getProjectName());
                    }
                } else
                    project = initializeNewProject();

            }
        }

        return getAllProjectsNameAndCount();
    }

    private void initializeValuesForNewTemplate() {
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
    }

    private boolean isStudentNickname(String line) {
        return line.equals(line.toLowerCase());
    }

    private boolean doesStudentAleadySignedUp(String studentName) {
        return alreadySignedUpStudents.containsKey(studentName);
    }

    private Project initializeNewProject() {
        Project project = new Project();
        project.setProjectName(line);
        projects.add(project);
        return project;
    }

    private void removeStudent(Project currentProject) {
        List<Project> projects = this.projects.stream()
                .filter(e -> {
                    String duplicateProjectName = alreadySignedUpStudents.get(line);
                    return e.getProjectName().equals(duplicateProjectName)
                            && !currentProject.getProjectName().equals(duplicateProjectName);
                })
                .collect(Collectors.toList());
        if(projects.size() > 0) {
            projects.get(0).removeStudent(line);
        }
    }

    private String getAllProjectsNameAndCount() {
        StringBuilder allProjectNameAndCount = new StringBuilder();
        bunchOfProjects.forEach(e -> allProjectNameAndCount.append(getProjectCountString(e)));
        return allProjectNameAndCount.toString();
    }

    private String getProjectCountString(List<Project> projects) {
        StringBuilder returnValue = new StringBuilder();
        projects.stream()
                .sorted(Project::compareTo)
                .forEach(e ->
                        returnValue.append(e.getProjectName())
                                .append(" ")
                                .append(e.getCountOfSignedUpStudents())
                                .append("\r\n"));

        return returnValue.toString();
    }
}
