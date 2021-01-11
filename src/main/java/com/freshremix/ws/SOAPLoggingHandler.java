package com.freshremix.ws;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

/*
 * This simple SOAPHandler will output the contents of incoming
 * and outgoing messages.
 */
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    // change this to redirect output if desired
    private static PrintStream out = System.out;
    
    private static Logger logger = Logger.getLogger(SOAPLoggingHandler.class);

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
    	logger.info("--- Handle Message: ---");
    	logToLog4j(smc);
        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
    	logger.info("===================== Handle Fault: =====================");
    	logToLog4j(smc);
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
    }

    /*
     * Check the MESSAGE_OUTBOUND_PROPERTY in the context
     * to see if this is an outgoing or incoming message.
     * Write a brief message to the print stream and
     * output the message. The writeTo() method can throw
     * SOAPException or IOException
     */
    private void logToLog4j(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean)
            smc.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
        	logger.info("\nOutbound message:");
        } else {
        	logger.info("\nInbound message:");
        }

        SOAPMessage message = smc.getMessage();
        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	message.writeTo(baos);
        	logger.info(baos);
            //message.writeTo(out);
            //out.println("");   // just to add a newline
        } catch (Exception e) {
            out.println("Exception in handler: " + e);
        }
    }
}

