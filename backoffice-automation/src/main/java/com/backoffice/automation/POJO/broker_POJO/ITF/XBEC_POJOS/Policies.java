package com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */


public class Policies {

    @NotNull
    @NotEmpty
    @Size(min = 1)
    private List<Policy> policyList;

    public List<Policy> getPolicy() {
        return policyList;
    }

    public void setPolicy(List<Policy> Policy) {
        this.policyList = Policy;
    }

    @Override
    public String toString() {
        return "ClassPojo [Policy = " + policyList + "]";
    }
}
