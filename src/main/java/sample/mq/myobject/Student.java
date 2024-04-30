package sample.mq.myobject;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Student implements Serializable {
    private long id;
    private String fullName;

    public Student() {
    }

    public Student(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
