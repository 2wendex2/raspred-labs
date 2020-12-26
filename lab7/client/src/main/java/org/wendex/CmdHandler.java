package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.io.InputStream;
import java.util.Scanner;

public class CmdHandler {
    static String PROXY_URL = "tcp://localhost:5559";
    static String PUT_CMD = "PUT";
    static String GET_CMD = "GET";
    static int REQ_TIMEOUT = 10000;

    private Scanner scanner;

    public CmdHandler(InputStream source) {
        this.scanner = new Scanner(source);
    }

    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    public DataRequest nextRequest() {
        String line = scanner.nextLine();
        String[] args = line.trim().split("\\s");
        if (args.length == 1 && args[0].length() == 0)
            return null;
        if (args[0].equals(PUT_CMD)) {
            if (args.length != 3)
                throw new IllegalArgumentException("Wrong argument number");
            int cell = Integer.parseInt(args[1]);
            int value = Integer.parseInt(args[2]);
            return new PutRequest(cell, value);
        } else if (args[0].equals(GET_CMD)) {
            if (args.length != 2)
                throw new IllegalArgumentException("Wrong argument number");
            int cell = Integer.parseInt(args[1]);
            return new GetRequest(cell);
        }
        throw new IllegalArgumentException("Unknown command");
    }

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket requester = context.socket(SocketType.REQ);
        requester.connect(PROXY_URL);
        requester.setReceiveTimeOut(REQ_TIMEOUT);
        System.out.println("Launch and connect client");

        CmdHandler handler = new CmdHandler(System.in);
        while (handler.hasNext()){
            DataRequest request;
            try {
                request = handler.nextRequest();
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            if (request == null)
                continue;
            byte[] b = request.toBytes();
            requester.send(request.toBytes(), 0);

            byte[] r = requester.recv();
            if (request instanceof GetRequest) {
                if (r == null)
                    System.out.println("GET: TIMEOUT");
                else if (r.length != 1)
                    System.out.println("GET: " + BytesTools.bytesToInt(r));
                else
                    System.out.println("GET: FAILURE");
            }
            else {
                if (r == null) {
                    System.out.println("PUT: TIMEOUT");
                    continue;
                }
                boolean success = BytesTools.bytesToBool(r);
                if (success)
                    System.out.println("PUT: SUCCESS");
                else
                    System.out.println("PUT: FAILURE");
            }
        }
        requester.close();
        context.term();
    }
}
