package com.soa.lab4.stateless;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StateClassInterface {
    List getWolneMiejsca();

}
