package com.dreamspace.util;

/**
 * Created by Liujilong on 2016/10/24/0024.
 * liujilong.me@gmail.com
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Logger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class Logger {
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    private static final String DEFAULT_TAG = "PRETTYLOGGER";

    private static Printer printer = new LoggerPrinter();

    //no instance
    private Logger() {
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    public static Settings init(String tag) {
        printer = new LoggerPrinter();
        return printer.init(tag);
    }

    public static void resetSettings() {
        printer.resetSettings();
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void d(Object object) {
        printer.d(object);
    }

    public static void e(String message, Object... args) {
        printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }

    public final static class Settings {

        private int methodCount = 2;
        private boolean showThreadInfo = true;
        private int methodOffset = 0;
        private LogAdapter logAdapter;

        /**
         * Determines to how logs will be printed
         */
        private LogLevel logLevel = LogLevel.FULL;

        public Settings hideThreadInfo() {
            showThreadInfo = false;
            return this;
        }

        public Settings methodCount(int methodCount) {
            if (methodCount < 0) {
                methodCount = 0;
            }
            this.methodCount = methodCount;
            return this;
        }

        public Settings logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Settings methodOffset(int offset) {
            this.methodOffset = offset;
            return this;
        }

        public Settings logAdapter(LogAdapter logAdapter) {
            this.logAdapter = logAdapter;
            return this;
        }

        public int getMethodCount() {
            return methodCount;
        }

        public boolean isShowThreadInfo() {
            return showThreadInfo;
        }

        public LogLevel getLogLevel() {
            return logLevel;
        }

        public int getMethodOffset() {
            return methodOffset;
        }

        public LogAdapter getLogAdapter() {
            if (logAdapter == null) {
                logAdapter = new AndroidLogAdapter();
            }
            return logAdapter;
        }

        public void reset() {
            methodCount = 2;
            methodOffset = 0;
            showThreadInfo = true;
            logLevel = LogLevel.FULL;
        }
    }

    final static class LoggerPrinter implements Printer {

        private static final String DEFAULT_TAG = "PRETTYLOGGER";

        private static final int DEBUG = 3;
        private static final int ERROR = 6;
        private static final int ASSERT = 7;
        private static final int INFO = 4;
        private static final int VERBOSE = 2;
        private static final int WARN = 5;

        /**
         * Android's max limit for a log entry is ~4076 bytes,
         * so 4000 bytes is used as chunk size since default charset
         * is UTF-8
         */
        private static final int CHUNK_SIZE = 4000;

        /**
         * It is used for json pretty print
         */
        private static final int JSON_INDENT = 2;

        /**
         * The minimum stack trace index, starts at this class after two native calls.
         */
        private static final int MIN_STACK_OFFSET = 3;

        /**
         * Drawing toolbox
         */
        private static final char TOP_LEFT_CORNER = '╔';
        private static final char BOTTOM_LEFT_CORNER = '╚';
        private static final char MIDDLE_CORNER = '╟';
        private static final char HORIZONTAL_DOUBLE_LINE = '║';
        private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
        private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
        private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
        private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
        private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

        /**
         * tag is used for the Log, the name is a little different
         * in order to differentiate the logs easily with the filter
         */
        private String tag;

        /**
         * Localize single tag and method count for each thread
         */
        private final ThreadLocal<String> localTag = new ThreadLocal<>();
        private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();

        /**
         * It is used to determine log settings such as method count, thread info visibility
         */
        private final Settings settings = new Settings();

        public LoggerPrinter() {
            init(DEFAULT_TAG);
        }

        /**
         * It is used to change the tag
         *
         * @param tag is the given string which will be used in Logger
         */
        @Override public Settings init(String tag) {
            if (tag == null) {
                throw new NullPointerException("tag may not be null");
            }
            if (tag.trim().length() == 0) {
                throw new IllegalStateException("tag may not be empty");
            }
            this.tag = tag;
            return settings;
        }

        @Override public Settings getSettings() {
            return settings;
        }

        @Override public Printer t(String tag, int methodCount) {
            if (tag != null) {
                localTag.set(tag);
            }
            localMethodCount.set(methodCount);
            return this;
        }

        @Override public void d(String message, Object... args) {
            log(DEBUG, null, message, args);
        }

        @Override public void d(Object object) {
            String message;
            if (object.getClass().isArray()) {
                message = Arrays.deepToString((Object[]) object);
            } else {
                message = object.toString();
            }
            log(DEBUG, null, message);
        }

        @Override public void e(String message, Object... args) {
            e(null, message, args);
        }

        @Override public void e(Throwable throwable, String message, Object... args) {
            log(ERROR, throwable, message, args);
        }

        @Override public void w(String message, Object... args) {
            log(WARN, null, message, args);
        }

        @Override public void i(String message, Object... args) {
            log(INFO, null, message, args);
        }

        @Override public void v(String message, Object... args) {
            log(VERBOSE, null, message, args);
        }

        @Override public void wtf(String message, Object... args) {
            log(ASSERT, null, message, args);
        }

        /**
         * Formats the json content and print it
         *
         * @param json the json content
         */
        @Override public void json(String json) {
            if (Helper.isEmpty(json)) {
                d("Empty/Null json content");
                return;
            }
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    String message = jsonObject.toString(JSON_INDENT);
                    d(message);
                    return;
                }
                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    String message = jsonArray.toString(JSON_INDENT);
                    d(message);
                    return;
                }
                e("Invalid Json");
            } catch (JSONException e) {
                e("Invalid Json");
            }
        }

        /**
         * Formats the json content and print it
         *
         * @param xml the xml content
         */
        @Override public void xml(String xml) {
            if (Helper.isEmpty(xml)) {
                d("Empty/Null xml content");
                return;
            }
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
            } catch (TransformerException e) {
                e("Invalid xml");
            }
        }

        @Override public synchronized void log(int priority, String tag, String message, Throwable throwable) {
            if (settings.getLogLevel() == LogLevel.NONE) {
                return;
            }
            if (throwable != null && message != null) {
                message += " : " + Helper.getStackTraceString(throwable);
            }
            if (throwable != null && message == null) {
                message = Helper.getStackTraceString(throwable);
            }
            if (message == null) {
                message = "No message/exception is set";
            }
            int methodCount = getMethodCount();
            if (Helper.isEmpty(message)) {
                message = "Empty/NULL log message";
            }

            logTopBorder(priority, tag);
            logHeaderContent(priority, tag, methodCount);

            //get bytes of message with system's default charset (which is UTF-8 for Android)
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= CHUNK_SIZE) {
                if (methodCount > 0) {
                    logDivider(priority, tag);
                }
                logContent(priority, tag, message);
                logBottomBorder(priority, tag);
                return;
            }
            if (methodCount > 0) {
                logDivider(priority, tag);
            }
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                //create a new String with system's default charset (which is UTF-8 for Android)
                logContent(priority, tag, new String(bytes, i, count));
            }
            logBottomBorder(priority, tag);
        }

        @Override public void resetSettings() {
            settings.reset();
        }

        /**
         * This method is synchronized in order to avoid messy of logs' order.
         */
        private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
            if (settings.getLogLevel() == LogLevel.NONE) {
                return;
            }
            String tag = getTag();
            String message = createMessage(msg, args);
            log(priority, tag, message, throwable);
        }

        private void logTopBorder(int logType, String tag) {
            logChunk(logType, tag, TOP_BORDER);
        }

        @SuppressWarnings("StringBufferReplaceableByString")
        private void logHeaderContent(int logType, String tag, int methodCount) {
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            if (settings.isShowThreadInfo()) {
                logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
                logDivider(logType, tag);
            }
            String level = "";

            int stackOffset = getStackOffset(trace) + settings.getMethodOffset();

            //corresponding method count with the current stack may exceeds the stack trace. Trims the count
            if (methodCount + stackOffset > trace.length) {
                methodCount = trace.length - stackOffset - 1;
            }

            for (int i = methodCount; i > 0; i--) {
                int stackIndex = i + stackOffset;
                if (stackIndex >= trace.length) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                builder.append("║ ")
                        .append(level)
                        .append(getSimpleClassName(trace[stackIndex].getClassName()))
                        .append(".")
                        .append(trace[stackIndex].getMethodName())
                        .append(" ")
                        .append(" (")
                        .append(trace[stackIndex].getFileName())
                        .append(":")
                        .append(trace[stackIndex].getLineNumber())
                        .append(")");
                level += "   ";
                logChunk(logType, tag, builder.toString());
            }
        }

        private void logBottomBorder(int logType, String tag) {
            logChunk(logType, tag, BOTTOM_BORDER);
        }

        private void logDivider(int logType, String tag) {
            logChunk(logType, tag, MIDDLE_BORDER);
        }

        private void logContent(int logType, String tag, String chunk) {
            String[] lines = chunk.split(System.getProperty("line.separator"));
            for (String line : lines) {
                logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
            }
        }

        private void logChunk(int logType, String tag, String chunk) {
            String finalTag = formatTag(tag);
            switch (logType) {
                case ERROR:
                    settings.getLogAdapter().e(finalTag, chunk);
                    break;
                case INFO:
                    settings.getLogAdapter().i(finalTag, chunk);
                    break;
                case VERBOSE:
                    settings.getLogAdapter().v(finalTag, chunk);
                    break;
                case WARN:
                    settings.getLogAdapter().w(finalTag, chunk);
                    break;
                case ASSERT:
                    settings.getLogAdapter().wtf(finalTag, chunk);
                    break;
                case DEBUG:
                    // Fall through, log debug by default
                default:
                    settings.getLogAdapter().d(finalTag, chunk);
                    break;
            }
        }

        private String getSimpleClassName(String name) {
            int lastIndex = name.lastIndexOf(".");
            return name.substring(lastIndex + 1);
        }

        private String formatTag(String tag) {
            if (!Helper.isEmpty(tag) && !Helper.equals(this.tag, tag)) {
                return this.tag + "-" + tag;
            }
            return this.tag;
        }

        /**
         * @return the appropriate tag based on local or global
         */
        private String getTag() {
            String tag = localTag.get();
            if (tag != null) {
                localTag.remove();
                return tag;
            }
            return this.tag;
        }

        private String createMessage(String message, Object... args) {
            return args == null || args.length == 0 ? message : String.format(message, args);
        }

        private int getMethodCount() {
            Integer count = localMethodCount.get();
            int result = settings.getMethodCount();
            if (count != null) {
                localMethodCount.remove();
                result = count;
            }
            if (result < 0) {
                throw new IllegalStateException("methodCount cannot be negative");
            }
            return result;
        }

        /**
         * Determines the starting index of the stack trace, after method calls made by this class.
         *
         * @param trace the stack trace
         *
         * @return the stack offset
         */
        private int getStackOffset(StackTraceElement[] trace) {
            for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
                StackTraceElement e = trace[i];
                String name = e.getClassName();
                if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                    return --i;
                }
            }
            return -1;
        }

    }

    enum LogLevel {

        /**
         * Prints all logs
         */
        FULL,

        /**
         * No log will be printed
         */
        NONE
    }

    interface LogAdapter {
        void d(String tag, String message);

        void e(String tag, String message);

        void w(String tag, String message);

        void i(String tag, String message);

        void v(String tag, String message);

        void wtf(String tag, String message);
    }


    static class AndroidLogAdapter implements LogAdapter {
        @Override public void d(String tag, String message) {
            Log.d(tag, message);
        }

        @Override public void e(String tag, String message) {
            Log.e(tag, message);
        }

        @Override public void w(String tag, String message) {
            Log.w(tag, message);
        }

        @Override public void i(String tag, String message) {
            Log.i(tag, message);
        }

        @Override public void v(String tag, String message) {
            Log.v(tag, message);
        }

        @Override public void wtf(String tag, String message) {
            Log.wtf(tag, message);
        }
    }

    interface Printer {

        Printer t(String tag, int methodCount);

        Settings init(String tag);

        Settings getSettings();

        void d(String message, Object... args);

        void d(Object object);

        void e(String message, Object... args);

        void e(Throwable throwable, String message, Object... args);

        void w(String message, Object... args);

        void i(String message, Object... args);

        void v(String message, Object... args);

        void wtf(String message, Object... args);

        void json(String json);

        void xml(String xml);

        void log(int priority, String tag, String message, Throwable throwable);

        void resetSettings();
    }
}

final class Helper {

    private Helper() {
        // Hidden constructor.
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     *
     * @return true if str is null or zero length
     */
    static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     *
     * @return true if a and b are equal
     *
     * NOTE: Logic slightly change due to strict policy on CI -
     * "Inner assignments should be avoided"
     */
    static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        if (a != null && b != null) {
            int length = a.length();
            if (length == b.length()) {
                if (a instanceof String && b instanceof String) {
                    return a.equals(b);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (a.charAt(i) != b.charAt(i)) return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

}


