package com.udmtek.DBCore.DBAccessor;

import java.io.Serializable;
import java.util.List;

public interface DBCoreKey extends Serializable{
	abstract public List<String> getKeyColumns();
	abstract public boolean checkKeyData();
}
