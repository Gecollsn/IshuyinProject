package com.ishuyin.gecollsn.base;

/**
 * Created by Gecollsn on 10/23/2016.
 */

public abstract class IDataWriter {
    public abstract void writeData(Object... data);
    public void writeError(Object... error){}
    public void writeNothing(){}
}
