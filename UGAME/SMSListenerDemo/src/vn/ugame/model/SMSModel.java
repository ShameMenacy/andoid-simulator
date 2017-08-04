package vn.ugame.model;

import java.util.ArrayList;
import java.util.List;

public class SMSModel {

    private String sms;
    private List<PropertyChangedListener> propertyChangedListenerList;

    public String getSms() {
	return sms;
    }

    public void setSms(String sms) {
	if (!this.sms.equals(sms)) {
	    this.sms = sms;
	    fireSMSChangedEvent();
	}
    }

    public SMSModel() {
	this.sms = "No message";
	this.propertyChangedListenerList = new ArrayList<PropertyChangedListener>();
    }

    public void addSMSChangedListener(PropertyChangedListener listener) {
	this.propertyChangedListenerList.add(listener);
    }

    private void fireSMSChangedEvent() {
	for (PropertyChangedListener listener : propertyChangedListenerList) {
	    listener.onPropertyChanged(this);
	}
    }
}
