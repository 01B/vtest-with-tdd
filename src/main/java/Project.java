import java.util.HashMap;
import java.util.Map;

/**
 * Created by no.pain.no.code@gmail.com
 * nopainnocode.tistory.com
 */
public class Project implements Comparable<Project> {
    private String projectName;
    private Map<String, String> signedUpStudents;

    public Project() {
        signedUpStudents = new HashMap<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCountOfSignedUpStudents() {
        return signedUpStudents.size();
    }

    public void removeStudent(String studentName) {
        if (signedUpStudents.containsKey(studentName)) {
            signedUpStudents.remove(studentName);
        }
    }

    public boolean canStudentSignUpThenDo(String studentNickName) {
        if (canStudentSignUpForThisProject(studentNickName)) {
            signUpForProject(studentNickName);
            return true;
        }
        return false;
    }

    private boolean canStudentSignUpForThisProject(String studentNickName) {
        return projectName != null
                && !projectName.isEmpty()
                && !signedUpStudents.containsKey(studentNickName);
    }

    private void signUpForProject(String studentNickName) {
        signedUpStudents.put(studentNickName, projectName);
    }

    @Override
    public int compareTo(Project o) {
        if (Integer.compare(getCountOfSignedUpStudents(), o.getCountOfSignedUpStudents()) == 0)
            return projectName.compareTo(o.projectName);
        return Integer.compare(getCountOfSignedUpStudents(), o.getCountOfSignedUpStudents()) * -1;
    }
}
