package za.co.dladle.entity;

import za.co.dladle.model.User;

import java.util.List;

/**
 * Created by prady on 6/29/2017.
 */
public class ServiceView {
    private boolean emergency;
    private String serviceNeedTime;
    private String serviceDescription;
    private List<ServiceDocuments> serviceDocuments;
    private User user;
}
