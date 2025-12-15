package entity;

import java.util.Date;

public class Updatable {
    private final Date createDate;
    private Date updateDate;

    public Updatable() {
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return ", createDate=" + createDate +
                ", updateDate=" + updateDate;
    }
}
