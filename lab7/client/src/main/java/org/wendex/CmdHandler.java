package org.wendex;

import org.zeromq.ZMQ;

public class CmdHandler {


    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong arguments count");

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket requester = context.socket()
    }
}
