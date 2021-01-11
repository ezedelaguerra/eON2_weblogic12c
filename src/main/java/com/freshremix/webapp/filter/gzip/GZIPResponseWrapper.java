package com.freshremix.webapp.filter.gzip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {
    /** wrapped HttpServletResponse */
    final protected HttpServletResponse wrappedResponse;

    /** PrintWriter instance for JSPs (and others using getWriter() */
    protected PrintWriter writer;

    /** gzip servlet output stream wrapping servlet output stream */
    protected GZIPServletOutputStream gzipOS;

    /** stores the buffer size to use when wrapping around output stream */
    protected int bufferSize;

    /** stores content length in case required for reference */
    protected int contentLength;

    /** logger instance */
    protected final Logger log = Logger.getLogger(this.getClass());

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        wrappedResponse = response;
    }

    /**
     * Completes the compression by closing all streams.
     * 
     * @throws IOException
     */
    protected void complete() throws IOException {
        log.debug("GZIPResponseWrapper.complete()");

        if (writer != null) {
            // flush (just to be sure)
            writer.flush();

            // close!
            writer.close();
        } else if (gzipOS != null) {
            // flush the output stream (just to be sure)
            gzipOS.flush();

            // close the output stream
            gzipOS.close();
        } else {
            //Nothing to close!!!
        }

        // force flush on wrapped response
        super.flushBuffer();
    }

    /**
     * Returns a ServletOutputStream that compresses data
     */
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getOutputStream()");
        } else if (gzipOS == null) {
            gzipOS = new GZIPServletOutputStream(wrappedResponse);
        }

        return gzipOS;
    }

    /**
     * Returns a PrintWriter that compresses data
     */
    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return writer;
        } else if (gzipOS != null) {
            // we have a gzipOS, but no writer: it is not allowed to call
            // getWriter() at this point!
            throw new IllegalStateException("getWriter()");
        } else {
            // prepare gzip gzipOS
            gzipOS = new GZIPServletOutputStream(wrappedResponse);

            // buffer response
            final BufferedOutputStream bos = new BufferedOutputStream(gzipOS);

            // wrap in a PrintWriter
            return writer = new PrintWriter(
                    new OutputStreamWriter(bos, "UTF-8"));
        }
    }

    public ServletResponse getResponse() {
        return super.getResponse();
    }

    public boolean isCommitted() {
        /** gzip response is always committed */
        return true;
    }

    public void reset() {
        // dispatch to super class reset
        super.reset();

        // discard anything written to the writer and / or gzipOS
        this.writer = null;
        this.gzipOS = null;
    }

    public void resetBuffer() {
        // dispatch to super class reset
        super.resetBuffer();

        // discard anything written to the writer and / or gzipOS
        this.writer = null;
        this.gzipOS = null;
    }

    public void setResponse(ServletResponse response) {
        //dispatch setResponse to superclass to set the response object
        super.setResponse(response);
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    public void flushBuffer() throws IOException {
        // flush does not flush wrapped response's buffer!
    }

    public String getCharacterEncoding() {
        return super.getCharacterEncoding();
    }

    public Locale getLocale() {
        return super.getLocale();
    }

    public void setContentLength(int contentLength) {
        super.setContentLength(contentLength);

        // store content length
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        super.setContentType(contentType);
    }

    public void setLocale(Locale locale) {
        super.setLocale(locale);
    }

    /**
     * This class extendsd the ServletOutputStream by streaming any data sent
     * through the wrapped output gzipOS into a compressed form.
     * <p>
     * Revision History: <tt><PRE>
     * ============================================================================
     * Date             Author            Description
     * [MMM DD, YYYY]   [username]        [your modification]
     * Sep 6, 2005      Bren              Class created.	
     * ============================================================================
     * </PRE></tt>
     * 
     * @author Bren
     */
    protected class GZIPServletOutputStream extends ServletOutputStream {
        /** storage for compressed output */
        protected final ByteArrayOutputStream baos;

        /**
         * the gzip gzipOS; reference required to control compression states
         */
        protected final GZIPOutputStream gzippedStream;

        /**
         * The original response; used during writing of final compressed data
         */
        protected final HttpServletResponse wrappedResponse;

        /** state marker indicating if connection is already closed */
        protected boolean closed;

        /** keeps track of the number of bytes written into the output stream */
        protected int bytesWritten;

        public GZIPServletOutputStream(HttpServletResponse wrappedResponse)
                throws IOException {
            this.wrappedResponse = wrappedResponse;
            this.baos = new ByteArrayOutputStream();
            this.gzippedStream = new GZIPOutputStream(baos);
        }

        public void close() throws IOException {
            log.debug("GZIPServletOutputStream.close()");
            if (!closed) {
                // wrap up the gipped gzipOS
                gzippedStream.finish();

                // close gzip gzipOS cleanly
                gzippedStream.close();

                // get gzip-compressed data
                final byte[] compressedData = baos.toByteArray();

                // set content encoding to gzip
                GZIPResponseWrapper.this.setHeader("Content-Encoding", "gzip");

                // set content length header
                GZIPResponseWrapper.this
                        .setContentLength(compressedData.length);

                // get servlet respone's output gzipOS
                final ServletOutputStream os = wrappedResponse
                        .getOutputStream();

                // write out compressed data fully
                os.write(compressedData);

                // flush!
                os.flush();

                // close cleanly
                os.close();

                // mark as closed
                this.closed = true;

                log.debug("Response closed.");
            }
        }

        public void write(int b) throws IOException {
            if (!closed) {
                // write to zipped stream
                gzippedStream.write((byte) b);
                this.bytesWritten += 1;
            } else {
                throw new IOException("Already closed!");
            }
        }

        public void write(byte[] b) throws IOException {
            if (!closed) {
                // write to zipped stream
                gzippedStream.write(b);
                this.bytesWritten += b.length;
            } else {
                throw new IOException("Already closed!");
            }
        }

        public void write(byte[] b, int off, int len) throws IOException {
            if (!closed) {
                // write to zipped stream
                gzippedStream.write(b, off, len);
                this.bytesWritten += len;
            } else {
                ;//throw new IOException("Already closed!");
            }
        }

        public void flush() throws IOException {
            if (!closed) {
                gzippedStream.flush();
            } else {
                ;//throw new IOException("Already closed!");
            }
        }
    }
}
