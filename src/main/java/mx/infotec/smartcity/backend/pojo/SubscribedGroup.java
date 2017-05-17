package mx.infotec.smartcity.backend.pojo;

import mx.infotec.smartcity.backend.model.Group;

public class SubscribedGroup extends Group {
    private boolean subscribed;

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public void convert(Group group) {
        this.setId(group.getId());
        this.setDateCreated(group.getDateCreated());
        this.setDateModified(group.getDateModified());
        this.setGroup(group.getGroup());
        this.setNotificationIds(group.getNotificationIds());

    }

}
