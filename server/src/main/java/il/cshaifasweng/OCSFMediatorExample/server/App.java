package il.cshaifasweng.OCSFMediatorExample.server;

import java.util.concurrent.CountDownLatch;

public class App {

    private static SimpleServer server;

    public static void main(String[] args) throws Exception {

      server = new SimpleServer(3000);
      server.listen();
    }
}