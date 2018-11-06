import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by no.pain.no.code@gmail.com
 * nopainnocode.tistory.com
 */
public class ProjectMemberReport {

    private List<List<Project>> bunchOfProjects;
    private List<Project> projects;
    private Map<String, String> alreadySignedUpStudents;
    private String line;

    ProjectMemberReport() {
        bunchOfProjects = new ArrayList<>();
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
    }

    public String getProjectsNameAndCountOfMembers() {
        Scanner scanner = new Scanner(System.in);
        Project project = new Project();

        while (isNextLineExist(scanner))
            if (isEndOfOneTemplate())
                addProjectAndInitialize();
            else
                if (isStudentNickname(line))
                    dealWithStudentNickName(project);
                else
                    project = initializeNewProject();

        return getAllProjectsNameAndCount();
    }

    private boolean isNextLineExist(Scanner scanner) {
        return scanner.hasNext()
                && !(line = scanner.nextLine()).equals("0");
    }

    private void dealWithStudentNickName(Project project) {
        if (doesStudentAlreadySignedUp(line))
            removeStudent(project);
        else
            if (project.canStudentSignUpThenDo(line))
                alreadySignedUpStudents.put(line, project.getProjectName());
    }

    private boolean isEndOfOneTemplate() {
        return "1".equals(line);
    }

    private void addProjectAndInitialize() {
        bunchOfProjects.add(projects);
        initializeValuesForNewTemplate();
    }

    private void initializeValuesForNewTemplate() {
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
    }

    private boolean isStudentNickname(String line) {
        return line.equals(line.toLowerCase());
    }

    private boolean doesStudentAlreadySignedUp(String studentName) {
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
