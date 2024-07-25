package com.kim.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class MyLength extends GenericUDF {
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        if (objectInspectors.length != 1) {
            throw new UDFArgumentException("直接收一个参数");
        }
        ObjectInspector objectInspector = objectInspectors[0];
        if (ObjectInspector.Category.PRIMITIVE != objectInspector.getCategory()) {
            throw new UDFArgumentException("直接收基本数据类型的参数");
        }
        PrimitiveObjectInspector inspector = (PrimitiveObjectInspector) objectInspector;
        if (inspector.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("直接收STRING数据类型的参数");
        }
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        DeferredObject deferredObject = deferredObjects[0];
        Object value = deferredObject.get();
        if (value == null) {
            return 0;
        }
        return String.valueOf(value).length();
    }

    @Override
    public String getDisplayString(String[] strings) {
        return "";
    }
}
