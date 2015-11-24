package com.godchin.codelife.ui.wifi;


import android.os.Parcel;
import android.os.Parcelable;

public class SeeExistwifiwifiMessage implements Parcelable{
public String wifiName;
public String wifiCode;
public SeeExistwifiwifiMessage()
{
	
}
@Override
public int describeContents() {
	return 0;
}

@Override
public void writeToParcel(Parcel dest, int flags) {
	dest.writeString(wifiName);
	dest.writeString(wifiCode);
}
public static final Parcelable.Creator<SeeExistwifiwifiMessage> CREATOR = new Parcelable.Creator<SeeExistwifiwifiMessage>() {

	@Override
	public SeeExistwifiwifiMessage createFromParcel(Parcel source) {
		SeeExistwifiwifiMessage wifimsg = new SeeExistwifiwifiMessage();
		wifimsg.wifiName = source.readString();
		wifimsg.wifiCode = source.readString();
		return wifimsg;
	}
	@Override
	public SeeExistwifiwifiMessage[] newArray(int size) {
		return new SeeExistwifiwifiMessage[size];
	}
};
}
