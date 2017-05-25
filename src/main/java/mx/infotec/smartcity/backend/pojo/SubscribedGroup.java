package mx.infotec.smartcity.backend.pojo;

import java.util.List;

import mx.infotec.smartcity.backend.model.Group;

public class SubscribedGroup extends Group {
    private boolean subscribed;
    private List<String> notificacionNames;

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

	public List<String> getNotificacionNames() {
		return notificacionNames;
	}

	public void setNotificacionNames(List<String> notificacionNames) {
		this.notificacionNames = notificacionNames;
	}
    
    

}
