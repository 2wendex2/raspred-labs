package org.wendex;

public class ServerListMessage {
    private int[] ports;

    public int[] getPorts() {
        return ports;
    }

    public ServerListMessage(int[] ports) {
        this.ports = ports;
    }
}
