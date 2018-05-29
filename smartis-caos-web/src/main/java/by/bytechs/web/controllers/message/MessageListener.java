package by.bytechs.web.controllers.message;

import by.bytechs.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 */

@Component
@Scope("prototype")
public class MessageListener implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ServerSocket serverSocket;

    @Autowired
    private Environment env;

    @Autowired
    private MessageService messageService;

    @Override
    public void run() {
        log.info("Инициализация соккет листнера на порту " + env.getProperty("socket.port"));

        try {
            serverSocket = new ServerSocket(Integer.parseInt(env.getProperty("socket.port")));

            while (true) {
                Socket socket = serverSocket.accept();
                try {

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String line = in.readLine();
                    StringBuilder builder = new StringBuilder();

                    while( line != null && line.length() > 0 && !line.equals("null") )
                    {
                        builder.append(line);

                        line = in.readLine();
                    }

                    // Close our connection
                    in.close();
                    socket.close();

                    log.info("Входящее сообщение передано в обработку: " + builder.toString());

                    messageService.processIncomingMessage(builder.toString());



                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
