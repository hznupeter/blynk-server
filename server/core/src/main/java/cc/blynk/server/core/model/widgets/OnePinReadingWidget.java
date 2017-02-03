package cc.blynk.server.core.model.widgets;

import cc.blynk.server.core.model.Pin;
import cc.blynk.server.core.model.auth.Session;

import static cc.blynk.server.core.protocol.enums.Command.HARDWARE;

/**
 * The Blynk Project.
 * Created by Dmitriy Dumanskiy.
 * Created on 02.02.17.
 */
public abstract class OnePinReadingWidget extends OnePinWidget implements FrequencyWidget {

    private int frequency;

    private transient long lastRequestTS;

    @Override
    public boolean isTicked(long now) {
        if (frequency > 0 && now > lastRequestTS + frequency) {
            this.lastRequestTS = now;
            return true;
        }
        return false;
    }

    @Override
    public void sendReadingCommand(Session session, int dashId) {
        if (isNotValid()) {
            return;
        }
        session.sendMessageToHardware(dashId, HARDWARE, READING_MSG_ID, Pin.makeReadingHardwareBody(pinType.pintTypeChar, pin), deviceId);
    }

}