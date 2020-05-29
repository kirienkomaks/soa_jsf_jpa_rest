package com.soa.lab4.stateless;

import com.soa.lab4.data.Miejsce;
import com.soa.lab4.singleton.ConverterInterface;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StateClass implements StateClassInterface{

    @EJB
    private ConverterInterface bean;
    private List<Miejsce> wolnemiejsca;

    public StateClass() {}
    @Lock
    public void setWolnemiejsca(List<Miejsce> wolnemiejsca) {
        this.wolnemiejsca = wolnemiejsca;
    }
    @Lock
    public List getWolneMiejsca(){
        wolnemiejsca = bean.getSeatList();
        return wolnemiejsca;
    }


}
