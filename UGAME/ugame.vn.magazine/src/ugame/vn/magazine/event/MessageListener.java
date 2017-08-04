package ugame.vn.magazine.event;

import ugame.vn.magazine.model.Message;

public interface MessageListener {
	void onMessage(ClientSocketHandler handler,Message msg);
}
