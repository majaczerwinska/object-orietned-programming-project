package client.scenes;

import client.utils.ServerUtils;

import javax.inject.Inject;

public class WarningCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    public int boardID;

    /**
     * Constructor
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public WarningCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
}
