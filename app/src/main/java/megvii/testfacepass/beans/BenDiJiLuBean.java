package megvii.testfacepass.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


@Entity
public class BenDiJiLuBean {
    @Id
    private long id;
    private long subjectId ;
    private String subjectType;
    private String discernPlace;
    private String identificationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getDiscernPlace() {
        return discernPlace;
    }

    public void setDiscernPlace(String discernPlace) {
        this.discernPlace = discernPlace;
    }

    public String getIdentificationTime() {
        return identificationTime;
    }

    public void setIdentificationTime(String identificationTime) {
        this.identificationTime = identificationTime;
    }

    @Override
    public String toString() {
        return "BenDiJiLuBean{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", subjectType='" + subjectType + '\'' +
                ", discernPlace='" + discernPlace + '\'' +
                ", identificationTime='" + identificationTime + '\'' +
                '}';
    }
}
