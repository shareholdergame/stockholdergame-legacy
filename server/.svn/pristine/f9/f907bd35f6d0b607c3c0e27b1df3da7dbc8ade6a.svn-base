package com.stockholdergame.server.util;

import com.stockholdergame.server.exceptions.ApplicationException;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Alexander Savin
 *         Date: 1.3.12 19.11
 */
public final class AMFHelper {

    private AMFHelper() {
    }

    private static SerializationContext context = getSerializationContext();

    public static byte[] serializeToAmf(Object obj) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            Amf3Output amf3Output = new Amf3Output(context);
            amf3Output.setOutputStream(bout);
            amf3Output.writeObject(obj);
            amf3Output.flush();
            amf3Output.close();
            return bout.toByteArray();
        } catch(IOException e) {
            throw new ApplicationException("Serialization to AMF failed.", e);
        }
    }

    public static Object deserializeFromAmf(byte[] amfObject) {
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(amfObject);
            Amf3Input amf3Input = new Amf3Input(context);
            amf3Input.setInputStream(bin);
            return amf3Input.readObject();
        } catch (Exception e) {
            throw new ApplicationException("Deserialization to AMF failed.", e);
        }
    }

    private static SerializationContext getSerializationContext() {
        return SerializationContext.getSerializationContext();
        // todo - add serialization context configuration if necessary
        /*serializationContext.enableSmallMessages = true;
        serializationContext.instantiateTypes = true;
        serializationContext.supportRemoteClass = true;
        serializationContext.legacyCollection = false;
        serializationContext.legacyMap = false;
        serializationContext.legacyXMLDocument = false;
        serializationContext.legacyXMLNamespaces = false;
        serializationContext.legacyThrowable = false;
        serializationContext.legacyBigNumbers = false;
        serializationContext.restoreReferences = false;
        serializationContext.logPropertyErrors = false;
        serializationContext.ignorePropertyErrors = true;

        http://snipplr.com/view/7820/
        */
    }
}
