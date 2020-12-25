package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.io.InputStream;
import java.util.Scanner;

public class CmdHandler {
    static String URL_HEAD = "tcp://";

    private Scanner scanner;

    public CmdHandler(InputStream source) {
        this.scanner = new Scanner(source);
    }

    public DataRequest

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong arguments count");

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket requester = context.socket(SocketType.REQ);
        requester.connect(URL_HEAD + args[0]);
        System.out.println("Launch and connect client");


    }
}
