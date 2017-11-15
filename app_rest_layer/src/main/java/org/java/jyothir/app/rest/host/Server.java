package org.java.jyothir.app.rest.host;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Server {
    private static final int PORT = 8080;
    private static final String HTTP_LOCALHOST = "http://localhost";
    private static final String JERSEY_SERVLET_CONTEXT_PATH = "/myApp";

    private static URI getBaseURI(){
        return UriBuilder.fromUri(HTTP_LOCALHOST).port(PORT).path(JERSEY_SERVLET_CONTEXT_PATH).build();
    }

    public static final URI BASE_URI = getBaseURI();

    public static void main(String[] args) {
            ResourceConfig resourceConfig = new ResourceConfig();
            resourceConfig.register(MultiPartFeature.class);
//            resourceConfig.register(LoginRestService.class);

            final HttpServer grizzlyServer = GrizzlyHttpServerFactory.createHttpServer(getBaseURI(),resourceConfig);
        try {
            grizzlyServer.start();
            System.out.println(String.format("Started the server WADL availabel at " + "%s/application.wadl",BASE_URI));
            System.out.println("Enter to stop");
            System.in.read();
            System.out.println("Shutting down server");
            grizzlyServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
